package net.endgineer.curseoftheabyss.config.variables.strains;

import net.endgineer.curseoftheabyss.config.spec.ModCommonConfig;

public class DeformationVariables {
    public final int DEFIANCE_LAYER = Integer.parseInt(((String) ModCommonConfig.STRAINS.DEFORMATION.BOUNDARY_LAYERS.get()).replaceAll("\\s|\\[|\\]", "").split(",")[0]);
    public final int YIELD_LAYER = Integer.parseInt(((String) ModCommonConfig.STRAINS.DEFORMATION.BOUNDARY_LAYERS.get()).replaceAll("\\s|\\[|\\]", "").split(",")[1]);
    public final double ELASTICITY_MODULUS = ModCommonConfig.STRAINS.DEFORMATION.ELASTICITY_MODULUS.get();
    public final double STRAIN_HARDENING_COEFFICIENT = ModCommonConfig.STRAINS.DEFORMATION.STRAIN_HARDENING_COEFFICIENT.get();
}
