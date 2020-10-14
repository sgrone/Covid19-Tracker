package com.example.covid_19tracker;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DataHandler {

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
    private Context context;

    public DataHandler(Context context) {
        this.context = context;
        fetchdata();
    }

    private void fetchdata() {
        // Create a String request
        // using Volley Library
        String url = "https://corona.lmao.ninja/v2/all";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Handle the JSON object and handle it inside try and catch
                try {
                    // Creating object of JSONObject
                    JSONObject jsonObject = new JSONObject(response.toString());
                    // Set the data in text view
                    // which are available in JSON format
                    // Note that the parameter inside
                    // the getString() must match
                    // with the name given in JSON format
                    tvCases = jsonObject.getString("cases");
                    tvTotalDeaths = jsonObject.getString("deaths");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }


}
