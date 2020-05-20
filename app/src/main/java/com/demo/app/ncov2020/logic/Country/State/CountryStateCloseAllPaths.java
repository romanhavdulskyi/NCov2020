package com.demo.app.ncov2020.logic.Country.State;

import com.demo.app.ncov2020.logic.Country.Country;

public class CountryStateCloseAllPaths extends BaseCountryState {
    public CountryStateCloseAllPaths(Country country) {
        super(country);
    }

    @Override
    public void applyState(){
        country.setOpenAirport(false);
        country.setOpenSeaport(false);
        country.setOpenGround(false);
        country.setOpenSchool(false);
        country.setKnowAboutVirus(true);
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
