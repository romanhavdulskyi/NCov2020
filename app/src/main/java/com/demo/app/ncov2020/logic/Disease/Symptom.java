package com.demo.app.ncov2020.logic.Disease;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Objects;

public class Symptom  implements  Cloneable{
    private final String name;
    private final String description;
    private final double infectivity;
    private final long severity;
    private final double lethality;
    private final int pricePoints;

    public Symptom(String name, String description, double infectivity, long severity, double lethality,int pricePoints) {
        this.name = name;
        this.description = description;
        this.infectivity = infectivity;
        this.severity = severity;
        this.lethality = lethality;
        this.pricePoints= pricePoints;
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

    public int getPricePoints() {
        return pricePoints;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Symptom)) return false;
        Symptom symptom = (Symptom) o;
        return Double.compare(symptom.getInfectivity(), getInfectivity()) == 0 &&
                getSeverity() == symptom.getSeverity() &&
                Double.compare(symptom.getLethality(), getLethality()) == 0 &&
                Objects.equals(getName(), symptom.getName()) &&
                Objects.equals(getDescription(), symptom.getDescription());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getInfectivity(), getSeverity(), getLethality());
    }
}
