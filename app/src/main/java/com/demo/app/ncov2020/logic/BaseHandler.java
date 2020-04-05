package com.demo.app.ncov2020.logic;

import com.demo.app.ncov2020.logic.Country;
import com.demo.app.ncov2020.logic.Handler;

public abstract class BaseHandler implements Handler {
    private Handler next;

    @Override
    public void setNext(Handler handler) {
        this.next=handler;
    }

    public void addHandler(Handler handler){
        if(next==null) {
            next=handler;
        }
        else {

        }
    }

    public void delegateNext(Country country){
        if(hasNext())next.handle(country);
    }

    public boolean hasNext(){
        return next!=null;
    }

    public Handler getNext(){
        return next;
    }

}
