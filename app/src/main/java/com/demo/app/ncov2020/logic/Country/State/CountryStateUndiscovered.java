package com.demo.app.ncov2020.logic.Country.State;

import com.demo.app.ncov2020.logic.MainPart.GameStateReali;

public class CountryStateUndiscovered extends BaseCountryState {
    public void applyState(){
        country.setOpenAirport(true);
        country.setOpenSeaport(true);
        country.setOpenGround(true);
        country.setOpenSchool(true);
        country.setKnowAboutVirus(false);
    };

    @Override
    public void checkIfNeedChangeState() {
        if(GameStateReali.getInstance().getDeadPeople()>1000)  changeState(new CountryStateDoNotTakeActions());
        if(GameStateReali.getInstance().getInfectedPeople()>1_000_000)  changeState(new CountryStateDoNotTakeActions());
        if(country.getPercentOfInfectedPeople()>0.7) changeState(new CountryStateDoNotTakeActions());
        //TODO: Callback suspecios disease
    }

}
