package com.esmods.curseoftheabyssreborn.init;

import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.ModList;
import net.neoforged.bus.api.SubscribeEvent;

import com.esmods.curseoftheabyssreborn.configuration.CurseOfTheAbyssConfConfiguration;
import com.esmods.curseoftheabyssreborn.CurseoftheabyssMod;

@EventBusSubscriber(modid = CurseoftheabyssMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CurseoftheabyssModConfigs {
	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		event.enqueueWork(() -> {
			ModList.get().getModContainerById("curseoftheabyss").get().registerConfig(ModConfig.Type.COMMON, CurseOfTheAbyssConfConfiguration.SPEC, "curse_of_the_abyss.toml");
		});
	}
}
