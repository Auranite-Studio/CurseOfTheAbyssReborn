package net.endgineer.curseoftheabyss.config.variables.strains;

import java.util.Arrays;
import net.endgineer.curseoftheabyss.config.spec.ModCommonConfig;

public class NauseaVariables {
    public final double[][] LAYER = {
        {0, 0},
        Arrays.stream(ModCommonConfig.STRAINS.NAUSEA.LAYER1_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.NAUSEA.LAYER2_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.NAUSEA.LAYER3_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.NAUSEA.LAYER4_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.NAUSEA.LAYER5_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.NAUSEA.LAYER6_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray(),
        Arrays.stream(ModCommonConfig.STRAINS.NAUSEA.LAYER7_RANGE.get().replaceAll("\\s|\\[|\\]", "").split(",")).mapToDouble(Double::parseDouble).toArray()
    };
}
