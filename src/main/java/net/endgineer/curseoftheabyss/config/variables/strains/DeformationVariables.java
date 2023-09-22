package net.endgineer.curseoftheabyss.config.variables.strains;

import net.endgineer.curseoftheabyss.config.spec.ModCommonConfig;

public class DeformationVariables {
    public final int DEFIANCE_LAYER = ModCommonConfig.STRAINS.DEFORMATION.DEFIANCE_LAYER.get();
    public final int YIELD_LAYER = ModCommonConfig.STRAINS.DEFORMATION.YIELD_LAYER.get();
    public final double ELASTICITY_MODULUS = ModCommonConfig.STRAINS.DEFORMATION.ELASTICITY_MODULUS.get();
    public final double STRAIN_HARDENING_COEFFICIENT = ModCommonConfig.STRAINS.DEFORMATION.STRAIN_HARDENING_COEFFICIENT.get();
}
