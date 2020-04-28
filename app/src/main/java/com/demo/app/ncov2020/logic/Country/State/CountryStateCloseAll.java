package com.demo.app.ncov2020.logic.Country.State;

import com.demo.app.ncov2020.logic.Country.Country;

public class CountryStateCloseAll extends BaseCountryState {
    public CountryStateCloseAll(Country country) {
        super(country);
    }

    @Override
    public void applyState(){
        country.setOpenAirport(false);
        country.setOpenSeaport(false);
        country.setOpenGround(false);
        country.setOpenSchool(false);
        country.setSlowInfect(0.5);
    };

    @Override
    public void checkIfNeedChangeState() {

    }

    @Override
    public String toString() {
        return "CountryStateCloseAll{}";
    }
}
