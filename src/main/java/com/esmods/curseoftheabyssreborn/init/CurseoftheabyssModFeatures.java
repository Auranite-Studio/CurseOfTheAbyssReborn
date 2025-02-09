
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.esmods.curseoftheabyssreborn.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.core.registries.Registries;

import com.esmods.curseoftheabyssreborn.world.features.TreesInAbyssFeature;
import com.esmods.curseoftheabyssreborn.CurseoftheabyssMod;

public class CurseoftheabyssModFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(Registries.FEATURE, CurseoftheabyssMod.MODID);
	public static final DeferredHolder<Feature<?>, Feature<?>> TREES_IN_ABYSS = REGISTRY.register("trees_in_abyss", TreesInAbyssFeature::new);
}
