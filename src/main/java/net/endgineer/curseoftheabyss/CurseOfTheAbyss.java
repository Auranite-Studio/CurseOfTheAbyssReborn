package net.endgineer.curseoftheabyss;

import net.endgineer.curseoftheabyss.common.ModTabs;
import net.endgineer.curseoftheabyss.config.spec.ModCommonConfig;
import net.endgineer.curseoftheabyss.core.ModItems;
import net.endgineer.curseoftheabyss.client.gui.StarCompassOverlay;
import net.endgineer.curseoftheabyss.helpers.creativemd.enhancedvisuals.common.addon.curseoftheabyss.CurseOfTheAbyssShaders;
import net.endgineer.curseoftheabyss.networking.PacketHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

@Mod(CurseOfTheAbyss.MOD_ID)
public class CurseOfTheAbyss {
    public static final String MOD_ID = "curseoftheabyss";

    public CurseOfTheAbyss() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModTabs.REGISTRY.register(modEventBus);

        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::enqueueIMC);

        ModLoadingContext.get().registerConfig(Type.COMMON, ModCommonConfig.SPEC, CurseOfTheAbyss.MOD_ID + "-common.toml");
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        if(ModList.get().isLoaded("curios")) {
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().build());
        }
    }
    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PacketHandler.init();
            CurseOfTheAbyssShaders.load();
        });
    }

    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(modid = CurseOfTheAbyss.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD, value= Dist.CLIENT)
    public static class CurseOfTheAbyssClient {

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void onRegisterGuiOverlaysEvent(RegisterGuiOverlaysEvent event){
            event.registerAboveAll("unlock_gui", StarCompassOverlay.GUI);
        }
    }
}