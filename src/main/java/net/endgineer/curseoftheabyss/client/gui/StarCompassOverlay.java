package net.endgineer.curseoftheabyss.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
import net.endgineer.curseoftheabyss.client.CurseData;
import net.endgineer.curseoftheabyss.config.spec.ModCommonConfig;
import net.endgineer.curseoftheabyss.core.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;

public class StarCompassOverlay implements IGuiOverlay {
    public static final Minecraft minecraft = Minecraft.getInstance();
    public static final ResourceLocation FIELD = new ResourceLocation(CurseOfTheAbyss.MOD_ID, "textures/gui/field.png");
    public static final IGuiOverlay GUI = new StarCompassOverlay();

    @Override
    public void render(ForgeGui forgeGui, GuiGraphics gui,float var3, int screenWidth, int screenHeight) {
        boolean holdingCompass = minecraft.player.isHolding(ModItems.STAR_COMPASS.get());
        boolean hasCurioCompass = ModList.get().isLoaded("curios") && !CuriosApi.getCuriosHelper().findFirstCurio(minecraft.player, ModItems.STAR_COMPASS.get()).isEmpty();

        if (holdingCompass || hasCurioCompass) {
            minecraft.getProfiler().push(CurseOfTheAbyss.MOD_ID + "_overlay");

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);

            int xPos = screenWidth / 2;
            int yPos = 2;

            drawCenteredBarWithHead(gui, FIELD, CurseData.getField(), xPos + ModCommonConfig.OVERLAY.FIELD.XOFFSET.get(), yPos + ModCommonConfig.OVERLAY.FIELD.YOFFSET.get(), 0xFFFFFF, 0x000000);

            RenderSystem.disableBlend();
            RenderSystem.setShaderTexture(0, new ResourceLocation("textures/gui/container/stats_icons.png"));
            RenderSystem.setShaderTexture(0, new ResourceLocation("textures/gui/icons.png"));

            minecraft.getProfiler().pop();
        }
    }

    public static void drawCenteredBarWithHead(GuiGraphics gui, ResourceLocation texture, double value, int x, int y, int fontColor, int fontOutlineColor) {
        RenderSystem.setShaderTexture(0, texture);

        // RENDER BAR
        gui.blit(texture, x - 91, y, 0, 0, 182, 5, 182, 5);

        // PREPARE
        y -= 2;

        // RENDER HEAD
        x += (int) (Math.ceil(value * 179) - 90);
        gui.drawString(minecraft.font, ":", x + 1, y, fontOutlineColor);
        gui.drawString(minecraft.font, ":", x - 1, y, fontOutlineColor);
        gui.drawString(minecraft.font, ":", x, y + 1, fontOutlineColor);
        gui.drawString(minecraft.font, ":", x, y - 1, fontOutlineColor);
        gui.drawString(minecraft.font, ":", x, y, fontColor);
    }
}
