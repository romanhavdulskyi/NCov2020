package com.demo.app.ncov2020.logic.cure;

import com.demo.app.ncov2020.logic.Country;
import com.demo.app.ncov2020.logic.EverydayAble;
import com.demo.app.ncov2020.logic.GameModel;

public class GlobalCure implements EverydayAble {
    GameModel gameModel=GameModel.getInstance();
    private boolean began;
    private long timeToEnd=90;

    @Override
    public void pastOneUnit() {
        long tempTime = gameModel.getInfectedPeople()/100000;
        timeToEnd--;
    }
}
