package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Disease.Disease;
import com.demo.app.ncov2020.logic.EverydayAble;
import com.demo.app.ncov2020.logic.MainPart.Country;
import com.demo.app.ncov2020.logic.cure.GlobalCure;

import java.util.List;
import java.util.Objects;

public class GameStatev2 implements EverydayAble {
    private final int id;
    private final String playerGUID;
    private long amountOfPeople;
    private long deadPeople = 0;
    private long infectedPeople = 0;
    private long healthyPeople = 0;
    private List<Country> countries;
    private Disease disease;
    private GlobalCure globalCure;

    private boolean timePassed=false;

    public GameStatev2(int id, String playerGUID, List<Country> countries, Disease disease, GlobalCure globalCure) {
        this.id = id;
        this.playerGUID = playerGUID;
        this.countries = countries;
        this.disease = disease;
        this.globalCure = globalCure;
        for(Country country :countries){
            amountOfPeople+=country.getAmountOfPeople();
            deadPeople+=country.getDeadPeople();
            infectedPeople+=country.getInfectedPeople();
            healthyPeople+=country.getHealthyPeople();
        }
    }

    @Override
    public void pastOneTimeUnit() {
        timePassed=true;
        for (Country country: Objects.requireNonNull(getCountries())) {
            Objects.requireNonNull(getDisease()).acceptCountry(country);
        }
        if(getInfectedPeople()>100_000)
            getGlobalCure().startWorkOnCure();
        getGlobalCure().pastOneTimeUnit();

        if(getDeadPeople()==getAmountOfPeople()){
            System.out.println("You won the game");
            return;
        }
        if(getInfectedPeople()==0) {
            System.out.println("You lose the game");
        }
        if(getGlobalCure().isCureCreated()) {
            System.out.println("You lose the game");
        }
        System.out.println(this);
    }

    public void addCountry(Country country){
        Objects.requireNonNull(getCountries()).add(country);
    }

    public long getDeadPeople() {
        if(!timePassed) return deadPeople;
        reCalcPeople();
        return deadPeople;
    }

    public long getInfectedPeople() {
        if(!timePassed) return infectedPeople;
        reCalcPeople();
        return infectedPeople;
    }

    public long getHealthyPeople() {
        if(!timePassed) return healthyPeople;
        reCalcPeople();
        return healthyPeople;
    }

    public void reCalcPeople(){
        healthyPeople =0;
        infectedPeople=0;
        deadPeople = 0;
        for (Country country: countries) {
            healthyPeople +=country.getHealthyPeople();
            deadPeople+=country.getDeadPeople();
            infectedPeople+=country.getInfectedPeople();
        }
    }

    public int getId() {
        return id;
    }

    public String getPlayerGUID() {
        return playerGUID;
    }

    public long getAmountOfPeople() {
        return amountOfPeople;
    }

    public void setAmountOfPeople(long amountOfPeople) {
        this.amountOfPeople = amountOfPeople;
    }

    public void setDeadPeople(long deadPeople) {
        this.deadPeople = deadPeople;
    }

    public void setInfectedPeople(long infectedPeople) {
        this.infectedPeople = infectedPeople;
    }

    public void setHealthyPeople(long healthyPeople) {
        this.healthyPeople = healthyPeople;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public GlobalCure getGlobalCure() {
        return globalCure;
    }

    public void setGlobalCure(GlobalCure globalCure) {
        this.globalCure = globalCure;
    }

    @Override
    public String toString() {
        return "GameStatev2{" +
                "id=" + id +
                ", playerGUID='" + playerGUID + '\'' +
                ", amountOfPeople=" + amountOfPeople +
                ", deadPeople=" + deadPeople +
                ", infectedPeople=" + infectedPeople +
                ", healthyPeople=" + healthyPeople +
                ", countries=" + countries +
                ", disease=" + disease +
                ", globalCure=" + globalCure +
                ", timePassed=" + timePassed +
                '}';
    }
}
