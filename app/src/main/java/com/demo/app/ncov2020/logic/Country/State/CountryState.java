package com.demo.app.ncov2020.logic.Country.State;

public interface CountryState {
    void changeState(CountryState countryState);
    void applyState();
    void checkIfNeedChangeState();
}
