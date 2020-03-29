package com.demo.app.ncov2020.Logic.Disease;

public class Transmission {
    private final String name;
    private final String description;
    private final TypeTrans type;

    public Transmission(String name, String description, TypeTrans type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TypeTrans getType() {
        return type;
    }
}
