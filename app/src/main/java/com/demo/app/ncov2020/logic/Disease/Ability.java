package com.demo.app.ncov2020.logic.Disease;

public class Ability {
    private final String name;
    private final String description;
    private final TypeAbility typeAbility;

    public Ability(String name, String description, TypeAbility typeAbility) {
        this.name = name;
        this.description = description;
        this.typeAbility = typeAbility;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TypeAbility getTypeAbility() {
        return typeAbility;
    }
}
