package net.endgineer.curseoftheabyss.core;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.vertex.PoseStack;

import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
import net.endgineer.curseoftheabyss.client.CurseData;
import net.endgineer.curseoftheabyss.config.spec.ModCommonConfig;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModOverlays implements IGuiOverlay {
    public static final ResourceLocation FIELD = new ResourceLocation(CurseOfTheAbyss.MOD_ID, "textures/gui/field.png");
    
    static Minecraft minecraft = Minecraft.getInstance();

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        guiGraphics.pose().pushPose();

        if (!minecraft.options.hideGui && (minecraft.player.isHolding(ModItems.STAR_COMPASS.get()) || ModList.get().isLoaded("curios") && !CuriosApi.getCuriosHelper().findFirstCurio(minecraft.player, ModItems.STAR_COMPASS.get()).isEmpty())) {
            gui.setupOverlayRenderState(true, false);
            render(gui, guiGraphics, partialTick, screenWidth, screenHeight);
        }
    };

    public static void render(GuiGraphics gui, int screenWidth, int screenHeight) {
        minecraft.getProfiler().push(CurseOfTheAbyss.MOD_ID + "_overlay");
        
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        int xPos = screenWidth / 2;
        int yPos = 2;

        ModOverlays.drawCenteredBarWithHead(gui, FIELD, CurseData.getField(), xPos+ModCommonConfig.OVERLAY.FIELD.XOFFSET.get(), yPos+ModCommonConfig.OVERLAY.FIELD.YOFFSET.get(), 0xFFFFFF, 0x000000);

        RenderSystem.disableBlend();

//        RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);

        minecraft.getProfiler().pop();
    }

    public static void drawCenteredBarWithHead(GuiGraphics poseStack, ResourceLocation TEXTURE, double value, int x, int y, int fontColor, int fontOutlineColor) {
        
        //RENDER BAR
        poseStack.blit(TEXTURE, x-91, y, 0, 0, 182, 5, 182, 5);

        //PREPARE
        y -= 2;

        //RENDER HEAD
        x += (int) (Math.ceil(value*179) - 90);
		poseStack.drawString(minecraft.font, ":", x + 1, y, fontOutlineColor);
        poseStack.drawString(minecraft.font, ":", x - 1, y, fontOutlineColor);
        poseStack.drawString(minecraft.font, ":", x, y + 1, fontOutlineColor);
        poseStack.drawString(minecraft.font, ":", x, y - 1, fontOutlineColor);
        poseStack.drawString(minecraft.font, ":", x, y, fontColor);
	}

    public static void register() {}
}