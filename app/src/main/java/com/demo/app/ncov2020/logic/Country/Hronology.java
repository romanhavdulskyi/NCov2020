package com.demo.app.ncov2020.logic.Country;

import androidx.annotation.NonNull;

import java.util.List;

public class Hronology implements Cloneable{
    private List<String> urls;
    private int currMem=0;
    private int amountOfUnlocked=0;

    public Hronology(List<String> urls) {
        this.urls = urls;
    }

    String getMemUrl(){
        if(isAvailable() && hasNextMem()) throw new RuntimeException("There is no mem to show now");
        String url = urls.get(currMem);
        currMem++;
        return url;
    }

    void unlockMem(){
        if (hasNextMem()) {
            amountOfUnlocked++;
        }
    }

    boolean hasNextMem(){
        if(currMem>=urls.size()) return false;
        return true;
    }

    public boolean isAvailable() {
        if(currMem>=amountOfUnlocked && hasNextMem())return false;
        else return true;
    }

    public int getCurrMem() {
        return currMem;
    }

    public int getAmountOfUnlocked() {
        return amountOfUnlocked;
    }

    public void setAmountOfUnlocked(int amountOfUnlocked) {
        this.amountOfUnlocked = amountOfUnlocked;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public void setCurrMem(int currMem) {
        this.currMem = currMem;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
