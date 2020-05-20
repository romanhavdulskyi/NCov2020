package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Callback.Callback;
import com.demo.app.ncov2020.logic.Callback.CallbackType;
import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.Country.State.CountryStateBusinessRebellion;

import java.util.List;

public class StrategyBusinessRebellion extends BaseStrategy{
    static final int pricePoints=10;

    @Override
    boolean confirmBuy() {
        return (GameStateReali.getInstance().getUpgradePointsCalc().buyStuff(pricePoints));
    }

    @Override
    public CallbackType execute(List<Country> countries) {
        if (!confirmBuy()) return CallbackType.STRATEGYFAILED;
        for(Country country : countries){
            if(country.isInfected()) {
                CountryStateBusinessRebellion countryState = new CountryStateBusinessRebellion(country);
                countryState.setPreviousState(country.getState());
                country.setState(countryState);
            }
        }
        return CallbackType.STRATEGYEXECUTED;
    }
}
