package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Callback.CallbackType;
import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.Transsmission.HandlerGround;

import java.util.List;

public class StrategyNoAction implements Strategy{

    @Override
    public CallbackType execute(List<Country> countries) {
        return CallbackType.STRATEGYEXECUTED;
    }
}
