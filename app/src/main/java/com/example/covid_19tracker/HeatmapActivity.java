package com.example.covid_19tracker;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.RawRes;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HeatmapActivity extends MapActivity {
    List<LatLng> latLngs = null;
    Context context = getApplicationContext();


    //read in data from JSON file
    public void readFile()
    {
        try {
            latLngs = readItems(R.raw.states_locations);
        } catch (JSONException e) {
            Toast.makeText(context, "Problem reading list of locations.", Toast.LENGTH_LONG).show();
        }
    }

    //create heat map tile provider and pass it state coordinates
    HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
            .data(latLngs)
            .build();

    TileOverlay overlay = map.addTileOverlay(new TileOverlayOptions().tileProvider(provider));

    public List<LatLng> readItems(@RawRes int resource) throws JSONException {
        List<LatLng> result = new ArrayList<>();
        InputStream inputStream = context.getResources().openRawResource(resource);
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            result.add(new LatLng(lat, lng));
        }
        return result;
    }

    //create the gradient

    //add tile overlay to map

}
