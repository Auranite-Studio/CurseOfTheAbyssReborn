package net.endgineer.curseoftheabyss.helpers.creativemd.enhancedvisuals.common.addon.curseoftheabyss;

import net.minecraft.resources.ResourceLocation;
import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
import net.endgineer.curseoftheabyss.helpers.creativemd.enhancedvisuals.common.visual.VisualRegistry;

public class CurseOfTheAbyssShaders {
    
    public static BlurHandler BLUR;
    
    public static void load() {
        VisualRegistry.registerHandler(new ResourceLocation(CurseOfTheAbyss.MOD_ID, "blur"), BLUR = new BlurHandler());
    }
}
