package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.Transsmission.HandlerGround;

import java.util.List;

public class ConcreateStrategyTravelGround implements Strategy{
    private static HandlerGround handlerGround = new HandlerGround();
    @Override
    public void execute(List<Country> countries) {
        for(Country country : countries){
            if(country.isInfected())
            handlerGround.infectAnotherCountryByGround(country);
        }
    }
}
