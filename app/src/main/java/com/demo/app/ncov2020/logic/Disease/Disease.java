package com.demo.app.ncov2020.logic.Disease;

import com.demo.app.ncov2020.logic.Country;
import com.demo.app.ncov2020.logic.Handler;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Disease extends RealmObject implements Diseaseable{
    private String name;
    private double infectivity;
    private long severity;
    private long lethality;
    private ArrayList<Symptom> symptoms;
    private ArrayList<Transmission> transmissions;
    private ArrayList<Ability> abilities;
    private Handler handTrans;
    private Handler handAbilit;

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
        double thisCountryMedicineFight=getInfectivity()-country.getCureKoef(); //country has a chance to cure

        long perUnitInfected = (long) Math.min((thisCountryMedicineFight * country.getInfectedPeople() + 1), country.getHealthyPeople());
        long perUnitDead = lethality;
        country.setHealthyPeople(Math.max(country.getHealthyPeople() - perUnitInfected, 0));
        country.setInfectedPeople(country.getInfectedPeople() + perUnitInfected);
        country.setDeadPeople(country.getDeadPeople()+ Math.min(perUnitDead, country.getInfectedPeople()));
        country.setInfectedPeople(country.getInfectedPeople() - Math.min(perUnitDead, country.getInfectedPeople()));

        for (Transmission transmission : transmissions){
            transmission.getType().getHandler().handle(country);
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
