package com.demo.app.ncov2020.Logic.Disease;

import com.demo.app.ncov2020.Logic.Country;

import java.util.ArrayList;
import java.util.List;

public class Disease implements Diseaseable{
    private String name;
    private double infectivity;
    private long severity;
    private long lethality;
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

    @Override
    public void acceptCountry(Country country){
        if (!country.isInfected()) return;
        double thisCountryMedicineFight=getInfectivity()-country.getCureCoef(); //country has a chance to cure

        long perUnitInfected = (long) Math.min((thisCountryMedicineFight * country.getInfectedPeople() + 1), country.getHeathyPeople());
        long perUnitDead = lethality;
        country.setHeathyPeople(Math.max(country.getHeathyPeople() - perUnitInfected, 0));
        country.setInfectedPeople(country.getInfectedPeople() + perUnitInfected);
        country.setDeadPeople(country.getDeadPeople()+ Math.min(perUnitDead, country.getInfectedPeople()));
        country.setInfectedPeople(country.getInfectedPeople() - Math.min(perUnitDead, country.getInfectedPeople()));

        if(country.getPercentOfInfectedPeople()>0.2){
            boolean luck = Math.random()*100 > 80;
            country.infectAnotherCountryBy(TypeTrans.getRandom());
        }
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
