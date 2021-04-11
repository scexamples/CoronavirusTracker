package com.example.coronavirustracker.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Country implements Comparable<Country>{

    private String name;
    private Set<State> states = new HashSet<>();
    private int numberOfCases;

    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    public void addState(State state){
        states.add(state);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return this.name.equals(country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Country o) {
        return this.name.compareTo(o.name);
    }

    public int getNumberOfCases() {
        if(states != null && !states.isEmpty()){
            return numberOfCases + getSumOfCasesFromStates();
        } else {
            return numberOfCases;
        }
    }

    public int getSumOfCasesFromStates() {
        int sumOfCasesFromStates = 0;
         
        sumOfCasesFromStates = states.stream()
        .collect(Collectors.summingInt(State::getNumberOfCases));
        
        return sumOfCasesFromStates;
    }

    public void setNumberOfCases(int cases){
        numberOfCases = cases;
    }


}
