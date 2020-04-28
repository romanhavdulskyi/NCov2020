package com.demo.app.ncov2020.logic.Callback;

import androidx.annotation.NonNull;

import com.demo.app.ncov2020.game.Game;
import com.demo.app.ncov2020.game.GameProvider;
import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.Country.CountryComposite;
import com.demo.app.ncov2020.logic.Disease.Disease;
import com.demo.app.ncov2020.logic.MainPart.GameStateReali;
import com.demo.app.ncov2020.logic.cure.GlobalCure;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameStateForEntity implements Cloneable{
    private final int id;
    private final String playerGUID;
    private long amountOfPeople;
    private long deadPeople = 0;
    private long infectedPeople = 0;
    private long healthyPeople = 0;
    private CountryComposite countryComposite;
    private HashMap<String, Country> infectedCountries = new HashMap<>();
    private Disease disease;
    private GlobalCure globalCure;
    private Date date;
    private int upgradePoints = 0; //TODO: add point when user watches mem and when infects country and when countries changes state

    public GameStateForEntity(GameStateReali gameStateReali) {
        this.id = gameStateReali.getId();
        this.playerGUID =  gameStateReali.getPlayerGUID();
        this.amountOfPeople =  gameStateReali.getAmountOfPeople();
        this.deadPeople =  gameStateReali.getDeadPeople();
        this.infectedPeople =  gameStateReali.getInfectedPeople();
        this.healthyPeople =  gameStateReali.getHealthyPeople();
        this.countryComposite =  gameStateReali.getCountryComposite();
        this.infectedCountries =  gameStateReali.getInfectedCountries();
        this.disease =  gameStateReali.getDisease();
        this.globalCure =  gameStateReali.getGlobalCure();
        this.date =  gameStateReali.getCalendar().getTime();
        this.upgradePoints =  gameStateReali.getUpgradePoints();
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

    public long getDeadPeople() {
        return deadPeople;
    }

    public void setDeadPeople(long deadPeople) {
        this.deadPeople = deadPeople;
    }

    public long getInfectedPeople() {
        return infectedPeople;
    }

    public void setInfectedPeople(long infectedPeople) {
        this.infectedPeople = infectedPeople;
    }

    public long getHealthyPeople() {
        return healthyPeople;
    }

    public void setHealthyPeople(long healthyPeople) {
        this.healthyPeople = healthyPeople;
    }

    public CountryComposite getCountryComposite() {
        return countryComposite;
    }

    public void setCountryComposite(CountryComposite countryComposite) {
        this.countryComposite = countryComposite;
    }

    public HashMap<String, Country> getInfectedCountries() {
        return infectedCountries;
    }

    public void setInfectedCountries(HashMap<String, Country> infectedCountries) {
        this.infectedCountries = infectedCountries;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUpgradePoints() {
        return upgradePoints;
    }

    public void setUpgradePoints(int upgradePoints) {
        this.upgradePoints = upgradePoints;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        HashMap<String, Country> infectedCountriesCopy = new HashMap<>(infectedCountries);
        Object copy =  super.clone();
        ((GameStateForEntity)copy).infectedCountries = infectedCountriesCopy;
        return copy;
    }
}
