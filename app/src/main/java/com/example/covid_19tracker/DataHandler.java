package com.example.covid_19tracker;

import android.content.Context;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Vector;


/*
 * Updated by Payton to pull covid data from the covidtracking API and store it in several Vectors.
 * */
public class DataHandler {
    //private String[] stateInitial = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS",
    //"KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH",
    //"OK", "OR", "PA", "RI", "SC","SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};

    //Any null values read in will be stored as "N/A" in the vectors.
    // Stores a list of State initials pulled in from the covidtracker API in the order they appeared in the JSON file.
    private Vector<String> stateID;
    // Stores a list of the number of positive covid test results pulled in from the covidtracker API in the order they appeared in the JSON file.
    private Vector<String> positiveResults;
    // Stores a list of the number of deaths initials pulled in from the covidtracker API in the order they appeared in the JSON file.
    private Vector<String> deaths;
    // Stores a list of the number of people currently hospitalized pulled in from the covidtracker API in the order they appeared in the JSON file.
    private Vector<String> hospitalizedCurrent;

    //I'll add better comments later.
    //These are for getting individual values out of the Vector lists.
    public String getState(int index) {
        return stateID.get(index);
    }

    public String getPosResult(int index) {
        return positiveResults.get(index);
    }

    public String getDeathData(int index) {
        return deaths.get(index);
    }

    public String getHospCur(int index) {
        return hospitalizedCurrent.get(index);
    }

    //These are for getting the entire Vector lists.
    public Vector<String> getStateList() {
        return stateID;
    }

    public Vector<String> getPosResultList() {
        return positiveResults;
    }

    public Vector<String> getDeathDataList() {
        return deaths;
    }

    public Vector<String> getHospCurList() {
        return hospitalizedCurrent;
    }

    private Context context;
    RequestQueue requestQueue;

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

    public DataHandler(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        fetchdata();
        stateID = new Vector<String>();
        positiveResults = new Vector<String>();
        deaths = new Vector<String>();
        hospitalizedCurrent = new Vector<String>();
    }

    private void fetchdata() {
        Log.i("Check", "Fetch Data Method Check.");
        // Create a String request
        // using Volley Library
        String url = "https://api.covidtracking.com/v1/states/current.json";

        //RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.i("Check", "Response Length: " + response.length());
                    for (int x = 0; x < response.length(); x++) {
                        JSONObject data = response.getJSONObject(x);
                        String tempState = data.getString("state");

                        /*The States of America Samoa(AS), Puerto Rico(PR),
                         * District of Columbia(DC), Guam(GU), Northern Marianas(MP),
                         * and the Virgin Islands(VI) are ignored here.
                         * */
                        if (!(tempState.equals("AS") || tempState.equals("PR") || tempState.equals("DC") ||
                                tempState.equals("GU") || tempState.equals("MP") || tempState.equals("VI"))) {

                            stateID.add(data.getString("state"));

                            /*Some values pulled in from the JSON objects may be null if that data is
                             * unavailable from a particular state. If this is the case, "N/A" is added
                             * to the piece of data's respective Vector list in place of a value for that
                             * state.*/
                            if (data.getString("positive") != null) {
                                positiveResults.add(data.getString("positive"));
                            } else {
                                positiveResults.add("N/A");
                            }

                            if (data.getString("death") != null) {
                                deaths.add(data.getString("death"));
                            } else {
                                deaths.add("N/A");
                            }

                            if (data.getString("hospitalizedCurrently") != null) {
                                hospitalizedCurrent.add(data.getString("hospitalizedCurrently"));
                            } else {
                                hospitalizedCurrent.add("N/A");
                            }
                            Log.i("Check", "State ID: " + data.getString("state"));
                        }

                        Log.i("Check", "State ID: " + data.getString("state"));
                    }
                    Log.i("Check", "For Loop Done,");
                } catch (JSONException e) {
                    Log.i("Check", "State Data Capture Failed!");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            // Handles errors that occur due to Volley
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");
            }
        });

        requestQueue.add(request);
    }
}