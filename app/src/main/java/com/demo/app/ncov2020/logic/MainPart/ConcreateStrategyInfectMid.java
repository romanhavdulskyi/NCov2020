package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Country.Country;

import java.util.List;

public class ConcreateStrategyInfectMid implements Strategy{
    @Override
    public void execute(List<Country> countries) {
        for(Country country : countries){
            applyAdditionalOnCountry(country,10000);
        }
    }

    private void applyAdditionalOnCountry(Country country, long infectivity){
        if (!country.isInfected()) return;
        calcInfectedPeople(country,infectivity);
        calcDeadPeople(country);
        calcHealthyPeople(country);
    }

    private void calcInfectedPeople(Country country, long infectivity){
        long perTimeUnitInfected =(long) Math.min(infectivity, country.getHealthyPeople());
        country.setInfectedPeople(country.getInfectedPeople()+perTimeUnitInfected);
    }
    private void calcDeadPeople(Country country){
    }
    private void calcHealthyPeople(Country country){
        country.setHealthyPeople(country.getAmountOfPeople()-country.getInfectedPeople()-country.getDeadPeople());
    }
}
