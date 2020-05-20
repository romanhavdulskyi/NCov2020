package com.demo.app.ncov2020.logic.cure;

import com.demo.app.ncov2020.logic.Country.Country;

import java.io.Serializable;
import java.util.HashMap;

public class GlobalCure implements Serializable {
    private static GlobalCure instance;
    private boolean startedWork;
    private long timeToEnd=90;
    private HashMap<String,Long> contibutorsMoney = new HashMap<>();
    private Long totalMoney = 0L;

    public void passOneTimeUnitCure(){
        if (!isStartedWork()) return;
        long tempTime = totalMoney/1_000_000;
        timeToEnd=timeToEnd-tempTime-1;
    }

    public void addContributor(Country country,Long money){
        contibutorsMoney.put(country.getName(),money);
        totalMoney+=money;
    }

    public boolean checkIfThereIsContributor(String nameCountry){
        return contibutorsMoney.containsKey(nameCountry);
    }

    public Long checkAmountOfGivenMoney(String nameCountry){
        return contibutorsMoney.get(nameCountry);
    }

    public GlobalCure(long timeToEnd) {
        this.timeToEnd = timeToEnd;
    }
    public static GlobalCure init(long timeToEnd){
        GlobalCure globalCure = new GlobalCure(timeToEnd);
        instance =globalCure;
        return instance;
    }

    public void startWorkOnCure(){
        startedWork=true;
    }

    public boolean isCureCreated(){
        return timeToEnd<=0;
    }

    public boolean isStartedWork() {
        return startedWork;
    }

    public long getTimeToEnd() {
        return timeToEnd;
    }

    public void setTimeToEnd(long timeToEnd) {
        this.timeToEnd = timeToEnd;
    }

    public HashMap<String, Long> getContibutorsMoney() {
        return contibutorsMoney;
    }

    public void setContibutorsMoney(HashMap<String, Long> contibutorsMoney) {
        this.contibutorsMoney = contibutorsMoney;
    }

    public static GlobalCure getInstance() {
        return instance;
    }

    public static void setInstance(GlobalCure instance) {
        GlobalCure.instance = instance;
    }

    @Override
    public String toString() {
        return "GlobalCure{" +
                "startedWork=" + startedWork +
                ", timeToEnd=" + timeToEnd +
                '}';
    }
}
