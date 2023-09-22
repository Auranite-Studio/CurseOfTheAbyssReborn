package net.endgineer.curseoftheabyss.config.variables.strains;

import java.util.Arrays;
import net.endgineer.curseoftheabyss.config.spec.ModCommonConfig;

public class HallucinationVariables {
    public final double[][] LAYER = {
        {0, 0},
        Arrays.stream(ModCommonConfig.STRAINS.HALLUCINATION.LAYER1_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.HALLUCINATION.LAYER2_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.HALLUCINATION.LAYER3_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.HALLUCINATION.LAYER4_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.HALLUCINATION.LAYER5_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.HALLUCINATION.LAYER6_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.HALLUCINATION.LAYER7_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray()
    };
}
