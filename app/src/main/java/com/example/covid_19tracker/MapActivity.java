package com.example.covid_19tracker;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final CameraPosition USA =
            new CameraPosition.Builder().target(new LatLng(37.090200, -95.712900))
                    .zoom(50.5f)
                    .bearing(0)
                    .tilt(0)
                    .build();


    public GoogleMap map;

    LatLngBounds usaBounds = new LatLngBounds(
            new LatLng(38.377654, -122.512669), //west bounds
            new LatLng(38.652731, -75.227514) // east bounds
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.heatMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        //restricting zoom controls and My Location button
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setMyLocationButtonEnabled(false);

        //show USA
        map.setLatLngBoundsForCameraTarget(usaBounds);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.090200, -95.712900),2));

    }

}