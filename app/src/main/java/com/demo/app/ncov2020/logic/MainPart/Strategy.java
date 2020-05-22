package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Callback.CallbackType;
import com.demo.app.ncov2020.logic.Country.Country;

import java.util.List;

public interface Strategy extends Priceable {
    CallbackType execute(List<Country> country);
}
