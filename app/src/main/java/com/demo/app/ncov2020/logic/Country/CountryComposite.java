package com.demo.app.ncov2020.logic.Country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CountryComposite implements Component, IterCollection {
    private final String name;
    private Map<String,Component> components = new HashMap<>();

    public CountryComposite(String name) {
        this.name = name;
    }

    @Override
    public void passOneTimeUnit() {
        for (Component component:components.values()){
            component.passOneTimeUnit();
        }
    }

    @Override
    public long getAmountOfPeople() {
        long amountOfPeople=0;
        for (Component component:components.values()){
            amountOfPeople+=component.getAmountOfPeople();
        }
        return amountOfPeople;
    }

    @Override
    public long getDeadPeople() {
        long deadPeople=0;
        for (Component component:components.values()){
            deadPeople+=component.getDeadPeople();
        }
        return deadPeople;
    }

    @Override
    public long getHealthyPeople() {
        long healthyPeople=0;
        for (Component component:components.values()){
            healthyPeople+=component.getHealthyPeople();
        }
        return healthyPeople;
    }

    @Override
    public long getInfectedPeople() {
        long infectedPeople=0;
        for (Component component:components.values()){
            infectedPeople+=component.getInfectedPeople();
        }
        return infectedPeople;
    }

    @Override
    public List<String> getHardLevelInfectedCountry() {
        List<String> list = new ArrayList<>();
        for(Component component : components.values())
            list.addAll(component.getHardLevelInfectedCountry());
        return list;
    }

    @Override
    public List<String> getMediumLevelInfectedCountry() {
        List<String> list = new ArrayList<>();
        for(Component component : components.values())
            list.addAll(component.getMediumLevelInfectedCountry());
        return list;
    }

    @Override
    public List<String> getLowLevelInfectedCountry() {
        List<String> list = new ArrayList<>();
        for(Component component : components.values())
            list.addAll(component.getLowLevelInfectedCountry());
        return list;
    }

    public void addComponent(Component component){
        if(component instanceof Country){
            components.put(((Country) component).getName(),component);
        }
        else if(component instanceof CountryComposite){
            components.put(((CountryComposite) component).getName(),component);
        }
    }

    @Override
    public List<Component> getAllLeaves() {
        List <Component> children= new LinkedList<>();
        for (Component component:components.values()){
            children.addAll(component.getAllLeaves());
        }
        return children;
    }

    //get Country by name
    public Component getComponentByName(String name){
        return components.get(name);
    }

    public void infectComponent(String name){
        Component component = getComponentByName(name);
        if(component instanceof Country){
            ((Country) component).beginInfection();
        }
        else if(component instanceof CountryComposite){
            IIterator iterator = getIterator();
            while (iterator.hasNext()){
                Country next =(Country) iterator.next();
                next.beginInfection();
            }
        }
    }

    @Override
    public IIterator getIterator() {
        return new CountryIterator(getAllLeaves());
    }

    private class CountryIterator implements IIterator<Component>{
        int index=0;
        List <Component> children;

        public CountryIterator(List<Component> children) {
            this.children = children;
        }

        @Override
        public boolean hasNext() {
            return index<children.size();
        }

        @Override
        public Component next() {
            return children.get(index++);
        }

    }

    public void removeComponent(Component component){
        components.remove(component);
    }

    public String getName() {
        return name;
    }
}
