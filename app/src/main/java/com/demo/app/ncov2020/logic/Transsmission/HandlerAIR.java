package com.demo.app.ncov2020.logic.Transsmission;

import com.demo.app.ncov2020.logic.BaseHandler;
import com.demo.app.ncov2020.logic.MainPart.Country;

public class HandlerAIR extends BaseHandler {
    @Override
    public void handle(Country country) {
        if(country.isOpenAirport()){
            double luck = Math.random();
            if (country.getPercentOfInfectedPeople()>0.8 && luck>0.3){
                infectAnotherCountryByAir(country);
            }
            else if (country.getPercentOfInfectedPeople()>0.5 && luck>0.5) {
                infectAnotherCountryByAir(country);
            }
            else if (country.getPercentOfInfectedPeople()>0.3 && luck>0.8) {
                infectAnotherCountryByAir(country);
            }
        }
        delegateNext(country);
    }

    public void infectAnotherCountryByAir(Country baseCountry){
        baseCountry.shufflePathAir();
        for(Country country: baseCountry.getPathsAir()){
            if(country.isOpenAirport() && !country.isInfected()){
                country.beginInfection();
                baseCountry.removePathAir(country);
                return;
            }
            else baseCountry.removePathAir(country);
        }
    }
}
