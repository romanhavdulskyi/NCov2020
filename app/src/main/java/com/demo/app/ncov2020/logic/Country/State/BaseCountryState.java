package com.demo.app.ncov2020.logic.Country.State;

import androidx.annotation.NonNull;

import com.demo.app.ncov2020.game.Game;
import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.MainPart.GameStateReali;

public abstract class BaseCountryState implements CountryState, Cloneable {
    protected Country country;

    public BaseCountryState(Country country) {
        this.country = country;
    }

    @Override
    public void changeState(CountryState countryState) {
        country.setState(countryState);
        countryState.applyState();
        GameStateReali.getInstance().addUpgradePoints((int) (Math.random()*3));
        //TODO: notify that country state changed
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
