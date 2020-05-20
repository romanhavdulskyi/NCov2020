package com.demo.app.ncov2020.logic.Abilities;

import com.demo.app.ncov2020.logic.BaseHandler;
import com.demo.app.ncov2020.logic.Country.Climate;
import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.Disease.Transmission;

public class HandlerHot extends BaseHandler {
    @Override
    public void handle(Country country) {
        if(country.getClimate()== Climate.HOT){
            applyAdditionalOnCountry(country,0.07);
        }
    }

    private void applyAdditionalOnCountry(Country country, double infectivity){
        if (!country.isInfected()) return;
        calcInfectedPeople(country,infectivity);
        calcDeadPeople(country);
        calcHealthyPeople(country);
    }
    private void calcInfectedPeople(Country country, double infectivity){
        long perTimeUnitInfected =(long) Math.min(Math.ceil(infectivity*country.getInfectedPeople()), country.getHealthyPeople());
        country.setInfectedPeople(country.getInfectedPeople()+perTimeUnitInfected);
    }
    private void calcDeadPeople(Country country){
    }
    private void calcHealthyPeople(Country country){
        country.setHealthyPeople(country.getAmountOfPeople()-country.getInfectedPeople()-country.getDeadPeople());
    }
}
