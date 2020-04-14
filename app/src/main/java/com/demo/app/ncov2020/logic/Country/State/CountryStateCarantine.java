package com.demo.app.ncov2020.logic.Country.State;

import com.demo.app.ncov2020.logic.Country.Country;

public class CountryStateCarantine extends BaseCountryState {
    public void applyState(){
        country.setOpenAirport(true);
        country.setOpenSeaport(true);
        country.setOpenGround(true);
        country.setOpenSchool(false);
        country.setSlowInfect(0.2);
        //TODO: повідомляти GameModel
    };

    @Override
    public void checkIfNeedChangeState() {

    }

}
