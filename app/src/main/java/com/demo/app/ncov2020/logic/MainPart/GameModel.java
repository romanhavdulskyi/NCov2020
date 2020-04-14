package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Abilities.HandlerAntibiotics1;
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
import com.demo.app.ncov2020.logic.Country.Climate;
import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.Country.CountryBuilder;
import com.demo.app.ncov2020.logic.Country.Hronology;
import com.demo.app.ncov2020.logic.Country.MedicineLevel;
import com.demo.app.ncov2020.logic.Transsmission.HandlerAIR;
import com.demo.app.ncov2020.logic.Transsmission.HandlerGround;
import com.demo.app.ncov2020.logic.Transsmission.HandlerWater;
import com.demo.app.ncov2020.logic.cure.GlobalCure;

import java.util.ArrayList;


public class GameModel implements EverydayAble {
    GameStatev2 gameStatev2;

    private Callback callback;
    public static GameModel instance;

    private GameModel(GameStatev2 gameStatev2) {
        this.gameStatev2 = gameStatev2;
        instance = this;
    }

    private GameModel(GameStatev2 gameStatev2, Callback callback) {
        this.gameStatev2 = gameStatev2;
        this.callback = callback;
        instance = this;
    }

    public static GameModel init(GameStatev2 gameStatev2){
        if (instance != null){
            throw new AssertionError("You already initialized me");
        }
        instance = new GameModel(gameStatev2);
        return instance;
    }
    public static GameModel init(GameStatev2 gameStatev2, Callback callback){
        if (instance != null){
            throw new AssertionError("You already initialized me");
        }
        instance = new GameModel(gameStatev2,callback);
        return instance;
    }

    public static GameModel getInstance(){
        if(instance==null) throw new RuntimeException("Create one object through init");
        return instance;
    }

    @Override
    public void pastOneTimeUnit() {
        CallbackType callbackType = gameStatev2.pastOneTimeUnit();
        callback.callingBack(new GameStateForEntity(gameStatev2), callbackType);
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
        Country ukraine = new CountryBuilder()
                .setName("Ukraine")
                .setAmountOfPeople(42_000_000)
                .setRich(false)
                .setClimate(Climate.NORMAL)
                .setMedicineLevel(MedicineLevel.FIRST)
                .setHronology(new Hronology(new ArrayList<>()))
                .buildCountry();
        Country italy = new CountryBuilder()
                .setName("Italy")
                .setAmountOfPeople(60_000_000)
                .setRich(true)
                .setClimate(Climate.NORMAL)
                .setMedicineLevel(MedicineLevel.SECOND)
                .setHronology(new Hronology(new ArrayList<>()))
                .buildCountry();
        Country china= new CountryBuilder()
                .setName("China")
                .setAmountOfPeople(1_400_000_000)
                .setRich(false)
                .setClimate(Climate.HOT)
                .setMedicineLevel(MedicineLevel.THIRD)
                .setHronology(new Hronology(new ArrayList<>()))
                .buildCountry();
        Country japan = new CountryBuilder()
                .setName("Japan")
                .setAmountOfPeople(78_000_000)
                .setRich(true)
                .setClimate(Climate.NORMAL)
                .setMedicineLevel(MedicineLevel.SECOND)
                .buildCountry();
        ukraine.addPathAir(china);
        ukraine.addPathSea(italy);
        china.addPathSea(japan);

        countries.add(ukraine);
        countries.add(italy);
        countries.add(china);
        Disease disease = new Disease("nCov2019");
        GameModel gameModel = init(GameStatev2.init(1,"1",countries,disease,new GlobalCure(1000000)),new ConcreateCallback());
        gameModel.addSymptom(new Symptom("Pnevmonia","Hard to breathe",2,4,0));
        gameModel.addSymptom(new Symptom("Cough","A-a-a-pchi",2,4,0));
        gameModel.addAbility(new Ability("Antibiotics1","Can survive Level1 antibiotics", TypeAbility.ANTIBIOTICS1, new HandlerAntibiotics1()));
        gameModel.addTransmission(new Transmission("Plains transmission","You will be able to infect by plains", TypeTrans.AIR,new HandlerAIR()));
        gameModel.addTransmission(new Transmission("Tourist transmission","You will be able to infect by tourists", TypeTrans.GROUND,new HandlerGround()));
        gameModel.addTransmission(new Transmission("Ship transmission","You will be able to infect by ships", TypeTrans.WATER,new HandlerWater()));
        ukraine.beginInfection();
        for (int i=0;i<50;i++) {
            System.out.println(gameModel);
            gameModel.pastOneTimeUnit();
        }
        disease.addSymptom(new Symptom("Kill all","People started dying",2,4,2));
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

    @Override
    public String toString() {
        return "GameModel{" +
                "gameStatev2=" + gameStatev2 +
                ", callback=" + callback +
                '}';
    }
}
