package com.demo.app.ncov2020.logic.Abilities;

import com.demo.app.ncov2020.logic.BaseHandler;
import com.demo.app.ncov2020.logic.Climate;
import com.demo.app.ncov2020.logic.Country;

public class HandlerHot extends BaseHandler {
    @Override
    public void handle(Country country) {
        if(country.getClimate()== Climate.HOT){

        }
    }
}
