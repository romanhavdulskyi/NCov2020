package com.demo.app.ncov2020.logic.Country.State;

import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.MainPart.GameStatev2;

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
        if(GameStatev2.getInstance().getDeadPeople()>10000)  changeState(new CountryStateDoNotTakeActions());
        if(GameStatev2.getInstance().getInfectedPeople()>1_000_000)  changeState(new CountryStateDoNotTakeActions());
        if((double)country.getInfectedPeople()/country.getAmountOfPeople()>0.7) changeState(new CountryStateDoNotTakeActions());
    }

}
