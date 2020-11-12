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

import java.util.ArrayList;
import java.util.Vector;


/*
 * */
public class DataHandler {
    //private String[] stateInitial = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS",
    //"KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH",
    //"OK", "OR", "PA", "RI", "SC","SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};

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
    }

    public ArrayList<StateData> pullData(ArrayList<StateData> states) {
        final ArrayList<StateData> tempStateList = new ArrayList<>();

        Log.i("Check", "pullData Method Check.");
        // Create a String request
        // using Volley Library
        String url = "https://api.covidtracking.com/v1/states/current.json";

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

                            //stateID.add(data.getString("state"));
                            StateData newState = new StateData();
                            newState.setState(tempState);

                            if (data.getString("positive") != null) {
                                //positiveResults.add(data.getString("positive"));
                                newState.setPosResult(Integer.parseInt(data.getString("positive")));
                            } else {
                                //positiveResults.add("N/A");
                                newState.setPosResult(-1);
                            }

                            if (data.getString("death") != null) {
                                //deaths.add(data.getString("death"));
                                newState.setDeaths(Integer.parseInt(data.getString("death")));
                            } else {
                                newState.setDeaths(-1);
                                //deaths.add("N/A");
                            }

                            if (data.getString("hospitalizedCurrently") != null) {
                                //hospitalizedCurrent.add(data.getString("hospitalizedCurrently"));
                                newState.setHospCur(Integer.parseInt(data.getString("hospitalizedCurrently")));
                            } else {
                                //hospitalizedCurrent.add("N/A");
                                newState.setHospCur(-1);
                            }

                            tempStateList.add(newState);

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
        return(tempStateList);
    }
}