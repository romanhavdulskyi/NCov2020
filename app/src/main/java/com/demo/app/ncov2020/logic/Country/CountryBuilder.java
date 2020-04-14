package com.demo.app.ncov2020.logic.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryBuilder {
    private String name="CountryName";
    private long amountOfPeople=0;
    private boolean rich=false;
    private boolean openAirport=true;
    private boolean openSeaport=true;
    private boolean openGround =true;
    private boolean openSchool = true;
    private boolean knowAboutVirus=false;
    private Climate climate=Climate.NORMAL;
    private MedicineLevel medicineLevel=MedicineLevel.FIRST;
    private boolean infected = false;
    private double cureKoef = 0;
    private Hronology hronology=new Hronology(new ArrayList<>());
    private List<Country> pathsAir=new ArrayList<>();
    private List<Country> pathsSea=new ArrayList<>();
    private List<Country> pathsGround= new ArrayList<>();

    public CountryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CountryBuilder setAmountOfPeople(long amountOfPeople) {
        this.amountOfPeople = amountOfPeople;
        return this;
    }

    public CountryBuilder setRich(boolean rich) {
        this.rich = rich;
        return this;
    }

    public CountryBuilder setOpenAirport(boolean openAirport) {
        this.openAirport = openAirport;
        return this;
    }

    public CountryBuilder setOpenSeaport(boolean openSeaport) {
        this.openSeaport = openSeaport;
        return this;
    }

    public CountryBuilder setOpenGround(boolean openGround) {
        this.openGround = openGround;
        return this;
    }

    public CountryBuilder setOpenSchool(boolean openSchool) {
        this.openSchool = openSchool;
        return this;
    }

    public CountryBuilder setKnowAboutVirus(boolean knowAboutVirus) {
        this.knowAboutVirus = knowAboutVirus;
        return this;
    }

    public CountryBuilder setClimate(Climate climate) {
        this.climate = climate;
        return this;
    }

    public CountryBuilder setMedicineLevel(MedicineLevel medicineLevel) {
        this.medicineLevel = medicineLevel;
        return this;
    }

    public CountryBuilder setInfected(boolean infected) {
        this.infected = infected;
        return this;
    }

    public CountryBuilder setCureKoef(double cureKoef) {
        this.cureKoef = cureKoef;
        return this;
    }

    public CountryBuilder setHronology(Hronology hronology) {
        this.hronology = hronology;
        return this;
    }

    public CountryBuilder setPathsAir(List<Country> pathsAir) {
        this.pathsAir = pathsAir;
        return this;
    }

    public CountryBuilder setPathsSea(List<Country> pathsSea) {
        this.pathsSea = pathsSea;
        return this;
    }

    public CountryBuilder setPathsGround(List<Country> pathsGround) {
        this.pathsGround = pathsGround;
        return this;
    }

    public Country buildCountry() {
        return new Country(name, amountOfPeople, rich, openAirport, openSeaport, openGround, openSchool, knowAboutVirus, climate, medicineLevel, infected, cureKoef, hronology, pathsAir, pathsSea, pathsGround);
    }
}