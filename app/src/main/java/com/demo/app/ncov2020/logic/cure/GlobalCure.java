package com.demo.app.ncov2020.logic.cure;

import com.demo.app.ncov2020.logic.EverydayAble;
import com.demo.app.ncov2020.logic.MainPart.GameModel;

public class GlobalCure implements EverydayAble {
    private boolean startedWork;
    private long timeToEnd=90;

    public GlobalCure(long timeToEnd) {
        this.timeToEnd = timeToEnd;
    }

    public void startWorkOnCure(){
        startedWork=true;
    }

    @Override
    public void pastOneTimeUnit() {
        if (!startedWork) return;
        long tempTime = GameModel.getInstance().getGameStatev2().getInfectedPeople()/1000000;
        timeToEnd--;
        timeToEnd-=tempTime;
    }

    public boolean isCureCreated(){
        return timeToEnd<=0;
    }

    public boolean isStartedWork() {
        return startedWork;
    }

    @Override
    public String toString() {
        return "GlobalCure{" +
                "startedWork=" + startedWork +
                ", timeToEnd=" + timeToEnd +
                '}';
    }
}
