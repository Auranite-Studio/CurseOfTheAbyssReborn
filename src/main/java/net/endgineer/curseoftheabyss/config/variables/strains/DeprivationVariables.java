package net.endgineer.curseoftheabyss.config.variables.strains;

import java.util.Arrays;
import net.endgineer.curseoftheabyss.config.spec.ModCommonConfig;

public class DeprivationVariables {
    public final double[][] LAYER = {
        {0, 0},
        Arrays.stream(ModCommonConfig.STRAINS.DEPRIVATION.LAYER1_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.DEPRIVATION.LAYER2_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.DEPRIVATION.LAYER3_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.DEPRIVATION.LAYER4_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.DEPRIVATION.LAYER5_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.DEPRIVATION.LAYER6_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.DEPRIVATION.LAYER7_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray()
    };
}
