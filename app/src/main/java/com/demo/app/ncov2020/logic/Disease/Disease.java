package com.demo.app.ncov2020.logic.Disease;

import com.demo.app.ncov2020.logic.Country.Country;

import java.util.ArrayList;
import java.util.List;

public class Disease{
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

    public void addSymptom(Symptom symptom){
        symptoms.add(symptom);
        infectivity+=symptom.getInfectivity();
        severity+=symptom.getSeverity();
        lethality+=symptom.getLethality();
    }

    public boolean containsSymptom(Symptom symptom){
        return symptoms.contains(symptom);
    }

    public void addTransmission(Transmission transmission){
        transmissions.add(transmission);
    }

    public boolean containsTransByType(TypeTrans typeTrans){
        for (Transmission transmission : transmissions){
            if(transmission.getType()==typeTrans) return true;
        }
        return false;
    }

    public void addAbility(Ability ability){
        abilities.add(ability);
    }

    public boolean containsAbilityByType(TypeAbility typeAbility){
        for (Ability ability : abilities){
            if(ability.getTypeAbility()==typeAbility) return true;
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
}
