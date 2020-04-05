//package com.demo.app.ncov2020.logic;
//
//import com.demo.app.ncov2020.data.room_data.GameCountry;
//import com.demo.app.ncov2020.data.room_data.GameState;
//
//import java.util.Objects;
//
//public class GameModel implements EverydayAble {
//    private final GameState gameState;
//
//    public GameModel(GameState gameState) {
//        this.gameState = gameState;
//    }
//
//
//
//    public void addCountry(Country country){
//        Objects.requireNonNull(gameState.getCountries()).add(country);
//    }
//
//    @Override
//    public void pastOneUnit() {
//        for (GameCountry country: Objects.requireNonNull(gameState.getCountries())) {
//            Objects.requireNonNull(gameState.getDisease()).acceptCountry(country);
//        }
//        if(getDeadPeople()==gameState.getAmountOfPeople()) System.out.println("You won the game");
//        if(getInfectedPeople()==0) System.out.println("You lose the game");
//    }
//
//    public long getAmountOfPeople(){
//        return gameState.getAmountOfPeople();
//    }
//
//    public long getDeadPeople() {
//        long deadPeople = 0;
//        for (Country country: Objects.requireNonNull(gameState.getCountries())) {
//            deadPeople+=country.getDeadPeople();
//        }
//        return deadPeople;
//    }
//
//    public long getInfectedPeople() {
//        long infectedPeople=0;
//        for (Country country: Objects.requireNonNull(gameState.getCountries())) {
//            infectedPeople+=country.getInfectedPeople();
//        }
//        return infectedPeople;
//    }
//
//    public long getHealthyPeople() {
//        long healthyPeople =0;
//        for (Country country: Objects.requireNonNull(gameState.getCountries())) {
//            healthyPeople +=country.getHealthyPeople();
//        }
//        return healthyPeople;
//    }
//
//    @Override
//    public String toString() {
//        return "GameModel{" +
//                "amountOfPeople=" + gameState.getAmountOfPeople() +
//                ", deadPeople=" + getDeadPeople() +
//                ", infectedPeople=" + getInfectedPeople() +
//                ", healthyPeople=" + getHealthyPeople() +
//                ", countries=" + gameState.getCountries() +
//                ", disease=" + gameState.getDisease() +
//                '}';
//    }
//}
