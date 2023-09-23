package net.endgineer.curseoftheabyss.common;

import java.io.Serializable;

public class Strains implements Serializable {
    private static double[] log_normal_distribution = {
        0.00354476258391,
        0.00517349994344,
        0.00762534304554,
        0.01152761051910,
        0.01814284029300,
        0.03023014666000,
        0.05449568310140,
        0.10968923895700,
        0.25763431137900,
        0.50193656351700
    };

    private double strain_deformation;
    private double[] buffer_deformation;

    private double strain_deprivation;
    private double[] buffer_deprivation;

    private double strain_exhaustion;
    private double[] buffer_exhaustion;

    private double strain_hallucination;
    private double[] buffer_hallucination;

    private double strain_nausea;
    private double[] buffer_nausea;

    private double strain_numbness;
    private double[] buffer_numbness;

    private double stress;
    private int buffer_tick;
    private int stress_tick;

    public Strains() {
        this.strain_deformation = 0;
        this.buffer_deformation = new double[10];

        this.strain_deprivation = 0;
        this.buffer_deprivation = new double[10];

        this.strain_exhaustion = 0;
        this.buffer_exhaustion = new double[10];

        this.strain_hallucination = 0;
        this.buffer_hallucination = new double[10];

        this.strain_nausea = 0;
        this.buffer_nausea = new double[10];

        this.strain_numbness = 0;
        this.buffer_numbness = new double[10];

        this.stress = 0;
        this.buffer_tick = 0;
        this.stress_tick = 0;
    }

    public double observeDeformation(boolean alter) {
        double temp = this.strain_deformation;
        if(alter) { this.strain_deformation = 0; }
        return temp;
    }

    public double observeDeprivation(boolean alter) {
        double temp = this.strain_deprivation;
        if(alter) { this.strain_deprivation -= Math.min(this.strain_deprivation, 0.05); }
        return temp;
    }

    public double observeExhaustion(boolean alter) {
        double temp = this.strain_exhaustion;
        if(alter) { this.strain_exhaustion = 0; }
        return temp;
    }

    public double observeHallucination(boolean alter) {
        double temp = this.strain_hallucination;
        if(alter) { this.strain_hallucination = 0; }
        return temp;
    }

    public double observeNausea(boolean alter) {
        double temp = this.strain_nausea;
        if(alter) { this.strain_nausea -= Math.min(this.strain_nausea, 0.05); }
        return temp;
    }

    public double observeNumbness(boolean alter) {
        double temp = this.strain_numbness;
        if(alter) { this.strain_numbness -= Math.min(this.strain_numbness, 0.05); }
        return temp;
    }

    public void tick(double stress, double y_lowest, double field) {
        this.stress += stress;
        this.stress_tick++;

        int buffer_second = (int) Math.ceil(this.buffer_tick / 20.0);

        if(this.stress_tick == 20) {
            for(int second = 0; second < 10; second++) {
                int buffer_step = (buffer_second + second) % 10;
                double convolved_stress = Strains.log_normal_distribution[second] * this.stress;

                this.buffer_deformation[buffer_step] += convolved_stress * Abyss.strain_deformation(field, y_lowest);
                this.buffer_deprivation[buffer_step] += convolved_stress * Abyss.strain_deprivation(field, y_lowest);
                this.buffer_exhaustion[buffer_step] += convolved_stress * Abyss.strain_exhaustion(field, y_lowest);
                this.buffer_hallucination[buffer_step] += convolved_stress * Abyss.strain_hallucination(field, y_lowest);
                this.buffer_nausea[buffer_step] += convolved_stress * Abyss.strain_nausea(field, y_lowest);
                this.buffer_numbness[buffer_step] += convolved_stress * Abyss.strain_numbness(field, y_lowest);
            }

            this.stress = 0;
            this.stress_tick = 0;
        }

        if(this.buffer_tick % 20 == 0) {
            this.strain_deformation += this.buffer_deformation[buffer_second];
            this.buffer_deformation[buffer_second] = 0;
            this.strain_deprivation += this.buffer_deprivation[buffer_second];
            this.buffer_deprivation[buffer_second] = 0;
            this.strain_exhaustion += this.buffer_exhaustion[buffer_second];
            this.buffer_exhaustion[buffer_second] = 0;
            this.strain_hallucination += this.buffer_hallucination[buffer_second];
            this.buffer_hallucination[buffer_second] = 0;
            this.strain_nausea += this.buffer_nausea[buffer_second];
            this.buffer_nausea[buffer_second] = 0;
            this.strain_numbness += this.buffer_numbness[buffer_second];
            this.buffer_numbness[buffer_second] = 0;
        }

        this.buffer_tick = ++this.buffer_tick % 200;
    }

    public boolean empty() {
        for(int i = 0; i < 10; i++) {
            if(this.buffer_deformation[i] > 0) { return false; }
            if(this.buffer_deprivation[i] > 0) { return false; }
            if(this.buffer_exhaustion[i] > 0) { return false; }
            if(this.buffer_hallucination[i] > 0) { return false; }
            if(this.buffer_nausea[i] > 0) { return false; }
            if(this.buffer_numbness[i] > 0) { return false; }
        }

        if(this.strain_deformation > 0) { return false; }
        if(this.strain_deprivation > 0) { return false; }
        if(this.strain_exhaustion > 0) { return false; }
        if(this.strain_hallucination > 0) { return false; }
        if(this.strain_nausea > 0) { return false; }
        if(this.strain_numbness > 0) { return false; }

        return true;
    }
}
