package com.demo.app.ncov2020.logic.MainPart;

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
    private boolean openAirport;
    private boolean openSeaport;
    private boolean openGround =true;
    private boolean openSchool = true;
    private boolean knowAboutVirus=false;
    private Climate climate = Climate.NORMAL;
    private boolean infected = false;
    private double cureKoef = 0;
    private List<Country> pathsAir;
    private List<Country> pathsSea;
    private List<Country> pathsGround;

    public Country(String name, long amountOfPeople, boolean rich,Climate climate, boolean openAirport, boolean openSeaport) {
        this.name = name;
        this.amountOfPeople = amountOfPeople;
        this.healthyPeople = amountOfPeople;
        this.rich = rich;
        this.climate=climate;
        this.openAirport = openAirport;
        this.openSeaport = openSeaport;
        this.pathsAir = new ArrayList<>();
        this.pathsSea = new ArrayList<>();
        this.pathsGround = new ArrayList<>();
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

    public void setInfected(boolean infected) {
        this.infected = infected;
    }

    public double getCureKoef() {
        return cureKoef;
    }

    public void setCureKoef(double cureKoef) {
        this.cureKoef = cureKoef;
    }

    public boolean isOpenGround() {
        return openGround;
    }

    public void setOpenGround(boolean openGround) {
        this.openGround = openGround;
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
                ", infected=" + infected +
                ", cureKoef=" + cureKoef +
                ", pathsAir=" + pathsAir.size() +
                ", pathsSea=" + pathsSea.size() +
                ", pathsGround=" + pathsGround.size() +
                '}';
    }
}
