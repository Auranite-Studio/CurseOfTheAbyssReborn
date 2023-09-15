package net.endgineer.curseoftheabyss.utils.andrew6rant.mc237017fix.mixin;

/*
 * Translated directly from https://github.com/Andrew6rant/MC-237017-Fix by Andrew Grant (MIT License)
 */

import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({NoiseBasedChunkGenerator.class})
public class NoiseBasedChunkGeneratorMixin {
	/**
	 * @author Andrew6rant (Andrew Grant)
	 * @reason Remove the hardcoded -54 lava sea level
	 */
	@Shadow
	protected Holder<NoiseGeneratorSettings> settings;

	@Shadow
	private final Aquifer.FluidPicker globalFluidPicker = (x, y, z) -> { return new Aquifer.FluidStatus(this.settings.value().seaLevel(), this.settings.value().defaultFluid()); };
}
