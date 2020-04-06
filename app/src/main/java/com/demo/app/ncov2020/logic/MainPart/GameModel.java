package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Callback.Callback;
import com.demo.app.ncov2020.logic.Callback.CallbackType;
import com.demo.app.ncov2020.logic.Callback.ConcreateCallback;
import com.demo.app.ncov2020.logic.Disease.Ability;
import com.demo.app.ncov2020.logic.Disease.Disease;
import com.demo.app.ncov2020.logic.Disease.Symptom;
import com.demo.app.ncov2020.logic.Disease.Transmission;
import com.demo.app.ncov2020.logic.Disease.TypeAbility;
import com.demo.app.ncov2020.logic.Disease.TypeTrans;
import com.demo.app.ncov2020.logic.EverydayAble;
import com.demo.app.ncov2020.logic.Callback.GameStateForEntity;
import com.demo.app.ncov2020.logic.cure.GlobalCure;

import java.util.ArrayList;


public class GameModel implements EverydayAble {
    GameStatev2 gameStatev2;

    private Callback callback;
    public static GameModel instance;

    public GameModel(GameStatev2 gameStatev2) {
        this.gameStatev2 = gameStatev2;
        instance = this;
    }

    public GameModel(GameStatev2 gameStatev2, Callback callback) {
        this.gameStatev2 = gameStatev2;
        this.callback = callback;
        instance = this;
    }

    public static GameModel getInstance(){
        if(instance==null) throw new RuntimeException("Створи спочатку один об'єкт через конструктор");
        return instance;
    }

    @Override
    public void pastOneTimeUnit() {
        gameStatev2.pastOneTimeUnit();
        callback.callingBack(new GameStateForEntity(gameStatev2), CallbackType.TIMEPASS);
    }

    public void addSymptom(Symptom symptom){
        gameStatev2.getDisease().addSymptom(symptom);
        callback.callingBack(new GameStateForEntity(gameStatev2),CallbackType.SYMPTOMADD);
    }

    public void addTransmission(Transmission transmission){
        gameStatev2.getDisease().addTransmission(transmission);
        callback.callingBack(new GameStateForEntity(gameStatev2),CallbackType.TRANSADD);
    }

    public void addAbility(Ability ability){
        gameStatev2.getDisease().addAbility(ability);
        callback.callingBack(new GameStateForEntity(gameStatev2),CallbackType.ABILITYADD);
    }

    static public void testGameModel(){
        ArrayList<Country> countries = new ArrayList<>();
        Country ukraine =new Country("Ukraine",42_000_000,false, Climate.NORMAL,true,true).beginInfection();
        Country italy = new Country("Italy",60_000_000,true,Climate.NORMAL,true,true);
        Country china= new Country("China",1_400_000_000,false,Climate.HOT,true,true);
        ukraine.addPathAir(china);
        ukraine.addPathSea(italy);

        countries.add(ukraine);
        countries.add(italy);
        countries.add(china);
        Disease disease = new Disease("nCov2019");
        GameModel gameModel = new GameModel(new GameStatev2(1,"1",countries,disease,new GlobalCure(360)),new ConcreateCallback());
        gameModel.addSymptom(new Symptom("Pnevmonia","Hard to breathe",2,4,0));
        gameModel.addAbility(new Ability("Antibiotics1","Can survive Level1 antibiotics", TypeAbility.ANTIBIOTICS1));
        gameModel.addTransmission(new Transmission("Plains transmission","You will be able to infect by plains", TypeTrans.AIR));
        gameModel.addTransmission(new Transmission("Plains transmission","You will be able to infect by plains", TypeTrans.GROUND));
        gameModel.addTransmission(new Transmission("Plains transmission","You will be able to infect by plains", TypeTrans.WATER));
        for (int i=0;i<50;i++) {
            System.out.println(gameModel);
            gameModel.pastOneTimeUnit();
        }
        disease.addSymptom(new Symptom("Kill all","People started dying",2,4,100_000_000));
        for (int i=0;i<100;i++) {
            System.out.println(gameModel);
            gameModel.pastOneTimeUnit();
        }
        System.out.println(gameModel);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public GameStatev2 getGameStatev2() {
        return gameStatev2;
    }

    public void setGameStatev2(GameStatev2 gameStatev2) {
        this.gameStatev2 = gameStatev2;
    }
}
