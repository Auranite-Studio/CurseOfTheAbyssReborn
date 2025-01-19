package com.esmods.curseoftheabyssreborn.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;

import com.esmods.curseoftheabyssreborn.network.CurseoftheabyssModVariables;
import com.esmods.curseoftheabyssreborn.init.CurseoftheabyssModMobEffects;
import com.esmods.curseoftheabyssreborn.CurseoftheabyssMod;

@EventBusSubscriber
public class CurseWhenPlayerGoesUpProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!(getEntityGameType(entity) == GameType.CREATIVE) && !(getEntityGameType(entity) == GameType.SPECTATOR)) {
			if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).minPosY + 10 < entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY && entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curse == false) {
				if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).layer == 1) {
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.curse = true;
						_vars.syncPlayerVariables(entity);
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 0));
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.minPosY = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY;
						_vars.syncPlayerVariables(entity);
					}
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.curse = false;
						_vars.syncPlayerVariables(entity);
					}
				} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).layer == 2) {
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.curse = true;
						_vars.syncPlayerVariables(entity);
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 2));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 2));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 1));
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.minPosY = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY;
						_vars.syncPlayerVariables(entity);
					}
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.curse = false;
						_vars.syncPlayerVariables(entity);
					}
				} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).layer == 3) {
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.curse = true;
						_vars.syncPlayerVariables(entity);
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 2));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 2));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 2));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 2));
					new Object() {
						void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
							{
								CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
								_vars.random_sound = Mth.nextInt(RandomSource.create(), 1, 5);
								_vars.syncPlayerVariables(entity);
							}
							if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).random_sound == 1) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.zombie.ambient")), SoundSource.HOSTILE, 1, 1);
									} else {
										_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.zombie.ambient")), SoundSource.HOSTILE, 1, 1, false);
									}
								}
							} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).random_sound == 2) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.creeper.primed")), SoundSource.HOSTILE, 1, 1);
									} else {
										_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.creeper.primed")), SoundSource.HOSTILE, 1, 1, false);
									}
								}
							} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).random_sound == 3) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.lava.ambient")), SoundSource.BLOCKS, 1, 1);
									} else {
										_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.lava.ambient")), SoundSource.BLOCKS, 1, 1, false);
									}
								}
							} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).random_sound == 4) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.skeleton.ambient")), SoundSource.HOSTILE, 1, 1);
									} else {
										_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.skeleton.ambient")), SoundSource.HOSTILE, 1, 1, false);
									}
								}
							} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).random_sound == 5) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.sculk_shrieker.shriek")), SoundSource.BLOCKS, 1, 1);
									} else {
										_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.sculk_shrieker.shriek")), SoundSource.BLOCKS, 1, 1, false);
									}
								}
							}
							final int tick2 = ticks;
							CurseoftheabyssMod.queueServerWork(tick2, () -> {
								if (timedlooptotal > timedloopiterator + 1) {
									timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
								}
							});
						}
					}.timedLoop(0, 5, 60);
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.minPosY = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY;
						_vars.syncPlayerVariables(entity);
					}
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.curse = false;
						_vars.syncPlayerVariables(entity);
					}
				} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).layer == 4) {
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.curse = true;
						_vars.syncPlayerVariables(entity);
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 3));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CurseoftheabyssModMobEffects.BLEEDING, 200, 0));
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.minPosY = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY;
						_vars.syncPlayerVariables(entity);
					}
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.curse = false;
						_vars.syncPlayerVariables(entity);
					}
				} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).layer == 5) {
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.curse = true;
						_vars.syncPlayerVariables(entity);
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 255));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 255));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 255));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 255));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 1));
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.minPosY = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY;
						_vars.syncPlayerVariables(entity);
					}
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.curse = false;
						_vars.syncPlayerVariables(entity);
					}
				} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).layer == 6) {
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.curse = true;
						_vars.syncPlayerVariables(entity);
					}
					if (entity instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
						_livingEntity27.getAttribute(Attributes.MAX_HEALTH)
								.setBaseValue(((entity instanceof LivingEntity _livingEntity26 && _livingEntity26.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity26.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) - 5));
					entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.MAGIC)), 0);
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.if_6_layer_damaged_death = true;
						_vars.syncPlayerVariables(entity);
					}
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.minPosY = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY;
						_vars.syncPlayerVariables(entity);
					}
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.curse = false;
						_vars.syncPlayerVariables(entity);
					}
				} else if (entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).layer == 7) {
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.curse = true;
						_vars.syncPlayerVariables(entity);
					}
					entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.MAGIC)), 1000);
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.minPosY = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES).curPosY;
						_vars.syncPlayerVariables(entity);
					}
					{
						CurseoftheabyssModVariables.PlayerVariables _vars = entity.getData(CurseoftheabyssModVariables.PLAYER_VARIABLES);
						_vars.curse = false;
						_vars.syncPlayerVariables(entity);
					}
				}
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
