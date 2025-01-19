
package com.esmods.curseoftheabyssreborn.potion;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.resources.ResourceLocation;

import com.esmods.curseoftheabyssreborn.procedures.BleedingKazhdyiTikVoVriemiaEffiektaProcedure;
import com.esmods.curseoftheabyssreborn.CurseoftheabyssMod;

public class BleedingMobEffect extends MobEffect {
	public BleedingMobEffect() {
		super(MobEffectCategory.HARMFUL, -3407821);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(CurseoftheabyssMod.MODID, "effect.bleeding_0"), -0.07, AttributeModifier.Operation.ADD_VALUE);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		BleedingKazhdyiTikVoVriemiaEffiektaProcedure.execute(entity.level(), entity);
		return super.applyEffectTick(entity, amplifier);
	}
}
