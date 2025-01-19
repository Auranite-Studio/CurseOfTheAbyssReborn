
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package com.esmods.curseoftheabyssreborn.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.registries.Registries;

import com.esmods.curseoftheabyssreborn.potion.BleedingMobEffect;
import com.esmods.curseoftheabyssreborn.CurseoftheabyssMod;

public class CurseoftheabyssModMobEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(Registries.MOB_EFFECT, CurseoftheabyssMod.MODID);
	public static final DeferredHolder<MobEffect, MobEffect> BLEEDING = REGISTRY.register("bleeding", () -> new BleedingMobEffect());
}
