package com.demo.app.ncov2020.logic.MainPart;

import java.io.Serializable;

public class UpgradePointsCalc implements Serializable {
    private double lastPayedForInfected=0.;
    private int everyXDaysPay=4;
    private int currDay=0;
    private double lastPayedForDead=0.;
    private int upgradePoints = 0;

    void calculate(){
        double percentOfInfectedPeople = GameStateReali.getInstance().getPercentOfInfectedPeople();
        if (percentOfInfectedPeople -lastPayedForInfected>0.1){
            int pay = (int) ((percentOfInfectedPeople -lastPayedForInfected)/0.1*(Math.random()*3));
            GameStateReali.getInstance().addUpgradePoints(pay);
            lastPayedForInfected=percentOfInfectedPeople;
        }
        double percentOfDeadPeople = GameStateReali.getInstance().getPercentOfDeadPeople();
        if (percentOfDeadPeople -lastPayedForInfected>0.1){
            int pay = (int) ((percentOfDeadPeople -lastPayedForInfected)/0.1*(Math.random()*3));
            GameStateReali.getInstance().addUpgradePoints(pay);
            lastPayedForInfected=percentOfDeadPeople;
        }
        if(++currDay==everyXDaysPay){
            GameStateReali.getInstance().addUpgradePoints((int)Math.random()*3);
            currDay=0;
        }
    }

    public void addUpgradePoints(int points){
        this.upgradePoints+=points;
    }

    public boolean checkCanBuy(int points){
        return upgradePoints>=points;
    }

    public boolean buyStuff(int points){
        if (upgradePoints>=points){
            upgradePoints-=points;
            return true;
        }
        else return false;
    }

    public double getLastPayedForInfected() {
        return lastPayedForInfected;
    }

    public void setLastPayedForInfected(double lastPayedForInfected) {
        this.lastPayedForInfected = lastPayedForInfected;
    }

    public int getEveryXDaysPay() {
        return everyXDaysPay;
    }

    public void setEveryXDaysPay(int everyXDaysPay) {
        this.everyXDaysPay = everyXDaysPay;
    }

    public int getCurrDay() {
        return currDay;
    }

    public void setCurrDay(int currDay) {
        this.currDay = currDay;
    }

    public double getLastPayedForDead() {
        return lastPayedForDead;
    }

    public void setLastPayedForDead(double lastPayedForDead) {
        this.lastPayedForDead = lastPayedForDead;
    }

    public int getUpgradePoints() {
        return upgradePoints;
    }

    public void setUpgradePoints(int upgradePoints) {
        this.upgradePoints = upgradePoints;
    }
}
