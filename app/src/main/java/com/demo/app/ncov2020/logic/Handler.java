package com.demo.app.ncov2020.logic;

public interface Handler {
    void setNext(Handler handler);
    void handle(Country country);
}
