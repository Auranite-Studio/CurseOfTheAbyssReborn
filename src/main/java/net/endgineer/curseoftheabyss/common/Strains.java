package net.endgineer.curseoftheabyss.common;

import java.io.Serializable;

public class Strains implements Serializable {
    private static double[] log_normal_distribution = {
        0.00332245986159,
        0.00474201525312,
        0.00697360788608,
        0.01063420443520,
        0.01696323531110,
        0.02866300611990,
        0.05228157562260,
        0.10605341157700,
        0.24989514434800,
        0.51000122571000
    };

    private double[] buffer;
    private double stress;
    private int buffer_tick;
    private int stress_tick;

    public Strains() {
        this.buffer = new double[10];
        this.stress = 0;
        this.buffer_tick = 0;
        this.stress_tick = 0;
    }

    public double tick(double stress) {
        this.stress += stress;
        this.stress_tick++;

        if(this.stress_tick == 20) {
            for(int i = 0; i < 10; i++) {
                this.buffer[((int) Math.ceil(this.buffer_tick/20.0)+i) % 10] += Strains.log_normal_distribution[i]*this.stress;
            }

            this.stress = 0;
            this.stress_tick = 0;
        }

        double strain = 0;
        if(this.buffer_tick % 20 == 0) {
            strain = this.buffer[this.buffer_tick / 20];
            this.buffer[this.buffer_tick / 20] = 0;
        }

        this.buffer_tick = ++this.buffer_tick % 200;
        return strain;
    }

    public boolean handled()
    {
        for(int i = 0; i < 10; i++) {
            if(this.buffer[i] > 0) {
                return false;
            }
        }

        return true;
    }
}
