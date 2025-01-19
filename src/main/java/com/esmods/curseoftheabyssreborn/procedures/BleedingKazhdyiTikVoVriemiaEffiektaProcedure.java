package com.esmods.curseoftheabyssreborn.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;

public class BleedingKazhdyiTikVoVriemiaEffiektaProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!entity.onGround()) {
			entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.MAGIC)), 1);
		}
	}
}
