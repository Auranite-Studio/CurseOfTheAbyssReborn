
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.esmods.curseoftheabyssreborn.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import com.esmods.curseoftheabyssreborn.CurseoftheabyssMod;

public class CurseoftheabyssModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CurseoftheabyssMod.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CURSE_OF_THE_ABYSS_ITEMS = REGISTRY.register("curse_of_the_abyss_items",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.curseoftheabyss.curse_of_the_abyss_items")).icon(() -> new ItemStack(CurseoftheabyssModItems.STAR_COMPASS.get())).displayItems((parameters, tabData) -> {
				tabData.accept(CurseoftheabyssModItems.STAR_COMPASS.get());
			}).build());
}
