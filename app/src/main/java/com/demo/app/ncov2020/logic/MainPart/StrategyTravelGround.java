package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Callback.CallbackType;
import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.Transsmission.HandlerGround;

import java.util.List;

public class StrategyTravelGround extends BaseStrategy{
    static final int pricePoints=10;

    private static HandlerGround handlerGround = new HandlerGround();
    @Override
    boolean confirmBuy() {
        return (GameStateReali.getInstance().getUpgradePointsCalc().buyStuff(pricePoints));
    }

    @Override
    public CallbackType execute(List<Country> countries) {
        if (!confirmBuy()) return CallbackType.STRATEGYFAILED;
        for(Country country : countries){
            if(country.isInfected())
            handlerGround.infectAnotherCountryByGround(country);
        }
        return CallbackType.STRATEGYEXECUTED;
    }
}
