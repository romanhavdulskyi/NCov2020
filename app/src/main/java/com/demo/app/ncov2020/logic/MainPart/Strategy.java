package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Country.Country;

import java.util.List;

public interface Strategy {
    void execute(List<Country> country);
}
