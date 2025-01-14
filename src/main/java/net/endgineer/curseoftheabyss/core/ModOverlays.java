//package net.endgineer.curseoftheabyss.core;
//
//import net.minecraft.resources.ResourceLocation;
//import com.mojang.blaze3d.vertex.PoseStack;
//
//import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
//import net.endgineer.curseoftheabyss.client.CurseData;
//import net.endgineer.curseoftheabyss.config.spec.ModCommonConfig;
//import net.minecraft.client.Minecraft;
//import com.mojang.blaze3d.systems.RenderSystem;
//import net.minecraft.client.renderer.GameRenderer;
//import net.minecraftforge.fml.ModList;
//import top.theillusivec4.curios.api.CuriosApi;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//@OnlyIn(Dist.CLIENT)
//public class ModOverlays {
//    public static final ResourceLocation FIELD = new ResourceLocation(CurseOfTheAbyss.MOD_ID, "textures/gui/field.png");
//
//    static Minecraft minecraft = Minecraft.getInstance();
//
//    public static final IIngameOverlay OVERLAY = OverlayRegistry.registerOverlayTop(CurseOfTheAbyss.MOD_ID + "_overlay", (gui, poseStack, partialTicks, screenWidth, screenHeight) -> {
//        if (!minecraft.options.hideGui && (minecraft.player.isHolding(ModItems.STAR_COMPASS.get()) || ModList.get().isLoaded("curios") && !CuriosApi.getCuriosHelper().findFirstCurio(minecraft.player, ModItems.STAR_COMPASS.get()).isEmpty())) {
//            gui.setupOverlayRenderState(true, false);
//            render(gui, screenWidth, screenHeight, poseStack);
//        }
//    });
//
//    public static void render(ForgeIngameGui gui, int screenWidth, int screenHeight, PoseStack poseStack) {
//        minecraft.getProfiler().push(CurseOfTheAbyss.MOD_ID + "_overlay");
//
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//
//        int xPos = screenWidth / 2;
//        int yPos = 2;
//
//        ModOverlays.drawCenteredBarWithHead(poseStack, FIELD, CurseData.getField(), xPos+ModCommonConfig.OVERLAY.FIELD.XOFFSET.get(), yPos+ModCommonConfig.OVERLAY.FIELD.YOFFSET.get(), 0xFFFFFF, 0x000000);
//
//        RenderSystem.disableBlend();
//        RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
//
//        minecraft.getProfiler().pop();
//    }
//
//    public static void drawCenteredBarWithHead(PoseStack poseStack, ResourceLocation TEXTURE, double value, int x, int y, int fontColor, int fontOutlineColor) {
//        RenderSystem.setShaderTexture(0, TEXTURE);
//
//        //RENDER BAR
//        ForgeIngameGui.blit(poseStack, x-91, y, 0, 0, 182, 5, 182, 5);
//
//        //PREPARE
//        y -= 2;
//
//        //RENDER HEAD
//        x += (int) (Math.ceil(value*179) - 90);
//		minecraft.font.draw(poseStack, ":", x + 1, y, fontOutlineColor);
//		minecraft.font.draw(poseStack, ":", x - 1, y, fontOutlineColor);
//		minecraft.font.draw(poseStack, ":", x, y + 1, fontOutlineColor);
//		minecraft.font.draw(poseStack, ":", x, y - 1, fontOutlineColor);
//		minecraft.font.draw(poseStack, ":", x, y, fontColor);
//	}
//
//    public static void register() {}
//}