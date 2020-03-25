package com.demo.app.ncov2020.Logic;

import java.util.List;

public class Country{
    private final String name;
    private final int amountOfPeople;
    private int deadPeople;
    private int infectedPeople;
    private int heathyPeople;
    private final boolean rich;
    private boolean openAirport;
    private boolean openSeaport;
    private boolean openSchool=true;
    private boolean infected = false;
    private double cureCoef=0;
    private List<Country> pathsAir;
    private List<Country> pathsSea;
    private List<Country> pathsGround;

    public Country(String name, int amountOfPeople, boolean rich, boolean openAirport, boolean openSeaport) {
        this.name = name;
        this.amountOfPeople = amountOfPeople;
        this.rich = rich;
        this.openAirport = openAirport;
        this.openSeaport = openSeaport;
    }

    public void addPathAir(Country country){
        pathsAir.add(country);
    }

    public void addPathSea(Country country){
        pathsSea.add(country);
    }

    public void addNeighbourGround(Country country){
        pathsGround.add(country);
    }



}
