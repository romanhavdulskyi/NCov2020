package com.demo.app.ncov2020.logic.Country;

public class AbstractVisitor implements Visitor {
    @Override
    public void accept(Country country) {
    }

    @Override
    public void accept(CountryComposite countryComposite) {
        for (Component component : countryComposite.getComponents())
            component.accept(this);
    }
}
