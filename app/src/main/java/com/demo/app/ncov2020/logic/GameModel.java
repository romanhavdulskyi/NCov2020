package com.demo.app.ncov2020.logic;

import com.demo.app.ncov2020.data.room_data.CommonCountry;
import com.demo.app.ncov2020.data.room_data.GameCountry;
import com.demo.app.ncov2020.data.room_data.GameState;
import com.demo.app.ncov2020.logic.Disease.Ability;
import com.demo.app.ncov2020.logic.Disease.Disease;
import com.demo.app.ncov2020.logic.Disease.Symptom;
import com.demo.app.ncov2020.logic.Disease.Transmission;
import com.demo.app.ncov2020.logic.Disease.TypeAbility;
import com.demo.app.ncov2020.logic.Disease.TypeTrans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GameModel implements EverydayAble {
    public static GameModel instance;
    private final GameState gameState;

    public GameModel(GameState gameState) {
        this.gameState = gameState;
        instance=this;
    }
    public static GameModel getInstance(){
        return instance;
    }

    public void addCountry(Country country){
        Objects.requireNonNull(gameState.getCountries()).add(country);
    }

    @Override
    public void pastOneTimeUnit() {
        for (GameCountry country: Objects.requireNonNull(gameState.getCountries())) {
            Objects.requireNonNull(gameState.getDisease()).acceptCountry(country);
        }
        if(getDeadPeople()==gameState.getAmountOfPeople()) System.out.println("You won the game");
        if(getInfectedPeople()==0) System.out.println("You lose the game");
    }

    public long getAmountOfPeople(){
        return gameState.getAmountOfPeople();
    }

    public long getDeadPeople() {
        long deadPeople = 0;
        for (GameCountry country: Objects.requireNonNull(gameState.getCountries())) {
            if(country.getDeadPeople() ==  null)
                continue;
            deadPeople+=country.getDeadPeople();
        }
        return deadPeople;
    }

    public long getInfectedPeople() {
        long infectedPeople=0;
        for (GameCountry country: Objects.requireNonNull(gameState.getCountries())) {
            if(country.getInfectedPeople() ==  null)
                continue;
            infectedPeople+=country.getInfectedPeople();
        }
        return infectedPeople;
    }

    public long getHealthyPeople() {
        long healthyPeople =0;
        for (Country country: Objects.requireNonNull(gameState.getCountries())) {
            healthyPeople +=country.getHealthyPeople();
        }
        return healthyPeople;
    }

    @Override
    public String toString() {
        return "GameModel{" +
                "amountOfPeople=" + gameState.getAmountOfPeople() +
                ", deadPeople=" + getDeadPeople() +
                ", infectedPeople=" + getInfectedPeople() +
                ", healthyPeople=" + getHealthyPeople() +
                ", countries=" + gameState.getCountries() +
                ", disease=" + gameState.getDisease() +
                '}';
    }

    static public void testGameModel(){
        List<Country> countries = new ArrayList<>();
        Country ukraine =new Country("Ukraine",42_000_000,false,true,true).beginInfection();
        Country italy = new Country("Italy",60_000_000,true,true,true);
        Country china= new Country("China",1_400_000_000,false,true,true);
        ukraine.addPathAir(china);
        ukraine.addPathSea(italy);

        countries.add(ukraine);
        countries.add(italy);
        countries.add(china);
        Disease disease = new Disease("nCov2019");
        disease.addSymptom(new Symptom("Pnevmonia","Hard to breathe",2,4,0));
        disease.addAbility(new Ability("Antibiotics1","Can survive Level1 antibiotics", TypeAbility.ANTIBIOTICS1));
        disease.addTransmission(new Transmission("Plains transmission","You will be able to infect by plains", TypeTrans.AIR));
        disease.addTransmission(new Transmission("Plains transmission","You will be able to infect by plains", TypeTrans.GROUND));
        disease.addTransmission(new Transmission("Plains transmission","You will be able to infect by plains", TypeTrans.WATER));
        GameModel gameModel = new GameModel(new GameState(1,"1",countries,disease));
        for (int i=0;i<100;i++) {
            System.out.println(gameModel);
            gameModel.pastOneTimeUnit();
        }
        System.out.println(gameModel);
    }

}
