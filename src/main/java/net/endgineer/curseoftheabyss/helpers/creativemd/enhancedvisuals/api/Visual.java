package net.endgineer.curseoftheabyss.helpers.creativemd.enhancedvisuals.api;

import com.mojang.blaze3d.vertex.PoseStack;

import net.endgineer.curseoftheabyss.helpers.creativemd.enhancedvisuals.api.type.VisualType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Visual {
    public final VisualType type;
    public final VisualHandler handler;
    
    private float opacity;
    
    public final boolean endless;
    
    private boolean displayed = false;
    
    public int variant;
    
    public Visual(VisualType type, VisualHandler handler, int variant) {
        this.type = type;
        this.handler = handler;
        this.variant = variant;
        this.endless = true;
    }
    
    public void setOpacityInternal(float opacity) {
        this.opacity = opacity;
    }
    
    public float getOpacityInternal() {
        return opacity;
    }
    
    public float getOpacity() {
        return handler.opacity * opacity * type.opacity;
    }
    
    public boolean displayed() {
        return displayed;
    }
    
    public void addToDisplay() {
        displayed = true;
    }
    
    public void removeFromDisplay() {
        displayed = false;
    }
    
    @OnlyIn(Dist.CLIENT)
    public void render(PoseStack stack, TextureManager manager, int screenWidth, int screenHeight, float partialTicks) {
        type.render(handler, this, manager, screenWidth, screenHeight, partialTicks);
    }
    
    public boolean isVisible() {
        return type.isVisible(handler, this);
    }
    
    public int getWidth(int screenWidth) {
        return screenWidth;
    }
    
    public int getHeight(int screenHeight) {
        return screenHeight;
    }
}
