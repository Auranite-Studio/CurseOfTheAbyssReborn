package net.endgineer.curseoftheabyss.core;

import java.util.Map;

import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
import net.endgineer.curseoftheabyss.common.Abyss;
import net.endgineer.curseoftheabyss.common.CurseCapability;
import net.endgineer.curseoftheabyss.common.CurseProvider;
import net.endgineer.curseoftheabyss.config.spec.ModCommonConfig;
import net.endgineer.curseoftheabyss.config.variables.ModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
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
            
            if(player.level.dimension().location().getPath() == "overworld" && Abyss.layer(player.getY()) >= ModCommonConfig.CUSTOM.FINALITY.LAYER.get()) {
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
                if(Abyss.layer(curse.getLowestDepth()) > ModVariables.STRAIN.YIELD_LAYER && player.getHealth()+event.getAmount() > curse.getConstitution()) {
                    event.setAmount((float) curse.getConstitution() - player.getHealth());
                }
            });
        }
    }

    @SubscribeEvent
    public static void onServerChatEvent(ServerChatEvent event) {
        event.getPlayer().getCapability(CurseProvider.CURSE).ifPresent(curse -> {
            event.setComponent(new TranslatableComponent((event.getPlayer().level.dimension().location().getPath() == "overworld" ? event.getPlayer().getY() : 0)+";"+curse.getDerangement()+";"+event.getUsername()+";"+event.getMessage()));
        });
    }

    @SubscribeEvent
    public static void onClientChatReceived(ClientChatReceivedEvent event) {
        Minecraft minecraft = Minecraft.getInstance();

        String[] constituents = event.getMessage().getString().split(";", 4);

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
                double strain = curse.tick(event.player);

                event.player.getFoodData().addExhaustion((float) strain);
                if(ModList.get().isLoaded("thirst")) {
                    event.player.getCapability(ModCapabilities.PLAYER_THIRST).ifPresent(thirst -> {
                        thirst.addExhaustion(event.player, (float) strain);
                    });
                }

                if(ModList.get().isLoaded("sanitydim")) {
                    event.player.getCapability(SanityProvider.CAP).ifPresent(sanity -> {
                        if(curse.getDerangement() > sanity.getSanity()) {
                            sanity.setSanity((float) curse.getDerangement());
                        }

                        if(Abyss.layer(curse.getLowestDepth()) > 2) {
                            SanityProcessor.addSanity(sanity, (float) (strain/20.0), (ServerPlayer) event.player);
                        }
                    });
                }

                if(Abyss.layer(curse.getLowestDepth()) > 3) {
                    event.player.hurt(new DamageSource(CurseOfTheAbyss.MOD_ID+"_cursed"), (float) strain);
                }
            });
        }
    }
}