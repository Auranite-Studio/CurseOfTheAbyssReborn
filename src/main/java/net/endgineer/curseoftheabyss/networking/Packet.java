package net.endgineer.curseoftheabyss.networking;

import net.endgineer.curseoftheabyss.client.CurseData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.api.distmarker.Dist;

public class Packet {
    public double field;

    public Packet(double field) {
        this.field = field;
    }

    public static void encode(Packet message, FriendlyByteBuf buffer) {
        buffer.writeDouble(message.field);
    }

    public static Packet decode(FriendlyByteBuf buffer) {
        return new Packet(buffer.readDouble());
    }

    public static void handle(Packet message, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CurseData.update(message.field)));
        }

        context.setPacketHandled(true);
    }
}