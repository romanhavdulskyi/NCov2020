package com.demo.app.ncov2020.logic.Country.State;

import androidx.annotation.NonNull;

import com.demo.app.ncov2020.logic.Country.Country;

public abstract class BaseCountryState implements CountryState, Cloneable {
    protected Country country;

    @Override
    public void changeState(CountryState countryState) {
        country.setState(countryState);
        countryState.applyState();
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
