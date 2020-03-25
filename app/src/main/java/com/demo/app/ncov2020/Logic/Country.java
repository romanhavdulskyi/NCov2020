package com.demo.app.ncov2020.Logic;

import com.demo.app.ncov2020.Logic.Disease.Disease;

import java.util.List;

public class Country {
    private final String name;
    private final long amountOfPeople;
    private long deadPeople = 0;
    private long infectedPeople = 0;
    private long heathyPeople;
    private final boolean rich;
    private boolean openAirport;
    private boolean openSeaport;
    private boolean openSchool = true;
    private boolean infected = false;
    private double cureCoef = 0;
    private List<Country> pathsAir;
    private List<Country> pathsSea;
    private List<Country> pathsGround;

    public Country(String name, int amountOfPeople, boolean rich, boolean openAirport, boolean openSeaport) {
        this.name = name;
        this.amountOfPeople = amountOfPeople;
        this.heathyPeople = amountOfPeople;
        this.rich = rich;
        this.openAirport = openAirport;
        this.openSeaport = openSeaport;
    }

    public Country beginInfection(){
        infected=true;
        heathyPeople--;
        infectedPeople=1;
        return this;
    }

    public void addPathAir(Country country) {
        pathsAir.add(country);
    }

    public void addPathSea(Country country) {
        pathsSea.add(country);
    }

    public void addNeighbourGround(Country country) {
        pathsGround.add(country);
    }

    public void pastOneUnit(Disease disease) {
        if (!infected) return;
        long perUnitInfected = (long) Math.min((disease.getInfectivity() * infectedPeople + 1), heathyPeople);
        long perUnitDead = (long) disease.getLethality();
        heathyPeople = Math.max(heathyPeople - perUnitInfected, 0);
        infectedPeople += perUnitInfected;
        deadPeople += Math.min(perUnitDead, infectedPeople);
        infectedPeople -= Math.min(perUnitDead, infectedPeople);
    }

    public long getAmountOfPeople() {
        return amountOfPeople;
    }

    public long getDeadPeople() {
        return deadPeople;
    }

    public void setDeadPeople(int deadPeople) {
        this.deadPeople = deadPeople;
    }

    public long getInfectedPeople() {
        return infectedPeople;
    }

    public void setInfectedPeople(int infectedPeople) {
        this.infectedPeople = infectedPeople;
    }

    public long getHeathyPeople() {
        return heathyPeople;
    }

    public void setHeathyPeople(int heathyPeople) {
        this.heathyPeople = heathyPeople;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", amountOfPeople=" + amountOfPeople +
                ", deadPeople=" + deadPeople +
                ", infectedPeople=" + infectedPeople +
                ", heathyPeople=" + heathyPeople +
                ", rich=" + rich +
                ", openAirport=" + openAirport +
                ", openSeaport=" + openSeaport +
                ", openSchool=" + openSchool +
                ", infected=" + infected +
                ", cureCoef=" + cureCoef +
                ", pathsAir=" + pathsAir +
                ", pathsSea=" + pathsSea +
                ", pathsGround=" + pathsGround +
                '}';
    }
}
