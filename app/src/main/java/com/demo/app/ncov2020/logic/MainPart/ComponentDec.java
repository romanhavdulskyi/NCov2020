package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Callback.CallbackType;
import com.demo.app.ncov2020.logic.Callback.GameStateForEntity;
import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.Disease.Ability;
import com.demo.app.ncov2020.logic.Disease.Symptom;
import com.demo.app.ncov2020.logic.Disease.Transmission;

import java.util.List;

public interface ComponentDec extends Originator<GameStateForEntity> {
    CallbackType pastOneTimeUnit();
    void addSymptom(Symptom symptom);

    void addTransmission(Transmission transmission);

    void addAbility(Ability ability);

    void infectComponentByName(String name);

    CallbackType executeStrategy(List<Country> countries);
    public CallbackType checkCanBuy(int points);

    public CallbackType buyStuff(Priceable priceable);

    public void setStrategy(Strategy strategy);
}
