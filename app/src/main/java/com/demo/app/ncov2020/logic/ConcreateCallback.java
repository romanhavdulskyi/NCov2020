package com.demo.app.ncov2020.logic;

public class ConcreateCallback implements Callback {
    //gameModel.setCallback(this);
    @Override
    public void callingBack(String str) {
        if(str=="stateChanged") return;
    }

}
