package com.demo.app.ncov2020.logic.Country.State;

import com.demo.app.ncov2020.logic.Country.Country;

public class CountryStateDoNotTakeActions extends BaseCountryState {
    public void applyState(){
        country.setKnowAboutVirus(true);
    };

    @Override
    public void checkIfNeedChangeState() {

    }

}
