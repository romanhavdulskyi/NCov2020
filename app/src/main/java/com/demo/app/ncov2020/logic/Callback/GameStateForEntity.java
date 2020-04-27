package com.demo.app.ncov2020.logic.Callback;

import com.demo.app.ncov2020.game.Game;
import com.demo.app.ncov2020.game.GameProvider;
import com.demo.app.ncov2020.logic.Country.CountryComposite;
import com.demo.app.ncov2020.logic.Disease.Disease;
import com.demo.app.ncov2020.logic.MainPart.GameStateReali;
import com.demo.app.ncov2020.logic.cure.GlobalCure;

import org.jetbrains.annotations.NotNull;

public class GameStateForEntity{
    private final int id;
    private final String playerGUID;
    private long amountOfPeople;
    private long deadPeople = 0;
    private long infectedPeople = 0;
    private long healthyPeople = 0;
    private CountryComposite countryComposite;
    private Disease disease;
    private GlobalCure globalCure;

    public GameStateForEntity(GameStateReali gameStateReali) {
        this.id = gameStateReali.getId();
        this.playerGUID = gameStateReali.getPlayerGUID();
        this.countryComposite = gameStateReali.getCountryComposite();
        this.disease = gameStateReali.getDisease();
        this.globalCure = gameStateReali.getGlobalCure();
        amountOfPeople += gameStateReali.getAmountOfPeople();
        deadPeople += gameStateReali.getDeadPeople();
        infectedPeople += gameStateReali.getInfectedPeople();
        healthyPeople += gameStateReali.getHealthyPeople();
    }

    public CountryComposite getCountryComposite() {
        return countryComposite;
    }

    public void setCountryComposite(CountryComposite countryComposite) {
        this.countryComposite = countryComposite;
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

    public long getHealthyPeople() {
        return healthyPeople;
    }

    public void setHealthyPeople(long healthyPeople) {
        this.healthyPeople = healthyPeople;
    }

    public long getInfectedPeople() {
        return infectedPeople;
    }

    public void setInfectedPeople(long infectedPeople) {
        this.infectedPeople = infectedPeople;
    }

    public long getDeadPeople() {
        return deadPeople;
    }

    public void setDeadPeople(long deadPeople) {
        this.deadPeople = deadPeople;
    }

    public long getAmountOfPeople() {
        return amountOfPeople;
    }

    public void setAmountOfPeople(long amountOfPeople) {
        this.amountOfPeople = amountOfPeople;
    }

    public int getId() {
        return id;
    }

    public String getPlayerGUID() {
        return playerGUID;
    }
}
