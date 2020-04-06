package com.demo.app.ncov2020.logic;

import com.demo.app.ncov2020.logic.MainPart.Country;

public interface Handler {
    void setNext(Handler handler);
    void handle(Country country);
}
