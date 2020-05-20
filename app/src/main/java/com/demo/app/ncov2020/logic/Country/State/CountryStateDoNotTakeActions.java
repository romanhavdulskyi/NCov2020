package com.demo.app.ncov2020.logic.Country.State;

import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.MainPart.GameStateReali;

public class CountryStateDoNotTakeActions extends BaseCountryState {
    public CountryStateDoNotTakeActions(Country country) {
        super(country);
    }

    @Override
    public void applyState(){
        country.setOpenAirport(true);
        country.setOpenSeaport(true);
        country.setOpenGround(true);
        country.setOpenSchool(true);
        country.setKnowAboutVirus(true);
        country.setSlowInfect(0.);
    };

    @Override
    public void checkIfNeedChangeState() {
        if(country.getDeadPeople()>1000)  changeState(new CountryStateCarantine(country));
        if(country.getPercentOfInfectedPeople()>0.2)  changeState(new CountryStateCarantine(country));
        //TODO: Callback suspecios disease
    }

    @Override
    public String toString() {
        return "CountryStateDoNotTakeActions{}";
    }
}
