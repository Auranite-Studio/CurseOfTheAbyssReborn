package net.endgineer.curseoftheabyss.mixin;

/*
 * Translated directly from https://github.com/Andrew6rant/MC-237017-Fix by Andrew Grant (MIT License)
 *
 * MIT License
 *
 * Copyright (c) 2023 Andrew Grant
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({NoiseBasedChunkGenerator.class})
public class NoiseBasedChunkGeneratorMixin {
    //    /**
//     * @author Andrew6rant (Andrew Grant)
//     * @reason Remove the hardcoded -54 lava sea level
//     */
    @Overwrite
    private static Aquifer.FluidPicker createFluidPicker(NoiseGeneratorSettings p_249264_) {
        Aquifer.FluidStatus $$1 = new Aquifer.FluidStatus(-54, Blocks.LAVA.defaultBlockState());
        int $$2 = p_249264_.seaLevel();
        Aquifer.FluidStatus $$3 = new Aquifer.FluidStatus($$2, p_249264_.defaultFluid());
        Aquifer.FluidStatus $$4 = new Aquifer.FluidStatus(DimensionType.MIN_Y * 2, Blocks.AIR.defaultBlockState());
        return (p_224274_, p_224275_, p_224276_) -> {
            return p_224275_ < Math.min(-1020, $$2) ? $$1 : $$3;
        };
    }
}