package com.example.covid_19tracker;

import android.util.Log;

import java.io.Serializable;

/*
* Data holding class that holds Covid-19 related data on a US State.*/
public class StateData implements Serializable {

    // State's initials.
    private String state;
    // State's name.
    private String stateName;
    // Number of positive Covid-19 tests in this state.
    private int positive;
    // Number of reported Covid-19 related deaths in this state.
    private int death;
    // Number of reported people currently hospitalized from Covid-19 in this state.
    private int hospitalizedCurrently;

    private int positiveIncrease;

    private int deathIncrease;

    private int hospitalizedIncrease;

    //Used to find the full name of a state based on the initials.
    public static final String[][] stateNameList = {{"AL", "Alabama"}, {"AK", "Alaska"}, {"AZ", "Arizona"},
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

    public int getPositiveIncrease() {return positiveIncrease; }

    public int getDeathIncrease() {return deathIncrease; }

    public int getHospitalizedIncrease() {return hospitalizedIncrease; }

    /*
    * Sets this State's initials (state) and full name (StateName).
    * The full name of a state is not pulled in when grabbing the Covid-19 data
    * so it must be determined and set based on the initials.
    * */
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

    public void setPositiveIncrease(int posIncrease){ this.positiveIncrease = posIncrease; }

    public void setDeathIncrease(int deathsIncrease){ this.deathIncrease = deathsIncrease; }

    public void setHospitalizedIncrease(int hospIncrease){ this.hospitalizedIncrease = hospIncrease; }

    /*Given the initials of a state, traverse through the array stateNameList and
     * find the state name that matches the initials. Sets StateData's stateName variable equal to
     * the found name.
     * @return tempName - The full state name of the given state initials. N/A if no name is found.*/
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
