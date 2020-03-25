package com.demo.app.ncov2020.Logic;

import com.demo.app.ncov2020.Logic.Disease.Disease;

import java.util.List;

public class GameModel implements Everydayble{
    private final int amountOfPeople;
    private int deadPeople;
    private int infectedPeople;
    private int heathyPeople;
    List<Country> countries;
    private Disease disease;

    public GameModel(int amountOfPeople, int deadPeople, int infectedPeople, int heathyPeople, Disease disease) {
        this.amountOfPeople = amountOfPeople;
        this.deadPeople = deadPeople;
        this.infectedPeople = infectedPeople;
        this.heathyPeople = heathyPeople;
        this.disease = disease;
    }

    public void addCountry(Country country){
        countries.add(country);
    }

    @Override
    public void pastOneUnit() {
        for (Country country:countries) {
            country.pastOneUnit();
        }
    }

    public int getDeadPeople() {
        deadPeople=0;
        for (Country country:countries) {
            deadPeople+=country.getDeadPeople();
        }
        return deadPeople;
    }

    public int getInfectedPeople() {
        infectedPeople=0;
        for (Country country:countries) {
            infectedPeople+=country.getInfectedPeople();
        }
        return infectedPeople;
    }

    public int getHeathyPeople() {
        heathyPeople=0;
        for (Country country:countries) {
            heathyPeople+=country.getHeathyPeople();
        }
        return heathyPeople;
    }
    }



}
