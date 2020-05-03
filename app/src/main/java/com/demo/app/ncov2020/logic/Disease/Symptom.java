package com.demo.app.ncov2020.logic.Disease;

import androidx.annotation.NonNull;

public class Symptom  implements  Cloneable{
    private final String name;
    private final String description;
    private final double infectivity;
    private final long severity;
    private final double lethality;

    public Symptom(String name, String description, double infectivity, long severity, double lethality) {
        this.name = name;
        this.description = description;
        this.infectivity = infectivity;
        this.severity = severity;
        this.lethality = lethality;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getInfectivity() {
        return infectivity;
    }

    public long getSeverity() {
        return severity;
    }

    public double getLethality() {
        return lethality;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
