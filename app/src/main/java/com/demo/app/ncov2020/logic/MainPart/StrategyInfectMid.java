package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Callback.CallbackType;
import com.demo.app.ncov2020.logic.Country.Country;

import java.util.List;

public class StrategyInfectMid extends BaseStrategy{
    static final int pricePoints=5;
    @Override
    boolean confirmBuy() {
        return (GameStateReali.getInstance().getUpgradePointsCalc().buyStuff(this));
    }

    @Override
    public CallbackType execute(List<Country> countries) {
        if (!confirmBuy()) return CallbackType.STRATEGYFAILED;
        for(Country country : countries){
            applyAdditionalOnCountry(country,1000000);
        }
        return CallbackType.STRATEGYEXECUTED;
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
    @Override
    public int getPricePoints() {
        return pricePoints;
    }
}
