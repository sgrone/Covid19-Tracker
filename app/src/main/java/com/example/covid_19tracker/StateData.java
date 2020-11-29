package com.example.covid_19tracker;

import android.util.Log;

import java.io.Serializable;

public class StateData implements Serializable {

    // State initials.
    private String state;
    // State name.
    private String stateName;
    // Number of positive Covid-19 tests in this state.
    private int positive;
    // Number of reported Covid-19 related deaths in this state.
    private int death;
    // Number of reported people currently hospitalized from Covid-19 in this state.
    private int hospitalizedCurrently;

    private static final String[][] stateNameList = {{"AL", "Alabama"}, {"AK", "Alaska"}, {"AZ", "Arizona"},
            {"AR", "Arkansas"}, {"CA", "California"}, {"CO", "Colorado"}, {"CT", "Connecticut"},
            {"DE", "Delaware"}, {"FL", "Florida"}, {"GA", "Georgia"}, {"HI", "Hawaii"},
            {"ID", "Idaho"}, {"IL", "Illinois"}, {"IN", "Indiana"}, {"IA", "Iowa"},
            {"KS", "Kansas"}, {"KY", "Kentucky"}, {"LA", "Louisiana"}, {"ME", "Maine"},
            {"MD", "Maryland"}, {"MA", "Massachusetts"}, {"MI", "Michigan"}, {"MN", "Minnesota"},
            {"MS", "Mississippi"}, {"MO", "Missouri"}, {"MT", "Montana"}, {"NE", "Nebraska"},
            {"NV", "Nevada"}, {"NH", "New Hampshire"}, {"NJ", "New Jersey"}, {"NM", "New Mexico"},
            {"NY", "New York"}, {"NC", "North Carolina"}, {"ND", "North Dakota"}, {"OH", "Ohio"},
            {"OK", "Oklahoma"}, {"OR", "Oregon"}, {"PA", "Pennsylvania"}, {"RI", "Rhode Island"},
            {"SC", "South Carolina"},{"SD", "South Dakota"}, {"TN", "Tennessee"}, {"TX", "Texas"},
            {"UT", "Utah"}, {"VT", "Vermont"}, {"VA", "Virginia"}, {"WA", "Washington"},
            {"WV", "West Virginia"}, {"WI", "Wisconsin"}, {"WY", "Wyoming"}};

    public StateData(){
    }

    //I'll add better comments later.
    //These are for getting individual values out of the Vector lists.
    public String getState() {
        return state;
    }

    public String getStateName() {
        return stateName;
    }

    public int getPositive() {
        return positive;
    }

    public int getDeath() {
        return death;
    }

    public int getHospitalizedCurrently() {
        return hospitalizedCurrently;
    }

    public void setState(String id) {
        this.state = id;
        this.setStateName(id);
    }

    public void setStateName(String id){ this.stateName = findStateName(id); }

    public void setPositive(int posRes) {
        this.positive = posRes;
    }

    public void setDeath(int dead) { this.death = dead; }

    public void setHospitalizedCurrently(int hospCur) {
        this.hospitalizedCurrently = hospCur;
    }

    /*Given the intials of a state, traverse through the array stateNameList and
     * find the state name that matches the initials. Sets StateData's stateName variable equal to
     * the found name.*/
    public static String findStateName(String initials) {
        String tempName = "N/A";
        int nameIndex = -1;

        for(int x = 0; x < stateNameList.length; x++){
            if(initials.equals(stateNameList[x][0])){
                nameIndex = x;
                break;
            }
        }

        if(nameIndex > -1){
            tempName = stateNameList[nameIndex][1];
        } else {
            Log.i("State name ", "State name for " + initials + "not found.");
        }

        return tempName;
    }
}
