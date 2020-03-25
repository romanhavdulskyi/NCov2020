package com.demo.app.ncov2020.Logic;

import com.demo.app.ncov2020.Logic.Disease.Disease;

import java.util.List;

public class GameModel implements Everydayble{
    private final long amountOfPeople;
    private long deadPeople=0;
    private long infectedPeople=0;
    private long heathyPeople;
    List<Country> countries;
    private Disease disease;

    public GameModel(List<Country> countries, Disease disease) {
        long amountOfPeople1;
        this.countries=countries;
        this.disease=disease;
        long tempAmountOfPeople=0;
        for(Country country : countries){
            tempAmountOfPeople = tempAmountOfPeople + country.getAmountOfPeople();
        }
        amountOfPeople = tempAmountOfPeople;
    }



    public void addCountry(Country country){
        countries.add(country);
    }

    @Override
    public void pastOneUnit() {
        for (Country country:countries) {
            country.pastOneUnit(disease);
        }
    }

    public long getAmountOfPeople(){
        return amountOfPeople;
    }

    public long getDeadPeople() {
        deadPeople=0;
        for (Country country:countries) {
            deadPeople+=country.getDeadPeople();
        }
        return deadPeople;
    }

    public long getInfectedPeople() {
        infectedPeople=0;
        for (Country country:countries) {
            infectedPeople+=country.getInfectedPeople();
        }
        return infectedPeople;
    }

    public long getHeathyPeople() {
        heathyPeople=0;
        for (Country country:countries) {
            heathyPeople+=country.getHeathyPeople();
        }
        return heathyPeople;
    }

}
