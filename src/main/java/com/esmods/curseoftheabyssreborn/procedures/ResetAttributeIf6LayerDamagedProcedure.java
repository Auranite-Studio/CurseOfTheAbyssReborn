package com.esmods.curseoftheabyssreborn.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

import com.esmods.curseoftheabyssreborn.network.CurseoftheabyssModVariables;

@EventBusSubscriber
public class ResetAttributeIf6LayerDamagedProcedure {
	@SubscribeEvent
	public static void onPlayerRespawned(PlayerEvent.PlayerRespawnEvent event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).if_6_layer_damaged_death) {
			if (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
				_livingEntity0.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20);
			{
				CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
				_vars.if_6_layer_damaged_death = false;
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}
