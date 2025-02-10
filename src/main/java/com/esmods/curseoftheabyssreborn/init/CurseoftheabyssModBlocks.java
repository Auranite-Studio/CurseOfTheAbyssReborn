
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.esmods.curseoftheabyssreborn.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.Block;

import com.esmods.curseoftheabyssreborn.block.StoneWithGrassLayer1Block;
import com.esmods.curseoftheabyssreborn.block.StoneLayer1Block;
import com.esmods.curseoftheabyssreborn.CurseoftheabyssMod;

public class CurseoftheabyssModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(CurseoftheabyssMod.MODID);
	public static final DeferredBlock<Block> STONE_LAYER_1 = REGISTRY.register("stone_layer_1", StoneLayer1Block::new);
	public static final DeferredBlock<Block> STONE_WITH_GRASS_LAYER_1 = REGISTRY.register("stone_with_grass_layer_1", StoneWithGrassLayer1Block::new);
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
