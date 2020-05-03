package com.demo.app.ncov2020.logic.Country;

public class ConcreateVisitor extends AbstractVisitor {
    @Override
    public void accept(Country country) {
        System.out.println("Country "+ country.getName());
    }

    @Override
    public void accept(CountryComposite countryComposite) {
        System.out.println("CountryComposite "+ countryComposite.getName()+" begin:");
        for (Component component : countryComposite.getComponents()) {
            component.accept(this);
        }
        System.out.println("End "+ countryComposite.getName());
    }
}
