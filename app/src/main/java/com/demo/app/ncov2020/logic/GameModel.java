package com.demo.app.ncov2020.logic;

import com.demo.app.ncov2020.logic.Disease.Ability;
import com.demo.app.ncov2020.logic.Disease.Disease;
import com.demo.app.ncov2020.logic.Disease.Symptom;
import com.demo.app.ncov2020.logic.Disease.Transmission;
import com.demo.app.ncov2020.logic.Disease.TypeAbility;
import com.demo.app.ncov2020.logic.Disease.TypeTrans;
import com.demo.app.ncov2020.logic.cure.GlobalCure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GameModel implements EverydayAble {
    final int id;
    final String playerGUID;
    long amountOfPeople;
    long deadPeople = 0;
    long infectedPeople = 0;
    long healthyPeople = 0;
    List<Country> countries;
    Disease disease;
    GlobalCure globalCure;

    private Callback callback;

    public static GameModel instance;

    public GameModel(int id, String playerGUID, List<Country> countries, Disease disease, GlobalCure globalCure) {
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
        instance=this;
    }

    public static GameModel getInstance(){
        if(instance==null) throw new RuntimeException("Створи спочатку один об'єкт через конструктор");
        return instance;
    }

    public void addCountry(Country country){
        Objects.requireNonNull(getCountries()).add(country);
    }

    @Override
    public void pastOneTimeUnit() {
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
        callback.callingBack("stateChanged");
    }

    public long getDeadPeople() {
        deadPeople = 0;
        for (Country country: countries) {
            deadPeople+=country.getDeadPeople();
        }
        return deadPeople;
    }

    public long getInfectedPeople() {
        infectedPeople=0;
        for (Country country: countries) {
            infectedPeople+=country.getInfectedPeople();
        }
        return infectedPeople;
    }

    public long getHealthyPeople() {
        healthyPeople =0;
        for (Country country: countries) {
            healthyPeople +=country.getHealthyPeople();
        }
        return healthyPeople;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    static public void testGameModel(){
        ArrayList<Country> countries = new ArrayList<>();
        Country ukraine =new Country("Ukraine",42_000_000,false,Climate.NORMAL,true,true).beginInfection();
        Country italy = new Country("Italy",60_000_000,true,Climate.NORMAL,true,true);
        Country china= new Country("China",1_400_000_000,false,Climate.HOT,true,true);
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
        GameModel gameModel = new GameModel(1,"1",countries,disease,new GlobalCure());
        gameModel.setCallback(new ConcreateCallback());
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
        return "GameModel{" +
                "id=" + id +
                ", playerGUID='" + playerGUID + '\'' +
                ", amountOfPeople=" + amountOfPeople +
                ", deadPeople=" + deadPeople +
                ", infectedPeople=" + infectedPeople +
                ", healthyPeople=" + healthyPeople +
                ", countries=" + countries +
                ", disease=" + disease +
                ", globalCure=" + globalCure +
                ", callback=" + callback +
                '}';
    }
}
