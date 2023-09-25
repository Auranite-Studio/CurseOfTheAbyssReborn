package net.endgineer.curseoftheabyss.helpers.creativemd.enhancedvisuals.client.render;

import java.util.Collection;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

import net.endgineer.curseoftheabyss.helpers.creativemd.enhancedvisuals.api.Visual;
import net.endgineer.curseoftheabyss.helpers.creativemd.enhancedvisuals.api.type.VisualType;
import net.endgineer.curseoftheabyss.helpers.creativemd.enhancedvisuals.client.VisualManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraftforge.client.ForgeHooksClient;

public class EVRenderer {
    
    private static Minecraft mc = Minecraft.getInstance();
    
    private static int framebufferWidth;
    private static int framebufferHeight;
    
    public static boolean reloadResources = false;
    
    public static void render() {
        if (true) {
            if (reloadResources) {
                for (VisualType type : VisualType.getTypes()) {
                    type.loadResources(mc.getResourceManager());
                }
                reloadResources = false;
            }
            
            if (!(mc.screen instanceof DeathScreen)) {
                
                float partialTicks = Minecraft.getInstance().getFrameTime();
                
                if (mc.getMainRenderTarget().width != framebufferWidth || mc.getMainRenderTarget().height != framebufferHeight) {
                    for (VisualType type : VisualType.getTypes())
                        type.resize(mc.getMainRenderTarget());
                    framebufferWidth = mc.getMainRenderTarget().width;
                    framebufferHeight = mc.getMainRenderTarget().height;
                }
                
                int screenWidth = mc.getWindow().getWidth();
                int screenHeight = mc.getWindow().getHeight();
                
                TextureManager manager = mc.getTextureManager();
                
                RenderSystem.clear(256, Minecraft.ON_OSX);
                Matrix4f matrix4f = Matrix4f.orthographic(0.0F, screenWidth, 0.0F, screenHeight, 1000.0F, ForgeHooksClient.getGuiFarPlane());
                RenderSystem.setProjectionMatrix(matrix4f);
                PoseStack stack = RenderSystem.getModelViewStack();
                stack.pushPose();
                stack.setIdentity();
                stack.translate(0.0D, 0.0D, 1000F - ForgeHooksClient.getGuiFarPlane());
                RenderSystem.applyModelViewMatrix();
                Lighting.setupFor3DItems();
                
                RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
                RenderSystem.enableTexture();
                RenderSystem.disableDepthTest();
                RenderSystem.depthMask(false);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                
                RenderSystem.disableBlend();
                RenderSystem.disableTexture();
                RenderSystem.resetTextureMatrix();
                renderVisuals(stack, VisualManager.visuals(), manager, screenWidth, screenHeight, partialTicks);
                
                RenderSystem.applyModelViewMatrix();
                
                Window window = mc.getWindow();
                RenderSystem.clear(256, Minecraft.ON_OSX);
                RenderSystem.setProjectionMatrix(Matrix4f
                        .orthographic(0.0F, (float) (window.getWidth() / window.getGuiScale()), 0.0F, (float) (window.getHeight() / window.getGuiScale()), 1000.0F, ForgeHooksClient
                                .getGuiFarPlane()));
                stack.popPose();
                RenderSystem.applyModelViewMatrix();
                Lighting.setupFor3DItems();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getMainRenderTarget().bindWrite(true);
            }
        }
    }
    
    private static void renderVisuals(PoseStack stack, Collection<Visual> visuals, TextureManager manager, int screenWidth, int screenHeight, float partialTicks) {
        if (visuals == null || visuals.isEmpty())
            return;
        try {
            
            for (Visual visual : visuals)
                if (visual.isVisible())
                    visual.render(stack, manager, screenWidth, screenHeight, partialTicks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
