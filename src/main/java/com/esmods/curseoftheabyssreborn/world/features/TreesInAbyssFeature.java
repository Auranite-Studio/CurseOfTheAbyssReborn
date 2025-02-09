
package com.esmods.curseoftheabyssreborn.world.features;

import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.WorldGenLevel;

import com.esmods.curseoftheabyssreborn.procedures.TreesInAbyssDopolnitielnoieUsloviieGienieratsiiProcedure;

public class TreesInAbyssFeature extends TreeFeature {
	public TreesInAbyssFeature() {
		super(TreeConfiguration.CODEC);
	}

	public boolean place(FeaturePlaceContext<TreeConfiguration> context) {
		WorldGenLevel world = context.level();
		int x = context.origin().getX();
		int y = context.origin().getY();
		int z = context.origin().getZ();
		if (!TreesInAbyssDopolnitielnoieUsloviieGienieratsiiProcedure.execute(world, x, y, z))
			return false;
		return super.place(context);
	}
}
