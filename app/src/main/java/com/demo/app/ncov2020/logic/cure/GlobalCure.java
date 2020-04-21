package com.demo.app.ncov2020.logic.cure;

public class GlobalCure{
    private boolean startedWork;
    private long timeToEnd=90;

    public GlobalCure(long timeToEnd) {
        this.timeToEnd = timeToEnd;
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

    @Override
    public String toString() {
        return "GlobalCure{" +
                "startedWork=" + startedWork +
                ", timeToEnd=" + timeToEnd +
                '}';
    }
}
