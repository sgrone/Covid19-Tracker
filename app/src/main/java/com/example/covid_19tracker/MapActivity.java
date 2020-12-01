package com.example.covid_19tracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RawRes;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapActivity extends FragmentActivity {

    private Context context;
    public GoogleMap map;
    SupportMapFragment mapFragment;
    List<LatLng> latLngs = new ArrayList<>();

    public MapActivity(Context context, SupportMapFragment mapFragment) {
        this.context = context;
        this.mapFragment = mapFragment;
        initMap();
    }

    private void initMap() {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                //restricting zoom controls and My Location button
                map.getUiSettings().setZoomControlsEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);

                //show USA
                map.setLatLngBoundsForCameraTarget(usaBounds);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(USA.target, 2));
            }
        });

        //read in data from JSON file
        try {
            latLngs = readItems(R.raw.states_locations);
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of locations.", Toast.LENGTH_LONG).show();
        }
    }

    public static final CameraPosition USA =
            new CameraPosition.Builder().target(new LatLng(37.090200, -95.712900))
                    .zoom(50.5f)
                    .bearing(0)
                    .tilt(0)
                    .build();

    LatLngBounds usaBounds = new LatLngBounds(
            new LatLng(38.377654, -122.512669), //west bounds
            new LatLng(38.652731, -75.227514) // east bounds
    );



    public void addHeatmap(List<LatLng> latLngCases) {
        //create the gradient
        int[] colors = {
                Color.rgb(102, 225, 0), //green
                Color.rgb(255, 0, 0) //red
        };

        float[] startPoints = {
                0.2f, 1f
        };

        Gradient gradient = new Gradient(colors, startPoints);

        //create heat map tile provider and pass it state coordinates and gradient
        HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                .data(latLngCases)
                .gradient(gradient)
                .build();

        map.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
    }

    public List<LatLng> readItems ( @RawRes int resource) throws JSONException {
        List<LatLng> result = new ArrayList<>();
        InputStream inputStream = context.getResources().openRawResource(resource);
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("latitude");
            double lng = object.getDouble("longitude");
            result.add(new LatLng(lat, lng));
        }
        return result;
    }
}

