package net.endgineer.curseoftheabyss.config.variables.strains;

import java.util.Arrays;
import net.endgineer.curseoftheabyss.config.spec.ModCommonConfig;

public class NumbnessVariables {
    public final double[][] LAYER = {
        {0, 0},
        Arrays.stream(ModCommonConfig.STRAINS.NUMBNESS.LAYER1_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.NUMBNESS.LAYER2_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.NUMBNESS.LAYER3_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.NUMBNESS.LAYER4_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.NUMBNESS.LAYER5_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.NUMBNESS.LAYER6_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.NUMBNESS.LAYER7_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray()
    };
}
