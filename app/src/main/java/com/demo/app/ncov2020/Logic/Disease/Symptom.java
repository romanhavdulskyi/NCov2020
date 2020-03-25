package com.demo.app.ncov2020.Logic.Disease;

public class Symptom {
    private final String name;
    private final String description;
    private final int infectivity;
    private final int severity;
    private final int lethality;

    public Symptom(String name, String description, int infectivity, int severity, int lethality) {
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

    public int getInfectivity() {
        return infectivity;
    }

    public int getSeverity() {
        return severity;
    }

    public int getLethality() {
        return lethality;
    }
}
