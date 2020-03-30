package com.demo.app.ncov2020.logic.Disease;

public enum TypeTrans {
    AIR,
    WATER,
    GROUND;

    public static TypeTrans getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
