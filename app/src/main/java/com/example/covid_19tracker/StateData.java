package com.example.covid_19tracker;

import android.util.Log;

import java.util.Vector;

public class StateData {

    //Any null values read in will be stored as "N/A" in the vectors.
    // State initials.
    private String stateID;
    // State name.
    private String stateName;
    // Number of positive Covid-19 tests in this state.
    private int positiveResults;
    // Number of reported Covid-19 related deaths in this state.
    private int deaths;
    // Number of reported people currently hospitalized from Covid-19 in this state.
    private int hospitalizedCurrent;

    private String[][] stateNameList = {{"AL", "Alabama"}, {"AK", "Alaska"}, {"AZ", "Arizona"}, {"AR", "Arkansas"}, {"CA", "California"}, {"CO", "Colorado"}, {"CT", "Connecticut"}, {"DE", "Delaware"}, {"FL", "Florida"}, {"GA", "Georgia"}, {"HI", "Hawaii"}, {"ID", "Idaho"}, {"IL", "Illinois"}, {"IN", "Indiana"}, {"IA", "Iowa"}, {"KS", "Kansas"},
            {"KY", "Kentucky"}, {"LA", "Louisiana"}, {"ME", "Maine"}, {"MD", "Maryland"}, {"MA", "Massachusetts"}, {"MI", "Michigan"}, {"MN", "Minnesota"}, {"MS", "Mississippi"}, {"MO", "Missouri"}, {"MT", "Montana"}, {"NE", "Nebraska"}, {"NV", "Nevada"}, {"NH", "New Hampshire"}, {"NJ", "New Jersey"}, {"NM", "New Mexico"}, {"NY", "New York"}, {"NC", "North Carolina"}, {"ND", "North Dakota"}, {"OH", "Ohio"},
            {"OK", "Oklahoma"}, {"OR", "Oregon"}, {"PA", "Pennsylvania"}, {"RI", "Rhode Island"}, {"SC", "South Carolina"},{"SD", "South Dakota"}, {"TN", "Tennessee"}, {"TX", "Texas"}, {"UT", "Utah"}, {"VT", "Vermont"}, {"VA", "Virginia"}, {"WA", "Washington"}, {"WV", "West Virginia"}, {"WI", "Wisconsin"}, {"WY", "Wyoming"}};

    public StateData(){
        //
        //Maybe include a long switch statement adding the full name of a state based on the passed in initials...
    }

    //I'll add better comments later.
    //These are for getting individual values out of the Vector lists.
    public String getStateInitials() {
        return stateID;
    }

    public String getStateName() {
        return stateName;
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

    public void setStateInitials(String iD) {
        this.stateID = iD;
        this.setStateName(iD);
    }

    /*Given the intials of a state, traverse through the array stateNameList and
    * find the state name that matches the initials. Sets StateData's stateName variable equal to
    * the found name.*/
    public void setStateName(String initials) {
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
        }

        this.stateName = tempName;
        //Log.i("Check", "State initals to name check: " + initials + ":" +tempName);
    }

    public void setPosResult(int posRes) {
        this.positiveResults = posRes;
    }

    public void setDeaths(int dead) { this.deaths = dead; }

    public void setHospCur(int hospCur) {
        this.hospitalizedCurrent = hospCur;
    }
}
