package com.demo.app.ncov2020.logic.MainPart;

import com.demo.app.ncov2020.logic.Abilities.HandlerAntibiotics1;
import com.demo.app.ncov2020.logic.Callback.Callback;
import com.demo.app.ncov2020.logic.Callback.CallbackType;
import com.demo.app.ncov2020.logic.Callback.ConcreateCallback;
import com.demo.app.ncov2020.logic.Callback.GameStateForEntity;
import com.demo.app.ncov2020.logic.Country.Climate;
import com.demo.app.ncov2020.logic.Country.Country;
import com.demo.app.ncov2020.logic.Country.CountryBuilder;
import com.demo.app.ncov2020.logic.Country.CountryComposite;
import com.demo.app.ncov2020.logic.Country.Hronology;
import com.demo.app.ncov2020.logic.Country.MedicineLevel;
import com.demo.app.ncov2020.logic.Disease.Ability;
import com.demo.app.ncov2020.logic.Disease.Disease;
import com.demo.app.ncov2020.logic.Disease.Symptom;
import com.demo.app.ncov2020.logic.Disease.Transmission;
import com.demo.app.ncov2020.logic.Disease.TypeAbility;
import com.demo.app.ncov2020.logic.Disease.TypeTrans;
import com.demo.app.ncov2020.logic.Transsmission.HandlerAIR;
import com.demo.app.ncov2020.logic.Transsmission.HandlerGround;
import com.demo.app.ncov2020.logic.Transsmission.HandlerWater;
import com.demo.app.ncov2020.logic.cure.GlobalCure;

import java.util.ArrayList;
import java.util.List;


public class GameStateLogDecorator extends BaseDecorator {
    public GameStateLogDecorator(ComponentDec componentDec) {
        super(componentDec);
    }

    public CallbackType pastOneTimeUnit() {
        CallbackType callbackType = super.pastOneTimeUnit();
        System.out.println(callbackType.name());
        return callbackType;
    }

    @Override
    public CallbackType checkCanBuy(int points) {
        CallbackType callbackType =  super.checkCanBuy(points);
        System.out.println(callbackType.name());
        return callbackType;
    }

    @Override
    public CallbackType buyStuff(Priceable priceable) {
        CallbackType callbackType =  super.buyStuff(priceable);
        System.out.println(callbackType.name());
        return callbackType;
    }

    public void addSymptom(Symptom symptom){
        super.addSymptom(symptom);
        System.out.println(CallbackType.SYMPTOMADD.name());
    }

    public void addTransmission(Transmission transmission){
        super.addTransmission(transmission);
        System.out.println(CallbackType.TRANSADD.name());
    }

    public void addAbility(Ability ability){
        super.addAbility(ability);
        System.out.println(CallbackType.ABILITYADD.name());
    }

    @Override
    public void infectComponentByName(String name) {
        super.infectComponentByName(name);
        System.out.println(CallbackType.BEGININFECTION.name());
    }

    @Override
    public GameStateForEntity makeSnapshot() {
        GameStateForEntity gameStateForEntity = super.makeSnapshot();
        System.out.println(CallbackType.MAKEDSNAPSHOT.name());
        return gameStateForEntity;
    }

    @Override
    public void loadSnapshot(GameStateForEntity snapshot) {
        super.loadSnapshot(snapshot);
        System.out.println(CallbackType.LOADSNAPSHOT.name());
    }

    @Override
    public CallbackType executeStrategy(List<Country> countries) {
        CallbackType callbackType =super.executeStrategy(countries);
        System.out.println(callbackType.name());
        return callbackType;
    }

    @Override
    public void setStrategy(Strategy strategy) {
        super.setStrategy(strategy);
    }

}
