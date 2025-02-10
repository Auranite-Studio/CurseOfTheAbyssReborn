
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.esmods.curseoftheabyssreborn.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import com.esmods.curseoftheabyssreborn.item.StarCompassItem;
import com.esmods.curseoftheabyssreborn.CurseoftheabyssMod;

public class CurseoftheabyssModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(CurseoftheabyssMod.MODID);
	public static final DeferredItem<Item> STAR_COMPASS = REGISTRY.register("star_compass", StarCompassItem::new);
	public static final DeferredItem<Item> STONE_LAYER_1 = block(CurseoftheabyssModBlocks.STONE_LAYER_1);
	public static final DeferredItem<Item> STONE_WITH_GRASS_LAYER_1 = block(CurseoftheabyssModBlocks.STONE_WITH_GRASS_LAYER_1);

	// Start of user code block custom items
	// End of user code block custom items
	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
