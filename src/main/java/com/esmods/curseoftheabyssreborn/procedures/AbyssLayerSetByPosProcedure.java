package com.esmods.curseoftheabyssreborn.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;

import com.esmods.curseoftheabyssreborn.network.CurseoftheabyssModVariables;

@EventBusSubscriber
public class AbyssLayerSetByPosProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		{
			CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
			_vars.curPosY = entity.getY();
			_vars.syncPlayerVariables(entity);
		}
		if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY < entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).minPosY && entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).layer > 0) {
			{
				CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
				_vars.minPosY = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (getEntityGameType(entity) == GameType.CREATIVE && getEntityGameType(entity) == GameType.SPECTATOR) {
			{
				CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
				_vars.minPosY = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY <= 0 && entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY >= -128) {
			{
				CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
				_vars.layer = 1;
				_vars.syncPlayerVariables(entity);
			}
		} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY <= -129 && entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY >= -256) {
			{
				CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
				_vars.layer = 2;
				_vars.syncPlayerVariables(entity);
			}
		} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY <= -257 && entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY >= -417) {
			{
				CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
				_vars.layer = 3;
				_vars.syncPlayerVariables(entity);
			}
		} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY <= -418 && entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY >= -577) {
			{
				CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
				_vars.layer = 4;
				_vars.syncPlayerVariables(entity);
			}
		} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY <= -578 && entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY >= -737) {
			{
				CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
				_vars.layer = 5;
				_vars.syncPlayerVariables(entity);
			}
		} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY <= -738 && entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY >= -897) {
			{
				CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
				_vars.layer = 6;
				_vars.syncPlayerVariables(entity);
			}
		} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY <= -898 && entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY >= -1024) {
			{
				CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
				_vars.layer = 7;
				_vars.syncPlayerVariables(entity);
			}
		} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY > 0) {
			{
				CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
				_vars.layer = 0;
				_vars.syncPlayerVariables(entity);
			}
		}
	}

	private static GameType getEntityGameType(Entity entity) {
		if (entity instanceof ServerPlayer serverPlayer) {
			return serverPlayer.gameMode.getGameModeForPlayer();
		} else if (entity instanceof Player player && player.level().isClientSide()) {
			PlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
			if (playerInfo != null)
				return playerInfo.getGameMode();
		}
		return null;
	}
}
