package com.example.covid_19tracker;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
*   ServiceGenerator is used as a tool to fetch the JSON data from the covidtracking API.
*   Objects of ServiceGenerator performs nearly all of the connection, data pulling, and
*   StateData variable filling needed to pull in Covid-19 data.
* */
public class ServiceGenerator {
    private static final String URL = "https://api.covidtracking.com/v1/states/";
    // Full API Address: https://api.covidtracking.com/v1/states/current.json
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    //
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(
            Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
}

