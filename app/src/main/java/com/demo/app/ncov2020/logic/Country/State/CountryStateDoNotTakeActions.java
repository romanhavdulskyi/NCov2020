package com.demo.app.ncov2020.logic.Country.State;

import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.MainPart.GameStateReali;

public class CountryStateDoNotTakeActions extends BaseCountryState {
    @Override
    public void applyState(){
        country.setOpenAirport(true);
        country.setOpenSeaport(true);
        country.setOpenGround(true);
        country.setOpenSchool(true);
        country.setKnowAboutVirus(true);
    };

    @Override
    public void checkIfNeedChangeState() {
        if(GameStateReali.getInstance().getDeadPeople()>10000)  changeState(new CountryStateDoNotTakeActions());
        if(GameStateReali.getInstance().getInfectedPeople()>1_000_000)  changeState(new CountryStateDoNotTakeActions());
        //TODO: Callback suspecios disease
    }

}
