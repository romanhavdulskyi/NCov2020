package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Callback.CallbackType;
import com.demo.app.ncov2020.logic.Callback.GameStateForEntity;
import com.demo.app.ncov2020.logic.Disease.Ability;
import com.demo.app.ncov2020.logic.Disease.Symptom;
import com.demo.app.ncov2020.logic.Disease.Transmission;

public interface ComponentDec extends Memento<GameStateForEntity> {
    CallbackType pastOneTimeUnit();
    void addSymptom(Symptom symptom);

    void addTransmission(Transmission transmission);

    void addAbility(Ability ability);

    void infectComponentByName(String name);
}
