package com.demo.app.ncov2020.logic.Disease;

import com.demo.app.ncov2020.logic.Handler;
import com.demo.app.ncov2020.logic.Transsmission.HandlerAIR;
import com.demo.app.ncov2020.logic.Transsmission.HandlerGround;
import com.demo.app.ncov2020.logic.Transsmission.HandlerWater;

public enum TypeTrans {
    AIR(),
    WATER(),
    GROUND();

    public static TypeTrans getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
