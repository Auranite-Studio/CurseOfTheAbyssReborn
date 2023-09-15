package net.endgineer.curseoftheabyss.config.spec.abyss.sections;

import net.minecraftforge.common.ForgeConfigSpec;

public class StrainSection {
    public final int DEFAULT_YIELD_LAYER = 5;
    public final int MINIMUM_YIELD_LAYER = 1;
    public final int MAXIMUM_YIELD_LAYER = 7;
    public final ForgeConfigSpec.ConfigValue<Integer> YIELD_LAYER;

    public final double DEFAULT_ELASTICITY_MODULUS = 0.07936;
    public final double MINIMUM_ELASTICITY_MODULUS = 0;
    public final ForgeConfigSpec.ConfigValue<Double> ELASTICITY_MODULUS;

    public final double DEFAULT_STRAIN_HARDENING_COEFFICIENT = 0.04121;
    public final double MINIMUM_STRAIN_HARDENING_COEFFICIENT = 0;
    public final ForgeConfigSpec.ConfigValue<Double> STRAIN_HARDENING_COEFFICIENT;

    public StrainSection(ForgeConfigSpec.Builder builder) {
        builder.push("STRAIN");

        YIELD_LAYER = builder.comment(
            "The layer at the bottom of which lies the absolute boundary of the Abyss. Beyond that boundary, strains become exponential and permanently deform the player.\n"+
            "Values: [ "+MINIMUM_YIELD_LAYER+", "+MAXIMUM_YIELD_LAYER+" ]\n"+
            "Default: "+DEFAULT_YIELD_LAYER)
            .define("YIELD_LAYER", DEFAULT_YIELD_LAYER, value -> value != null && (Integer) value >= MINIMUM_YIELD_LAYER && (Integer) value <= MAXIMUM_YIELD_LAYER);

        ELASTICITY_MODULUS = builder.comment(
            "The player's resistance to the linear portion of the curse. The smaller this value, the more damage the player takes from the curse.\n"+
            "Values: ( "+MINIMUM_ELASTICITY_MODULUS+", "+Double.MAX_VALUE+" ]\n"+
            "Default: "+DEFAULT_ELASTICITY_MODULUS)
            .define("ELASTICITY_MODULUS", DEFAULT_ELASTICITY_MODULUS, value -> value != null && (Double) value > MINIMUM_ELASTICITY_MODULUS);
        
        STRAIN_HARDENING_COEFFICIENT = builder.comment(
            "The player's resistance to the exponential portion of the curse. The smaller this value, the more damage the player takes from the curse.\n"+
            "Values: ( "+MINIMUM_STRAIN_HARDENING_COEFFICIENT+", "+Double.MAX_VALUE+" ]\n"+
            "Default: "+DEFAULT_STRAIN_HARDENING_COEFFICIENT)
            .define("STRAIN_HARDENING_COEFFICIENT", DEFAULT_STRAIN_HARDENING_COEFFICIENT, value -> value != null && (Double) value > MINIMUM_STRAIN_HARDENING_COEFFICIENT);

        builder.pop();
    }
}
