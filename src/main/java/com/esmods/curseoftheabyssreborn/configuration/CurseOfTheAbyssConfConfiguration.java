package com.esmods.curseoftheabyssreborn.configuration;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CurseOfTheAbyssConfConfiguration {
	public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
	public static final ModConfigSpec SPEC;

	public static final ModConfigSpec.ConfigValue<Double> CURSE_HEIGHT;
	static {
		BUILDER.push("BASIC_SETTINGS");
		CURSE_HEIGHT = BUILDER.comment("Maximum height relative to the minimum height at which the curse does not work (default: 10)").define("curse_height", (double) 10);
		BUILDER.pop();

		SPEC = BUILDER.build();
	}

}
