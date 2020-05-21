package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Callback.CallbackType;
import com.demo.app.ncov2020.logic.Callback.GameStateForEntity;
import com.demo.app.ncov2020.logic.Country.Component;
import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.Country.CountryComposite;
import com.demo.app.ncov2020.logic.Disease.Ability;
import com.demo.app.ncov2020.logic.Disease.Disease;
import com.demo.app.ncov2020.logic.Disease.Symptom;
import com.demo.app.ncov2020.logic.Disease.Transmission;
import com.demo.app.ncov2020.logic.cure.GlobalCure;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import androidx.annotation.NonNull;

public class GameStateReali implements ComponentDec, Originator<GameStateForEntity>, Cloneable {
    private final int id;
    private final String playerGUID;
    private long amountOfPeople;
    private long deadPeople = 0;
    private long infectedPeople = 0;
    private long healthyPeople = 0;
    private CountryComposite countryComposite;
    private HashMap<String,Country> infectedCountries = new LinkedHashMap<>();
    private Disease disease;
    private GlobalCure globalCure;
    private Calendar calendar;
    private UpgradePointsCalc upgradePointsCalc = new UpgradePointsCalc(); //TODO: add point when user watches mem
    private Strategy strategy = new StrategyNoAction();

    private boolean timePassed=false;

    private static GameStateReali instance;

    private GameStateReali(int id, String playerGUID, CountryComposite countryComposite, Disease disease, GlobalCure globalCure, Calendar calendar, UpgradePointsCalc upgradePointsCalc) {
        this.id = id;
        this.playerGUID = playerGUID;
        this.countryComposite = countryComposite;
        this.disease = disease;
        this.globalCure = globalCure;
        amountOfPeople+=countryComposite.getAmountOfPeople();
        deadPeople+=countryComposite.getDeadPeople();
        healthyPeople+=countryComposite.getHealthyPeople();
        infectedPeople+=countryComposite.getInfectedPeople();
        this.calendar = calendar;
        this.upgradePointsCalc = upgradePointsCalc==null? new UpgradePointsCalc():upgradePointsCalc;
        this.infectedCountries = new LinkedHashMap<>();
        for(String name : countryComposite.getInfectedCountry())
            infectedCountries.put(name, (Country) countryComposite.getComponentByName(name));
    }

    public static GameStateReali init(int id, String playerGUID, CountryComposite countryComposite, Disease disease, GlobalCure globalCure, Calendar calendar, UpgradePointsCalc upgradePointsCalc) {
        instance= new GameStateReali(id, playerGUID, countryComposite, disease, globalCure, calendar, upgradePointsCalc);
        return instance;
    }

    public static GameStateReali getInstance(){
        if(instance==null) throw new RuntimeException("Create one object through init");
        return instance;
    }

    public CallbackType pastOneTimeUnit() {
        timePassed=true;
        calendar.add(Calendar.DATE,1);
        //System.out.println(TimeUtils.INSTANCE.formatDate(calendar.getTime()));
        for (int i = 0; i < infectedCountries.size(); i++) {
            applyDiseaseOnCountry((Country) infectedCountries.values().toArray()[i]);
        }
        countryComposite.passOneTimeUnit();
        if(getInfectedPeople()>100_000)
            getGlobalCure().startWorkOnCure();
        globalCure.passOneTimeUnitCure();

        if(getDeadPeople()==getAmountOfPeople()){
            System.out.println("You won the game");
            return CallbackType.ENDGAMEWIN;
        }
        if(getInfectedPeople()==0) {
            System.out.println("You lose the game");
            return CallbackType.ENDGAMELOSE;
        }
        if(getGlobalCure().isCureCreated()) {
            System.out.println("You lose the game");
            return CallbackType.ENDGAMELOSE;
        }

        return CallbackType.TIMEPASS;
    }

    private void applyDiseaseOnCountry(Country country){
        if (!country.isInfected()) return;
        //TODO: double thisCountryMedicineFight=disease.getInfectivity()-country.getCureKoef(); //country has a chance to cure
        calcInfectedPeople(country);
        calcDeadPeople(country);
        calcHealthyPeople(country);
        for (int i = 0; i < disease.getTransmissions().size(); i++) {
            Transmission transmission = disease.getTransmissions().get(i);
            transmission.getHandler().handle(country);
        }
        for (int i = 0; i < disease.getAbilities().size(); i++) {
            Ability ability=disease.getAbilities().get(i);
            ability.getHandler().handle(country);
        }
    }
    private void calcInfectedPeople(Country country){
        double infectivity = Math.max(disease.getInfectivity()-country.getSlowInfect(),0.);
        long perTimeUnitInfected =(long) Math.min(Math.ceil(infectivity*country.getInfectedPeople()), country.getHealthyPeople());
        country.setInfectedPeople(country.getInfectedPeople()+perTimeUnitInfected);
    }
    private void calcDeadPeople(Country country){
        long perTimeUnitDead =(long) Math.min(Math.ceil(disease.getLethality() * country.getInfectedPeople()), country.getInfectedPeople());
        country.setDeadPeople(country.getDeadPeople()+perTimeUnitDead);
        country.setInfectedPeople(country.getInfectedPeople()-perTimeUnitDead);
    }
    private void calcHealthyPeople(Country country){
        country.setHealthyPeople(country.getAmountOfPeople()-country.getInfectedPeople()-country.getDeadPeople());
    }

    public CallbackType checkCanBuy(int points){
        if (upgradePointsCalc.checkCanBuy(points)){
            return CallbackType.CANBUY;
        }
        else return CallbackType.CANTBUY;
    }

    public CallbackType buyStuff(int points){
        if (upgradePointsCalc.buyStuff(points)){
            return CallbackType.BUYSUCCESSFUL;
        }
        else return CallbackType.BUYFAILED;
    }

    public void addUpgradePoints(int points){
        upgradePointsCalc.addUpgradePoints(points);
    }

    public void addSymptom(Symptom symptom){
        getDisease().addSymptom(symptom);
    }

    public void addTransmission(Transmission transmission){
        getDisease().addTransmission(transmission);
    }

    public void addAbility(Ability ability){
        getDisease().addAbility(ability);
    }

    @Override
    public void infectComponentByName(String name) {
        countryComposite.infectComponent(name);
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
        healthyPeople =countryComposite.getHealthyPeople();
        infectedPeople=countryComposite.getInfectedPeople();
        deadPeople = countryComposite.getDeadPeople();
    }

    public double getPercentOfInfectedPeople() {
        return (double) getInfectedPeople() / amountOfPeople;
    }

    public double getPercentOfHealthyPeople() {
        return (double) getHealthyPeople() / amountOfPeople;
    }

    public double getPercentOfDeadPeople() {
        return (double) getDeadPeople() / amountOfPeople;
    }

    public void addInfectedCountry(Country country){
        infectedCountries.put(country.getName(),country);
    }

    public void executeStrategy(List<Country> countries){
        strategy.execute(countries);
    }

    @Override
    public GameStateForEntity makeSnapshot() {
        try {
            return new GameStateForEntity((GameStateReali) this.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void loadSnapshot(GameStateForEntity snapshot) {
        this.amountOfPeople =  snapshot.getAmountOfPeople();
        this.deadPeople =  snapshot.getDeadPeople();
        this.infectedPeople =  snapshot.getInfectedPeople();
        this.healthyPeople =  snapshot.getHealthyPeople();
        this.countryComposite =  snapshot.getCountryComposite();
        this.infectedCountries =  snapshot.getInfectedCountries();
        this.disease =  snapshot.getDisease();
        this.globalCure =  snapshot.getGlobalCure();
        this.calendar = Calendar.getInstance();
        this.calendar.setTime(snapshot.getDate());
        this.upgradePointsCalc =  snapshot.getUpgradePointsCalc();
        this.timePassed=true;
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

    public HashMap<String, Country> getInfectedCountries() {
        return infectedCountries;
    }

    public void setInfectedCountries(HashMap<String, Country> infectedCountries) {
        this.infectedCountries = infectedCountries;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public UpgradePointsCalc getUpgradePointsCalc() {
        return upgradePointsCalc;
    }

    public void setUpgradePointsCalc(UpgradePointsCalc upgradePointsCalc) {
        this.upgradePointsCalc = upgradePointsCalc;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        GameStateReali gameStateRealiCopy = (GameStateReali) super.clone();
//        Gson gson = new Gson();
//        String jsonString = gson.toJson(infectedCountries);
//        Type type = new TypeToken<HashMap<Integer, Country>>(){}.getType();
        HashMap<String,Country> infectedCountriesCopy = new HashMap<>(infectedCountries);
        gameStateRealiCopy.setInfectedCountries(infectedCountriesCopy);
        return gameStateRealiCopy;
    }

    @Override
    public String toString() {
        return "GameStateReali{" +
                "id=" + id +
                ", playerGUID='" + playerGUID + '\'' +
                ", amountOfPeople=" + amountOfPeople +
                ", deadPeople=" + deadPeople +
                ", infectedPeople=" + infectedPeople +
                ", healthyPeople=" + healthyPeople +
                ", countryComposite=" + countryComposite +
                ", infectedCountries=" + infectedCountries +
                ", disease=" + disease +
                ", globalCure=" + globalCure +
                ", calendar=" + calendar +
                ", upgradePointsCalc=" + upgradePointsCalc +
                ", strategy=" + strategy +
                ", timePassed=" + timePassed +
                '}';
    }
}
