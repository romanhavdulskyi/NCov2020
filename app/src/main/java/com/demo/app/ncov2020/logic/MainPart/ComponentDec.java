package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Callback.CallbackType;
import com.demo.app.ncov2020.logic.Disease.Ability;
import com.demo.app.ncov2020.logic.Disease.Symptom;
import com.demo.app.ncov2020.logic.Disease.Transmission;

public interface ComponentDec {
    public CallbackType pastOneTimeUnit();
    public void addSymptom(Symptom symptom);

    public void addTransmission(Transmission transmission);

    public void addAbility(Ability ability);
}
