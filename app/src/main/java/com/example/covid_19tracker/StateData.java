package com.example.covid_19tracker;

import java.util.Vector;

public class StateData {

    //Any null values read in will be stored as "N/A" in the vectors.
    // Stores a list of State initials pulled in from the covidtracker API in the order they appeared in the JSON file.
    private String stateID;
    // Stores a list of the number of positive covid test results pulled in from the covidtracker API in the order they appeared in the JSON file.
    private int positiveResults;
    // Stores a list of the number of deaths initials pulled in from the covidtracker API in the order they appeared in the JSON file.
    private int deaths;
    // Stores a list of the number of people currently hospitalized pulled in from the covidtracker API in the order they appeared in the JSON file.
    private int hospitalizedCurrent;

    public StateData(){
        //
        //Maybe include a long switch statement adding the full name of a state based on the passed in initials...
    }

    //I'll add better comments later.
    //These are for getting individual values out of the Vector lists.
    public String getState() {
        return stateID;
    }

    public int getPosResult() {
        return positiveResults;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getHospCur() {
        return hospitalizedCurrent;
    }

    public void setState(String iD) {
        this.stateID = iD;
    }

    public void setPosResult(int posRes) {
        this.positiveResults = posRes;
    }

    public void setDeaths(int dead) { this.deaths = dead; }

    public void setHospCur(int hospCur) {
        this.hospitalizedCurrent = hospCur;
    }
}
