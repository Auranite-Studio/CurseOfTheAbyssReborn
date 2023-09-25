package net.endgineer.curseoftheabyss.core;

import java.util.Map;

import com.mojang.blaze3d.shaders.FogShape;

import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
import net.endgineer.curseoftheabyss.client.StrainsData;
import net.endgineer.curseoftheabyss.common.Abyss;
import net.endgineer.curseoftheabyss.common.CurseCapability;
import net.endgineer.curseoftheabyss.common.CurseProvider;
import net.endgineer.curseoftheabyss.config.spec.ModCommonConfig;
import net.endgineer.curseoftheabyss.config.variables.ModVariables;
import net.endgineer.curseoftheabyss.helpers.creativemd.enhancedvisuals.client.VisualManager;
import net.endgineer.curseoftheabyss.helpers.creativemd.enhancedvisuals.client.render.EVRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import croissantnova.sanitydim.SanityProcessor;
import croissantnova.sanitydim.capability.SanityProvider;
import dev.ghen.thirst.foundation.common.capability.ModCapabilities;

@Mod.EventBusSubscriber(modid = CurseOfTheAbyss.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(CurseProvider.CURSE).isPresent()) {
                event.addCapability(new ResourceLocation(CurseOfTheAbyss.MOD_ID, "properties"), new CurseProvider());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingDeath(LivingDeathEvent event) {
        Entity livingEntity = event.getEntityLiving();

        if(livingEntity instanceof Player) {
            Player player = ((Player) livingEntity);
            
            if(player.level.dimension().location().getPath() == "overworld" && Abyss.layer(player.getY()) > ModCommonConfig.CUSTOM.FINALITY.LAYER.get()) {
                player.getInventory().clearContent();
                
                if(ModList.get().isLoaded("curios")) {
                    CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> {
                        Map<String, ICurioStacksHandler> curios = handler.getCurios();
                        curios.forEach((id, stacksHandler) -> {
                            IDynamicStackHandler stacks = stacksHandler.getStacks();
                            for (int i = 0; i < stacks.getSlots(); i++) {
                                stacks.setStackInSlot(i, ItemStack.EMPTY);
                            }
                        });
                    });
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingHeal(LivingHealEvent event) {
        Entity livingEntity = event.getEntityLiving();

        if(livingEntity instanceof Player) {
            Player player = ((Player) livingEntity);
            
            player.getCapability(CurseProvider.CURSE).ifPresent(curse -> {
                if(Abyss.layer(curse.getLowestDepth()) > ModVariables.DEFORMATION.YIELD_LAYER && player.getHealth()+event.getAmount() > curse.getConstitution()) {
                    event.setAmount((float) curse.getConstitution() - player.getHealth());
                }
            });
        }
    }

    @SubscribeEvent
    public static void onServerChatEvent(ServerChatEvent event) {
        event.getPlayer().getCapability(CurseProvider.CURSE).ifPresent(curse -> {
            event.setComponent(new TranslatableComponent((event.getPlayer().level.dimension().location().getPath() == "overworld" ? event.getPlayer().getY() : 0)+"\n"+curse.getDerangement()+"\n"+event.getUsername()+"\n"+event.getMessage()));
        });
    }

    @SubscribeEvent
    public static void onClientChatReceived(ClientChatReceivedEvent event) {
        Minecraft minecraft = Minecraft.getInstance();

        String[] constituents = event.getMessage().getString().split("\n", 4);

        if(constituents.length != 4) { return; }
        
        double loss = Abyss.loss(Double.parseDouble(constituents[0]), minecraft.player.level.dimension().location().getPath() == "overworld" ? minecraft.player.getY() : 0);
        double corruption = Double.parseDouble(constituents[1])*loss;

        if(loss == 1 && ModCommonConfig.CUSTOM.LOSS.FULL.get()) {
            event.setCanceled(true);
        } else if(ModCommonConfig.CUSTOM.LOSS.PARTIAL.get()) {
            String modifiedUsername = "", modifiedMessage = "";

            for(int i = 0; i < constituents[2].length(); i++) {
                modifiedUsername += Math.random() < corruption ? "§k"+constituents[2].charAt(i)+"§r" : constituents[2].charAt(i);
            }

            for(int i = 0; i < constituents[3].length(); i++) {
                modifiedMessage += Math.random() < corruption ? "§k"+constituents[3].charAt(i)+"§r" : constituents[3].charAt(i);
            }

            event.setMessage(new TranslatableComponent("<"+modifiedUsername+"> "+modifiedMessage));
        } else {
            event.setMessage(new TranslatableComponent("<"+constituents[2]+"> "+constituents[3]));
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(CurseCapability.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER && event.phase.equals(TickEvent.Phase.END)) {
            event.player.getCapability(CurseProvider.CURSE).ifPresent(curse -> {
                curse.tick(event.player);

                if(ModList.get().isLoaded("thirst")) {
                    event.player.getCapability(ModCapabilities.PLAYER_THIRST).ifPresent(thirst -> {
                        thirst.addExhaustion(event.player, (float) curse.getStrains().observeExhaustion(false));
                    });
                }
                event.player.getFoodData().addExhaustion((float) curse.getStrains().observeExhaustion(true));

                curse.getStrains().observeNumbness(true);

                if(ModList.get().isLoaded("sanitydim")) {
                    event.player.getCapability(SanityProvider.CAP).ifPresent(sanity -> {
                        if(curse.getDerangement() > sanity.getSanity()) {
                            sanity.setSanity((float) curse.getDerangement());
                        }

                        SanityProcessor.addSanity(sanity, (float) curse.getStrains().observeHallucination(true), (ServerPlayer) event.player);
                    });
                }

                event.player.hurt(new DamageSource(CurseOfTheAbyss.MOD_ID+"_cursed"), (float) curse.getStrains().observeDeformation(true));
                
                curse.getStrains().observeDeprivation(true);
            });
        }
    }

    @SubscribeEvent
    public static void onRenderTick(RenderTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END)) {
            EVRenderer.render();
        }
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        if(StrainsData.getDeprivationProgress() == 1 && event.getSound().getSource() != SoundSource.MASTER) {
            event.setSound(null);
        }
    }

    @SubscribeEvent
    public static void onRenderFog(RenderFogEvent event) {
        if(StrainsData.getDeprivationProgress() > 0) {
            event.setCanceled(true);
            event.setFogShape(FogShape.SPHERE);
            event.scaleNearPlaneDistance((float) (1 - StrainsData.getDeprivationProgress()));
            event.scaleFarPlaneDistance((float) (1 - StrainsData.getDeprivationProgress()));
        }
    }

    @SubscribeEvent
    public static void onFogColor(FogColors color) {
        if(StrainsData.getDeprivationProgress() > 0) {
            color.setRed((float) (1 - StrainsData.getDeprivationProgress()));
            color.setBlue((float) (1 - StrainsData.getDeprivationProgress()));
            color.setGreen((float) (1 - StrainsData.getDeprivationProgress()));
        }
    }

    @SubscribeEvent
    public static void onRenderGameOverlayEvent(RenderGameOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        if(!mc.isPaused()) {
            if(StrainsData.getDeprivationProgress() == 1) {
                event.setCanceled(true);
            }
        }

        VisualManager.onTick(mc.player);
    }
}