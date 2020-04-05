package com.demo.app.ncov2020.logic.Disease;

import com.demo.app.ncov2020.logic.Handler;
import com.demo.app.ncov2020.logic.Transsmission.HandlerAIR;
import com.demo.app.ncov2020.logic.Transsmission.HandlerGround;
import com.demo.app.ncov2020.logic.Transsmission.HandlerWater;

public enum TypeTrans {
    AIR(new HandlerAIR()),
    WATER(new HandlerWater()),
    GROUND(new HandlerGround());

    private Handler handler;

    TypeTrans(Handler handler) {
        this.handler=handler;
    }

    public Handler getHandler(){
        return handler;
    }
    public static TypeTrans getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
