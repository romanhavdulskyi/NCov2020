package com.demo.app.ncov2020.logic.Disease;

import androidx.annotation.NonNull;

import com.demo.app.ncov2020.logic.Handler;

public class Ability implements Cloneable{
    private final String name;
    private final String description;
    private final TypeAbility typeAbility;
    private Handler handler;

    public Ability(String name, String description, TypeAbility typeAbility, Handler handler) {
        this.name = name;
        this.description = description;
        this.typeAbility = typeAbility;
        this.handler = handler;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TypeAbility getTypeAbility() {
        return typeAbility;
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
