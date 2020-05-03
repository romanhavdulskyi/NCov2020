package com.demo.app.ncov2020.logic.Country;

import java.util.List;

public interface Component extends Cloneable, Visitable{
    void passOneTimeUnit();

    long getAmountOfPeople();

    long getDeadPeople();

    long getHealthyPeople();

    long getInfectedPeople();

    List<Component> getAllLeaves();

    List<String> getInfectedCountry();

}
