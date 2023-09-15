package net.endgineer.curseoftheabyss.config.spec.abyss.sections;

import net.minecraftforge.common.ForgeConfigSpec;

public class AbyssSection {
    public final int DEFAULT_SPAN = 64;
    public final int MINIMUM_SPAN = 64;
    public final int MAXIMUM_SPAN = 2016;
    public final ForgeConfigSpec.ConfigValue<Integer> SPAN;

    public AbyssSection(ForgeConfigSpec.Builder builder) {
        builder.push("ABYSS");

        SPAN = builder.comment(
            "The size of the Abyss. The Abyss will always start below y = 0 and end at the negative of this value.\n"+
            "Values: { n | n >= 64 ∧ n <= 2016 ∧ n % 16 == 0 }\n"+
            "Default: "+DEFAULT_SPAN)
            .define("SPAN", DEFAULT_SPAN, value -> value != null && (Integer) value >= MINIMUM_SPAN && (Integer) value <= MAXIMUM_SPAN && (Integer) value % 16 == 0);
        
        builder.pop();
    }
}
