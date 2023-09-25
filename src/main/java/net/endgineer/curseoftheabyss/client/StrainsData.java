package net.endgineer.curseoftheabyss.client;

public class StrainsData {
    private static double progress_deprivation;

    public static void update(double progress_deprivation) {
        StrainsData.progress_deprivation = progress_deprivation / 20.0;
    }

    public static double getDeprivationProgress() { return StrainsData.progress_deprivation; }
}
