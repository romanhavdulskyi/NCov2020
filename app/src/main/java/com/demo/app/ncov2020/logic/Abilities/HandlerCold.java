package com.demo.app.ncov2020.logic.Abilities;

import com.demo.app.ncov2020.logic.BaseHandler;
import com.demo.app.ncov2020.logic.Country.Climate;
import com.demo.app.ncov2020.logic.Country.Country;

public class HandlerCold extends BaseHandler {
    @Override
    public void handle(Country country) {
        if(country.getClimate()== Climate.COLD){
            //scountry.setInfectedPeople(country.getInfectedPeople());
        }
    }
}
