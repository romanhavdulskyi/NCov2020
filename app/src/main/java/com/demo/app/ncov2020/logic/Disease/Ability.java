package com.demo.app.ncov2020.logic.Disease;

import androidx.annotation.NonNull;

import com.demo.app.ncov2020.logic.Handler;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ability)) return false;
        Ability ability = (Ability) o;
        return Objects.equals(getName(), ability.getName()) &&
                Objects.equals(getDescription(), ability.getDescription()) &&
                getTypeAbility() == ability.getTypeAbility() &&
                Objects.equals(getHandler(), ability.getHandler());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getTypeAbility(), getHandler());
    }
}
