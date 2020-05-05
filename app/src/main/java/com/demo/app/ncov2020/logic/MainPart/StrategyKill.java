package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Country.Country;

import java.util.List;

public class StrategyKill implements Strategy{
    @Override
    public void execute(List<Country> countries) {
        for(Country country : countries){
            applyAdditionalOnCountry(country,100);
        }
    }

    private void applyAdditionalOnCountry(Country country, long kill){
        if (!country.isInfected()) return;
        calcInfectedPeople(country);
        calcDeadPeople(country,kill);
        calcHealthyPeople(country);
    }

    private void calcInfectedPeople(Country country){
    }
    private void calcDeadPeople(Country country, long kill){
        long perTimeUnitDead =(long) Math.min(kill, country.getInfectedPeople());
        country.setDeadPeople(country.getDeadPeople()+perTimeUnitDead);
        country.setInfectedPeople(country.getInfectedPeople()-perTimeUnitDead);
    }
    private void calcHealthyPeople(Country country){
        country.setHealthyPeople(country.getAmountOfPeople()-country.getInfectedPeople()-country.getDeadPeople());
    }
}
