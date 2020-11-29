package com.example.covid_19tracker;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
*   ServiceGenerator uses Retrofit library tools to fetch the JSON data from the covidtracking API.
*   Objects of ServiceGenerator perform nearly all of the connection, data pulling, and
*   StateData variable filling needed to pull in Covid-19 data.
* */
public class ServiceGenerator {
    //Partial API address.
    private static final String URL = "https://api.covidtracking.com/v1/states/";
    // Full API Address: https://api.covidtracking.com/v1/states/current.json
    // As the name implies, builder sets up and builds a Retrofit object to use for parsing JSON  data.
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create());

    // Performs the parsing of pulled in JSON data and facilitates data mapping to Java objects or lists.
    private static Retrofit retrofit = builder.build();

    //Http client that facilitates the connections to the API.
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    // Essentially a special constructor class for ServiceGenerators.
    public static <S> S createService(
            Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
}