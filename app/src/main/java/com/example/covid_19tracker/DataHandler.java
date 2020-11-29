package com.example.covid_19tracker;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


/*
 * */
public class DataHandler extends Thread {

    private Context context;

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
    private ArrayList<StateData> tempStateList;

    public DataHandler(Context context, ArrayList<StateData> stateList) {
        this.context = context;
    }

    public void run() {
        pullData();
    }
    public void pullData() {
        tempStateList = new ArrayList<>();
        String url = "https://api.covidtracking.com/v1/states/current.json";

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

        /*if(tempStateList.size() > 0){
            StateData test1 = tempStateList.get(0);
            Log.i("Check", "First State Check: " + test1.getState());
        }*/
    }

    public ArrayList<StateData> getData(){
        if(tempStateList != null){
            return tempStateList;
        }else{
            return null;
        }
    }

}