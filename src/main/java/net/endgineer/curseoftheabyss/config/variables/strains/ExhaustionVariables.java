package net.endgineer.curseoftheabyss.config.variables.strains;

import java.util.Arrays;
import net.endgineer.curseoftheabyss.config.spec.ModCommonConfig;

public class ExhaustionVariables {
    public final double[][] LAYER = {
        {0, 0},
        Arrays.stream(ModCommonConfig.STRAINS.EXHAUSTION.LAYER1_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.EXHAUSTION.LAYER2_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.EXHAUSTION.LAYER3_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.EXHAUSTION.LAYER4_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.EXHAUSTION.LAYER5_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.EXHAUSTION.LAYER6_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.EXHAUSTION.LAYER7_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray()
    };
}
