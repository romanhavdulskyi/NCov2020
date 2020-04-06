package com.demo.app.ncov2020.logic.Callback;

import com.demo.app.ncov2020.logic.Disease.Disease;
import com.demo.app.ncov2020.logic.MainPart.Country;
import com.demo.app.ncov2020.logic.MainPart.GameStatev2;
import com.demo.app.ncov2020.logic.cure.GlobalCure;

import java.util.List;

public class GameStateForEntity{
    private final int id;
    private final String playerGUID;
    private long amountOfPeople;
    private long deadPeople = 0;
    private long infectedPeople = 0;
    private long healthyPeople = 0;
    private List<Country> countries;
    private Disease disease;
    private GlobalCure globalCure;

    public GameStateForEntity(GameStatev2 gameStatev2) {
        this.id = gameStatev2.getId();
        this.playerGUID = gameStatev2.getPlayerGUID();
        this.countries = gameStatev2.getCountries();
        this.disease = gameStatev2.getDisease();
        this.globalCure = gameStatev2.getGlobalCure();
        amountOfPeople+=gameStatev2.getAmountOfPeople();
        deadPeople+=gameStatev2.getDeadPeople();
        infectedPeople+=gameStatev2.getInfectedPeople();
        healthyPeople+=gameStatev2.getHealthyPeople();
    }

}
