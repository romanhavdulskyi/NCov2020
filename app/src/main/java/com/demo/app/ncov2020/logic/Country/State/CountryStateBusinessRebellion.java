package com.demo.app.ncov2020.logic.Country.State;

import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.MainPart.GameStateReali;

public class CountryStateBusinessRebellion extends BaseCountryState {
    public CountryStateBusinessRebellion(Country country) {
        super(country);
    }
    private int times=0;
    CountryState previousState;
    @Override
    public void applyState(){
        country.setOpenSchool(true);
        country.setSlowInfect(0.);
        //TODO: повідомляти GameModel
    }

    @Override
    public void checkIfNeedChangeState() {
        if(times++>=5)  changeState(previousState);
    }

    public CountryState getPreviousState() {
        return previousState;
    }

    public void setPreviousState(CountryState previousState) {
        this.previousState = previousState;
    }

    @Override
    public String toString() {
        return "CountryStateCarantine{}";
    }
}
