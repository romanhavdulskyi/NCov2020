package com.demo.app.ncov2020.logic.Transsmission;

import com.demo.app.ncov2020.logic.BaseHandler;
import com.demo.app.ncov2020.logic.Country.Country;

public class HandlerGround extends BaseHandler {
    public void handle(Country country) {
        if(country.isOpenGround()){
            double luck = Math.random();
            if (country.getPercentOfInfectedPeople()>0.8 && luck>0.3){
                infectAnotherCountryByGround(country);
            }
            else if (country.getPercentOfInfectedPeople()>0.5 && luck>0.5) {
                infectAnotherCountryByGround(country);
            }
            else if (country.getPercentOfInfectedPeople()>0.3 && luck>0.8) {
                infectAnotherCountryByGround(country);
            }
        }
        delegateNext(country);
    }

    public void infectAnotherCountryByGround(Country baseCountry){
        baseCountry.shufflePathGround();
        for(Country country: baseCountry.getPathsGround()){
            if(country.isOpenGround() && !country.isInfected()){
                country.beginInfection();
                baseCountry.removePathGround(country);
                return;
            }
            else baseCountry.removePathGround(country);
        }
    }
}
