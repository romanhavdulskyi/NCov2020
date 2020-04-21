package com.demo.app.ncov2020.logic.Callback;

import com.demo.app.ncov2020.logic.Country.CountryComposite;
import com.demo.app.ncov2020.logic.Disease.Disease;
import com.demo.app.ncov2020.logic.MainPart.GameStateReali;
import com.demo.app.ncov2020.logic.cure.GlobalCure;

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
        amountOfPeople+= gameStateReali.getAmountOfPeople();
        deadPeople+= gameStateReali.getDeadPeople();
        infectedPeople+= gameStateReali.getInfectedPeople();
        healthyPeople+= gameStateReali.getHealthyPeople();
    }

}
