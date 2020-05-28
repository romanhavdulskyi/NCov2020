package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Callback.CallbackType;
import com.demo.app.ncov2020.logic.Callback.GameStateForEntity;
import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.Disease.Ability;
import com.demo.app.ncov2020.logic.Disease.Symptom;
import com.demo.app.ncov2020.logic.Disease.Transmission;

import java.util.List;

public abstract class BaseDecorator implements ComponentDec {
    ComponentDec wrappee;

    public BaseDecorator(ComponentDec wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public CallbackType pastOneTimeUnit() {
        return wrappee.pastOneTimeUnit();
    }

    public CallbackType checkCanBuy(int points){
        return wrappee.checkCanBuy(points);
    }

    public CallbackType buyStuff(Priceable priceable){
        return wrappee.buyStuff(priceable);
    }

    @Override
    public void addSymptom(Symptom symptom) {
        wrappee.addSymptom(symptom);
    }

    @Override
    public void addTransmission(Transmission transmission) {
        wrappee.addTransmission(transmission);
    }

    @Override
    public void addAbility(Ability ability) {
        wrappee.addAbility(ability);
    }

    @Override
    public void infectComponentByName(String name) {
        wrappee.infectComponentByName(name);
    }

    @Override
    public GameStateForEntity makeSnapshot() {
        return wrappee.makeSnapshot();
    }

    @Override
    public void loadSnapshot(GameStateForEntity snapshot) {
        wrappee.loadSnapshot(snapshot);
    }

    public ComponentDec getWrappee() {
        return wrappee;
    }

    public void setWrappee(ComponentDec wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public CallbackType executeStrategy(List<Country> countries) {
        CallbackType callbackType= wrappee.executeStrategy(countries);
        return callbackType;
    }

    public void setStrategy(Strategy strategy){
        wrappee.setStrategy(strategy);
    }
}
