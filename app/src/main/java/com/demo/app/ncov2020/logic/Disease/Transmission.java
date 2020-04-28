package com.demo.app.ncov2020.logic.Disease;

import androidx.annotation.NonNull;

import com.demo.app.ncov2020.logic.Handler;

public class Transmission implements Cloneable{
    private final String name;
    private final String description;
    private final TypeTrans type;
    private Handler handler;

    public Transmission(String name, String description, TypeTrans type, Handler handler) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.handler = handler;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TypeTrans getType() {
        return type;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
