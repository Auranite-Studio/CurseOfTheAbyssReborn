package net.endgineer.curseoftheabyss.config.spec.custom.sections;

import net.minecraftforge.common.ForgeConfigSpec;

public class FinalitySection {
    public final int DEFAULT_LAYER = 1;
    public final int MINIMUM_LAYER = 1;
    public final int MAXIMUM_LAYER = 8;
    public final ForgeConfigSpec.ConfigValue<Integer> LAYER;

    public FinalitySection(ForgeConfigSpec.Builder builder) {
        builder.push("FINALITY");

        LAYER = builder.comment(
            "Should the Abyss have a sense of finality? If enabled, death at and beyond this layer results in the permanent loss of all items on the player.\n"+
            "Values: [ "+MINIMUM_LAYER+", "+MAXIMUM_LAYER+" ]\n"+
            "Default: "+DEFAULT_LAYER+"\nDisable: 8")
            .define("LAYER", DEFAULT_LAYER);
        
        builder.pop();
    }
}
