package com.example.coronavirustracker.models;

public class State implements Comparable<State>{

    private String name;
    private Country country;
    private int numberOfCases;

    public State() {
    }

    public State(String name, Country country, int numberOfCases) {
        this.name = name;
        this.country = country;
        this.numberOfCases = numberOfCases;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getNumberOfCases() {
        return numberOfCases;
    }

    public void setCases(int cases) {
        numberOfCases = cases;
    }

    @Override
    public int compareTo(State o) {
        return this.name.compareTo(o.name);
    }
}
