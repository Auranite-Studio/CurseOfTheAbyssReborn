package net.endgineer.curseoftheabyss.config.variables.abyss;

import net.endgineer.curseoftheabyss.config.spec.ModCommonConfig;

public class StrainVariables {
    public final int YIELD_LAYER = ModCommonConfig.ABYSS.STRAIN.YIELD_LAYER.get();
    public final double ELASTICITY_MODULUS = ModCommonConfig.ABYSS.STRAIN.ELASTICITY_MODULUS.get();
    public final double STRAIN_HARDENING_COEFFICIENT = ModCommonConfig.ABYSS.STRAIN.STRAIN_HARDENING_COEFFICIENT.get();
}
