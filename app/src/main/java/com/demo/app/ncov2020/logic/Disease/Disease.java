package com.demo.app.ncov2020.logic.Disease;

import androidx.annotation.NonNull;

import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.MainPart.Priceable;

import java.util.ArrayList;
import java.util.List;

public class Disease implements Cloneable {
    private String name;
    private double infectivity;
    private long severity;
    private double lethality;
    private List<Symptom> symptoms;
    private List<Transmission> transmissions;
    private List<Ability> abilities;

    public Disease(String name) {
        this.name = name;
        symptoms = new ArrayList<>();
        transmissions = new ArrayList<>();
        abilities = new ArrayList<>();
    }

    public void addSymptom(Symptom symptom) {
        symptoms.add(symptom);
        infectivity += symptom.getInfectivity();
        severity += symptom.getSeverity();
        lethality += symptom.getLethality();
    }

    public boolean containsSymptom(Symptom symptom) {
        return symptoms.contains(symptom);
    }

    public void addTransmission(Transmission transmission) {
        transmissions.add(transmission);
    }

    public boolean containsTransByType(TypeTrans typeTrans) {
        for (Transmission transmission : transmissions) {
            if (transmission.getType() == typeTrans) return true;
        }
        return false;
    }

    public void addAbility(Ability ability) {
        abilities.add(ability);
    }

    public boolean containsAbilityByType(TypeAbility typeAbility) {
        for (Ability ability : abilities) {
            if (ability.getTypeAbility() == typeAbility) return true;
        }
        return false;
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

    public void setLethality(double lethality) {
        this.lethality = lethality;
    }

    public void setSeverity(long severity) {
        this.severity = severity;
    }

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public List<Transmission> getTransmissions() {
        return transmissions;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    @Override
    public String toString() {
        return "Disease{" +
                "name='" + name + '\'' +
                ", infectivity=" + infectivity +
                ", severity=" + severity +
                ", lethality=" + lethality +
                ", symptoms=" + symptoms +
                ", transmissions=" + transmissions +
                ", abilities=" + abilities +
                '}';
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Disease disease = (Disease) super.clone();
        List<Symptom> symptomsCopy = new ArrayList<>(symptoms.size());
        for(Symptom symptom : symptoms)
            symptomsCopy.add((Symptom) symptom.clone());
        List<Transmission> transmissionsCopy = new ArrayList<>(transmissions.size());
        for(Transmission transmission : transmissions)
            transmissionsCopy.add((Transmission) transmission.clone());
        List<Ability> abilitiesCopy = new ArrayList<>(abilities.size());
        for(Ability ability : abilities)
            abilitiesCopy.add((Ability) ability.clone());
        disease.symptoms = symptomsCopy;
        disease.abilities = abilitiesCopy;
        disease.transmissions = transmissionsCopy;
        return disease;
    }
}
