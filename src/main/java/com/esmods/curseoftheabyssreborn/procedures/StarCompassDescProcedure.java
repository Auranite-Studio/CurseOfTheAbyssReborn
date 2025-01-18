package com.esmods.curseoftheabyssreborn.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import com.esmods.curseoftheabyssreborn.network.CurseoftheabyssModVariables;

public class StarCompassDescProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return Component.translatable("item.star_compass.tooltip.layer").getString() + "" + Math.round(entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).layer) + "\n"
				+ Component.translatable("item.star_compass.tooltip.current_player_pos").getString() + Math.round(entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY) + "\n"
				+ Component.translatable("item.star_compass.tooltip.min_player_pos").getString() + Math.round(entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).minPosY);
	}
}
