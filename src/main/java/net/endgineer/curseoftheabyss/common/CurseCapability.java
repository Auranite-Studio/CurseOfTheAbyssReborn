package net.endgineer.curseoftheabyss.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;
import net.minecraft.server.level.ServerPlayer;
import net.endgineer.curseoftheabyss.config.variables.ModVariables;
import net.endgineer.curseoftheabyss.networking.Packet;
import net.endgineer.curseoftheabyss.networking.PacketHandler;
import net.minecraft.nbt.CompoundTag;

public class CurseCapability implements Serializable {
    private double lowest_depth;
    private double previous_depth;
    private double derangement;
    private double constitution;
    private Strains strains;

    public CurseCapability() {
        this.reset();
    }

    public double getPreviousDepth() { return this.previous_depth; }

    public double getLowestDepth() { return this.lowest_depth; }

    public double getConstitution() { return this.constitution; }

    public double getDerangement() { return this.derangement; }

    public void reset() {
        this.lowest_depth = 0;
        this.previous_depth = 0;
        this.derangement = 0;
        this.constitution = 0;
        this.strains = new Strains();
    }

    public double tick(Player player) {
        if(this.constitution == 0) this.constitution = player.getMaxHealth();

        double x = player.getX(), y = player.getY(), z = player.getZ();

        double field = player.level.dimension().location().getPath() == "overworld" ? Abyss.field(Abyss.get(player.level.getServer()).getOrigin(), x, y, z, player.level.getGameTime()) : 0;

        double current_depth = Math.min(y, 0);

        double stress = 0;

        if(player.level.dimension().location().getPath() == "overworld" && current_depth < 0) {
            this.lowest_depth = Math.min(current_depth, this.lowest_depth);

            this.derangement = Math.min(this.derangement + Abyss.distortion(field, y), 1);
            
            if(current_depth - this.lowest_depth > 10 && this.previous_depth < current_depth) {
                stress = (current_depth-previous_depth)*Abyss.strain_deformation(field, this.lowest_depth);
            }
            this.previous_depth = current_depth;
        } else if(this.strains.handled() && this.lowest_depth >= Abyss.boundary(ModVariables.DEFORMATION.YIELD_LAYER)) {
            this.lowest_depth = 0;
            this.previous_depth = 0;
        }

        double strain = this.strains.tick(stress);

        if(Abyss.layer(this.lowest_depth) > ModVariables.DEFORMATION.YIELD_LAYER) {
            this.constitution = Math.max(0, this.constitution - strain);
        }

        sync(player, field);

        return strain;
    }

    public void sync(Player player, double field) {
        PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new Packet(field));
    }

    public void save(CompoundTag data) {
        byte[] bytes = null;
        try (ByteArrayOutputStream bytesOutStream = new ByteArrayOutputStream();  ObjectOutputStream objectOutStream = new ObjectOutputStream(bytesOutStream)) {
            objectOutStream.writeObject(this);
            bytes = bytesOutStream.toByteArray();
        } catch(IOException exception) { System.out.println(exception); }
        data.putByteArray("curse", bytes);
    }

    public void load(CompoundTag data) {
        byte[] bytes = data.getByteArray("curse");
        try (ByteArrayInputStream bytesInStream = new ByteArrayInputStream(bytes); ObjectInputStream objectInStream = new ObjectInputStream(bytesInStream)) {
            CurseCapability curse = (CurseCapability) objectInStream.readObject();
            this.lowest_depth = curse.lowest_depth;
            this.previous_depth = curse.previous_depth;
            this.derangement = curse.derangement;
            this.strains = curse.strains;
            this.constitution = curse.constitution;
        } catch(IOException | ClassNotFoundException exception) { System.out.println(exception); }
    }
}
