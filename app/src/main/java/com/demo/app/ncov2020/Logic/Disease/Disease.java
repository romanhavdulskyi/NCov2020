package com.demo.app.ncov2020.Logic.Disease;

import java.util.List;

public class Disease {
    private String name;
    private double infectivity;
    private long severity;
    private long lethality;
    private List<Symptom> symptoms;
    private List<Transmission> transmissions;
    private List<Ability> abilities;

    public Disease(String name) {
        this.name = name;
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

    public long getLethality() {
        return lethality;
    }
}
