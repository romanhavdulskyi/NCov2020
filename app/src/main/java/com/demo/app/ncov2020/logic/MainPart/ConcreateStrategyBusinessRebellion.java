package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.Country.State.CountryStateDoNotTakeActions;
import com.demo.app.ncov2020.logic.Transsmission.HandlerGround;

import java.util.List;

public class ConcreateStrategyBusinessRebellion implements Strategy{
    @Override
    public void execute(List<Country> countries) {
        for(Country country : countries){
            if(country.isInfected())
            country.setState(new CountryStateDoNotTakeActions(country));
        }
    }
}
