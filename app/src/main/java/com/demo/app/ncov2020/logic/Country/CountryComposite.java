package com.demo.app.ncov2020.logic.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CountryComposite implements Component {
    private List<Component> components = new ArrayList<>();

    @Override
    public void passOneTimeUnit() {
        for (Component component:components){
            component.passOneTimeUnit();
        }
    }

    @Override
    public long getAmountOfPeople() {
        long amountOfPeople=0;
        for (Component component:components){
            amountOfPeople+=component.getAmountOfPeople();
        }
        return amountOfPeople;
    }

    @Override
    public long getDeadPeople() {
        long deadPeople=0;
        for (Component component:components){
            deadPeople+=component.getDeadPeople();
        }
        return deadPeople;
    }

    @Override
    public long getHealthyPeople() {
        long healthyPeople=0;
        for (Component component:components){
            healthyPeople+=component.getHealthyPeople();
        }
        return healthyPeople;
    }

    @Override
    public long getInfectedPeople() {
        long infectedPeople=0;
        for (Component component:components){
            infectedPeople+=component.getInfectedPeople();
        }
        return infectedPeople;
    }

    @Override
    public List<String> getHardLevelInfectedCountry() {
        List<String> list = new ArrayList<>();
        for(Component component : components)
            list.addAll(component.getHardLevelInfectedCountry());
        return list;
    }

    @Override
    public List<String> getMediumLevelInfectedCountry() {
        List<String> list = new ArrayList<>();
        for(Component component : components)
            list.addAll(component.getMediumLevelInfectedCountry());
        return list;
    }

    @Override
    public List<String> getLowLevelInfectedCountry() {
        List<String> list = new ArrayList<>();
        for(Component component : components)
            list.addAll(component.getLowLevelInfectedCountry());
        return list;
    }

    public void addComponent(Component component){
        components.add(component);
    }
    public void removeComponent(Component component){
        components.remove(component);
    }

    public List<Component> getComponents() {
        return components;
    }
    public void setComponents(List<Component> components) {
        this.components = components;
    }
}
