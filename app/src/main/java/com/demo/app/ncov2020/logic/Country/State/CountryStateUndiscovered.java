package com.demo.app.ncov2020.logic.Country.State;

import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.MainPart.GameStateReali;

public class CountryStateUndiscovered extends BaseCountryState {
    public CountryStateUndiscovered(Country country) {
        super(country);
    }

    @Override
    public void applyState(){
        country.setOpenAirport(true);
        country.setOpenSeaport(true);
        country.setOpenGround(true);
        country.setOpenSchool(true);
        country.setKnowAboutVirus(false);
        country.setSlowInfect(0.);
    };

    @Override
    public void checkIfNeedChangeState() {
        if(country.getDeadPeople()>1000)  changeState(new CountryStateDoNotTakeActions(country));
        if(GameStateReali.getInstance().getInfectedPeople()>100_000)  changeState(new CountryStateDoNotTakeActions(country));
        if(country.getPercentOfInfectedPeople()>0.7) changeState(new CountryStateDoNotTakeActions(country));
        //TODO: Callback suspecios disease
    }

    @Override
    public String toString() {
        return "CountryStateUndiscovered{}";
    }
}
