package com.demo.app.ncov2020.logic.Country.State;

import com.demo.app.ncov2020.logic.Country.Country;

public abstract class BaseCountryState implements CountryState, Cloneable {
    protected Country country;

    @Override
    public void changeState(BaseCountryState baseCountryState) {
        country.setState(baseCountryState);
        baseCountryState.applyState();
        //TODO: notify that country state changed
    }

    public abstract void applyState();

    public abstract void checkIfNeedChangeState();

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
