package com.demo.app.ncov2020.logic.Country;

import java.util.List;

public interface Component {
    void passOneTimeUnit();

    long getAmountOfPeople();

    long getDeadPeople();

    long getHealthyPeople();

    long getInfectedPeople();

    List<Component> getAllChildren();

    List<String> getHardLevelInfectedCountry();

    List<String> getMediumLevelInfectedCountry();

    List<String> getLowLevelInfectedCountry();

}
