package net.endgineer.curseoftheabyss.common;
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */

import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
import net.endgineer.curseoftheabyss.core.ModItems;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CurseOfTheAbyss.MOD_ID);
    public static final RegistryObject<CreativeModeTab> ITEMS = REGISTRY.register("items",
            () -> CreativeModeTab.builder().title(Component.translatable("item_group.curseoftheabyss.items")).icon(() -> new ItemStack(ModItems.STAR_COMPASS.get())).displayItems((parameters, tabData) -> {
                        tabData.accept(ModItems.STAR_COMPASS.get());
                    })

                    .build());
}
