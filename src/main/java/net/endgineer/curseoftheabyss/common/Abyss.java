package net.endgineer.curseoftheabyss.common;

import javax.annotation.Nonnull;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.server.MinecraftServer;
import net.endgineer.curseoftheabyss.config.variables.ModVariables;
import net.endgineer.curseoftheabyss.utils.kdotjpg.opensimplex2.java.OpenSimplex2S;
import net.minecraft.nbt.CompoundTag;

public class Abyss extends SavedData {
    private long origin;

    public Abyss() {
        this.origin = System.currentTimeMillis();
    }

    public Abyss(CompoundTag tag) {
        this.origin = tag.getLong("origin");
    }

    @Nonnull
    public static Abyss get(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(Abyss::new, Abyss::new, "abyss");
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putLong("origin", this.origin);
        return tag;
    }

    public long getOrigin() { return origin; }

    public static int layer(double y) {
        return (int) Math.ceil(7 * Abyss.pressure(y));
    }

    private static double pressure(double y) {
        return Math.min(Math.max(0, -y/ModVariables.ABYSS.SPAN), 1);
    }

    public static double boundary(int layer) {
        return -(ModVariables.ABYSS.SPAN/7.0)*layer;
    }

    public static double distortion(double field, double y) {
        return field * ModVariables.DISTORTION.LAYER[Abyss.layer(y)];
    }

    public static double strain(double field, double y_lowest) {
        return field * (-y_lowest / (ModVariables.STRAIN.ELASTICITY_MODULUS * ModVariables.ABYSS.SPAN) + Math.pow(y_lowest / Abyss.boundary(ModVariables.STRAIN.YIELD_LAYER), 1 / ModVariables.STRAIN.STRAIN_HARDENING_COEFFICIENT));
    }

    public static double loss(double sender_y, double receiver_y) {
        double integrity = 1;

        int receiver_layer = Abyss.layer(receiver_y);
        int sender_layer = Abyss.layer(sender_y);

        int lower_layer = Math.min(sender_layer, receiver_layer), upper_layer = Math.max(sender_layer, receiver_layer);

        for(int i = lower_layer; i < upper_layer; i++) {
            double propagation = 0;

            switch(i) {
                case 0:
                    propagation = 1.00;
                    break;
                case 1:
                    propagation = 0.97;
                    break;
                case 2:
                    propagation = 0.94;
                    break;
                case 3:
                    propagation = 0.88;
                    break;
                case 4:
                    propagation = 0.76;
                    break;
                case 5:
                    propagation = 0.52;
                    break;
                case 6:
                    propagation = 0.04;
                    break;
            }
            
            if(Math.random() > propagation) {
                return 1;
            } else {
                integrity *= propagation;
            }
        }

        return 1-integrity;
    }

    public static double field(long seed, double x, double y, double z, long gametime) {
        double field = 0;

        if(y > 8) return field;

        for (int octave = 0; octave < 7; octave++) {
            field += Math.abs(
                OpenSimplex2S.noise3_ImproveXZ(
                    seed,
                    x / (ModVariables.FIELD.XZ_PERIOD * Math.pow(2, octave)),
                    y / (ModVariables.FIELD.Y_PERIOD * Math.pow(2, octave)) - gametime / ModVariables.FIELD.T_PERIOD,
                    z / (ModVariables.FIELD.XZ_PERIOD * Math.pow(2, octave))
                )
            ) / Math.pow(2, 6-octave);
        }

        return (y > 0 ? (1-y/8) : 1)*Math.min(Abyss.pressure(y) + field*(1-Abyss.pressure(y)), 1);
    }
}
