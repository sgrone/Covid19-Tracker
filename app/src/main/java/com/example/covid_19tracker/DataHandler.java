package com.example.covid_19tracker;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


/**
 * Thread class that can be created and called upon to grab JSON data from the 'covidtracking' api.
 * DataHandler pulls data once upon being started, but can repull and return data using calls to the
 * functions pullData and getData respectively.
 * **/
public class DataHandler extends Thread {

    private Context context;

    //Variables and methods from here...
    public String getTvCases() {
        return tvCases;
    }

    public void setTvCases(String tvCases) {
        this.tvCases = tvCases;
    }

    public String getTvTotalDeaths() {
        return tvTotalDeaths;
    }

    public void setTvTotalDeaths(String tvTotalDeaths) {
        this.tvTotalDeaths = tvTotalDeaths;
    }


    private String tvCases = "Invalid";
    private String tvTotalDeaths = "Invalid";
    //... to here are useless and will be deleted eventually.

    // ArrayList that will be filled with StateData objects containing Covid-19 data for US states.
    private ArrayList<StateData> tempStateList;

    public DataHandler(Context context, ArrayList<StateData> stateList) {
        this.context = context;
    }

    /**
     * DataHandler will pull Covid-19 data from API once upon being started.
     * **/
    public void run() {
        pullData();
    }
    /**
     * Uses the DataService class to request JSON data from the covidtracking api.
     * Data for each state is stored in StateData objects as the JSON data is parsed in
     * and the StateData objects are stored in a list and retured with the call to getData.
     * The StateData objects are then added to the ArrayList tempStateList which can be returned
     * as needed to access the Covid-19 state data.
     *
     * Note: Only the current (2020) 50 US states are considered, any US territories are ignored
     * for simplicity.
     * **/
    public void pullData() {
        tempStateList = new ArrayList<>();
        //String url = "https://api.covidtracking.com/v1/states/current.json";

        DataService service = ServiceGenerator.createService(DataService.class);
        Call<List<StateData>> calledData = service.getData();
        List<StateData> jArrayData = null;

        try {
            jArrayData = calledData.execute().body();
            Log.i("Check", "Check Check 1 2 3: " + jArrayData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(StateData state : jArrayData){
            state.setStateName(state.getState());
            if( !(state.getStateName().equals("N/A")) ){
                Log.i("Check", "Initials: " + state.getState() + " | Name: " + state.getStateName());
                tempStateList.add(state);
            }
        }
    }

    public ArrayList<StateData> getData(){
        if(tempStateList != null){
            return tempStateList;
        }else{
            return null;
        }
    }

}