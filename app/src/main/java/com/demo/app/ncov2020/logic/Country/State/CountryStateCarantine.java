package com.demo.app.ncov2020.logic.Country.State;

import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.MainPart.GameStateReali;

public class CountryStateCarantine extends BaseCountryState {
    public CountryStateCarantine(Country country) {
        super(country);
    }

    @Override
    public void applyState(){
        country.setOpenAirport(true);
        country.setOpenSeaport(true);
        country.setOpenGround(true);
        country.setOpenSchool(false);
        country.setKnowAboutVirus(true);
        country.setSlowInfect(0.2);
        if(!GameStateReali.getInstance().getGlobalCure().checkIfThereIsContributor(country.getName())){
            Long money = (long) (Math.random()*100_000.);
            money=country.isRich()?money*2:money;
            GameStateReali.getInstance().getGlobalCure().addContributor(country,money);
        }
        //TODO: повідомляти GameModel
    };

    @Override
    public void checkIfNeedChangeState() {
        if(country.getPercentOfDeadPeople()>0.3)  changeState(new CountryStateCloseAllPaths(country));
        if(country.getPercentOfInfectedPeople()>0.8)  changeState(new CountryStateCloseAllPaths(country));
    }

    @Override
    public String toString() {
        return "CountryStateCarantine{}";
    }
}
