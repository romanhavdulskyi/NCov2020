package com.demo.app.ncov2020.logic;

import com.demo.app.ncov2020.logic.Country.Country;

public interface Handler extends Cloneable{
    void setNext(Handler handler);
    boolean hasNext();
    void handle(Country country);
}
