package com.demo.app.ncov2020.logic.Country;

import com.demo.app.ncov2020.logic.Country.State.BaseCountryState;
import com.demo.app.ncov2020.logic.Country.State.CountryStateUndiscovered;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Country  {
    private final String name;
    private final long amountOfPeople;
    private long deadPeople = 0;
    private long infectedPeople = 0;
    private long healthyPeople;
    private final boolean rich;
    private boolean openAirport=true;
    private boolean openSeaport=true;
    private boolean openGround =true;
    private boolean openSchool = true;
    private boolean knowAboutVirus=false;
    private Climate climate;
    private final MedicineLevel medicineLevel;
    private boolean infected = false;
    private double slowInfect = 0;
    private Hronology hronology;
    private List<Country> pathsAir;
    private List<Country> pathsSea;
    private List<Country> pathsGround;
    private BaseCountryState state = new CountryStateUndiscovered();

    public Country(String name, long amountOfPeople, boolean rich, boolean openAirport, boolean openSeaport, boolean openGround, boolean openSchool, boolean knowAboutVirus, Climate climate, MedicineLevel medicineLevel, boolean infected, double slowInfect, Hronology hronology, List<Country> pathsAir, List<Country> pathsSea, List<Country> pathsGround,BaseCountryState state) {
        this.name = name;
        this.amountOfPeople = amountOfPeople;
        this.healthyPeople = amountOfPeople;
        this.rich = rich;
        this.openAirport = openAirport;
        this.openSeaport = openSeaport;
        this.openGround = openGround;
        this.openSchool = openSchool;
        this.knowAboutVirus = knowAboutVirus;
        this.climate = climate;
        this.medicineLevel = medicineLevel;
        this.infected = infected;
        this.slowInfect = slowInfect;
        this.hronology = hronology;
        this.pathsAir = pathsAir;
        this.pathsSea = pathsSea;
        this.pathsGround = pathsGround;
        this.state=state;
    }

    public Country(String name, long amountOfPeople, boolean rich, Climate climate, MedicineLevel medicineLevel, Hronology hronology) {
        this.name = name;
        this.amountOfPeople = amountOfPeople;
        this.healthyPeople = amountOfPeople;
        this.rich = rich;
        this.climate=climate;
        this.medicineLevel=medicineLevel;
        this.pathsAir = new ArrayList<>();
        this.pathsSea = new ArrayList<>();
        this.pathsGround = new ArrayList<>();
        this.hronology = hronology;
    }

    public void passOneTimeUnit(){
        hronology.setAmountOfUnlocked((int) (hronology.getUrls().size()*(infectedPeople/amountOfPeople)));
        state.checkIfNeedChangeState();
        if(hronology.isAvailable()){
            //TODO:Notify User that available Mem
        }
    }

    public Country beginInfection(){
        infected=true;
        healthyPeople--;
        infectedPeople=1;
        return this;
    }

    public void shufflePathAir(){
        Collections.shuffle(pathsAir);
    }
    public void shufflePathSea(){
        Collections.shuffle(pathsSea);
    }
    public void shufflePathGround(){
        Collections.shuffle(pathsGround);
    }

    public void removePathAir(Country country){
        pathsAir.remove(country);
    }
    public void removePathSea(Country country){
        pathsSea.remove(country);
    }
    public void removePathGround(Country country){
        pathsGround.remove(country);
    }


    public void addPathAir(Country country) {
        pathsAir.add(country);
    }
    public void addPathSea(Country country) {
        pathsSea.add(country);
    }
    public void addPathsGround(Country country) {
        pathsGround.add(country);
    }


    public Hronology getHronology() {
        return hronology;
    }

    public void setHronology(Hronology hronology) {
        this.hronology = hronology;
    }

    public double getPercentOfInfectedPeople(){
        return (double) infectedPeople/ healthyPeople;
    }

    public String getName() {
        return name;
    }

    public long getAmountOfPeople() {
        return amountOfPeople;
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

    public boolean isRich() {
        return rich;
    }

    public boolean isOpenAirport() {
        return openAirport;
    }

    public void setOpenAirport(boolean openAirport) {
        this.openAirport = openAirport;
    }

    public boolean isOpenSeaport() {
        return openSeaport;
    }

    public void setOpenSeaport(boolean openSeaport) {
        this.openSeaport = openSeaport;
    }

    public boolean isOpenSchool() {
        return openSchool;
    }

    public void setOpenSchool(boolean openSchool) {
        this.openSchool = openSchool;
    }

    public Climate getClimate() {
        return climate;
    }

    public void setClimate(Climate climate) {
        this.climate = climate;
    }

    public boolean isInfected() {
        return infected;
    }

//    public void setInfected(boolean infected) {
//        this.infected = infected;
//    }

    public double getSlowInfect() {
        return slowInfect;
    }

    public void setSlowInfect(double slowInfect) {
        this.slowInfect = slowInfect;
    }

    public boolean isOpenGround() {
        return openGround;
    }

    public void setOpenGround(boolean openGround) {
        this.openGround = openGround;
    }

    public void setPathsAir(List<Country> pathsAir) {
        this.pathsAir = pathsAir;
    }

    public void setPathsSea(List<Country> pathsSea) {
        this.pathsSea = pathsSea;
    }

    public void setPathsGround(List<Country> pathsGround) {
        this.pathsGround = pathsGround;
    }

    public List<Country> getPathsAir() {
        return pathsAir;
    }

    public List<Country> getPathsSea() {
        return pathsSea;
    }

    public List<Country> getPathsGround() {
        return pathsGround;
    }

    public MedicineLevel getMedicineLevel() {
        return medicineLevel;
    }

    public BaseCountryState getState() {
        return state;
    }

    public void setState(BaseCountryState state) {
        this.state = state;
        state.applyState();
    }

    public boolean isKnowAboutVirus() {
        return knowAboutVirus;
    }

    public void setKnowAboutVirus(boolean knowAboutVirus) {
        this.knowAboutVirus = knowAboutVirus;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", amountOfPeople=" + amountOfPeople +
                ", deadPeople=" + deadPeople +
                ", infectedPeople=" + infectedPeople +
                ", healthyPeople=" + healthyPeople +
                ", rich=" + rich +
                ", openAirport=" + openAirport +
                ", openSeaport=" + openSeaport +
                ", openGround=" + openGround +
                ", openSchool=" + openSchool +
                ", knowAboutVirus=" + knowAboutVirus +
                ", climate=" + climate +
                ", medicineLevel=" + medicineLevel +
                ", infected=" + infected +
                ", slowInfect=" + slowInfect +
                ", hronology=" + hronology +
                ", pathsAir=" + pathsAir +
                ", pathsSea=" + pathsSea +
                ", pathsGround=" + pathsGround +
                ", state=" + state +
                '}';
    }
}
