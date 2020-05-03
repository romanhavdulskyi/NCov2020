package com.demo.app.ncov2020.logic.Country;

public interface Visitor {
    void accept(Country country);
    void accept(CountryComposite countryComposite);
}
