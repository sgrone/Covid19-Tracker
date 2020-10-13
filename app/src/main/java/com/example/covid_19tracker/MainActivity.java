package com.example.covid_19tracker;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity
        extends AppCompatActivity {

    // Create the object of TextView
    TextView tvCases, tvRecovered,
            tvCritical, tvActive,
            tvTodayCases, tvTotalDeaths,
            tvTodayDeaths,
            tvAffectedCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link those objects with their respective id's
        // that we have given in .XML file
        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvAffectedCountries = findViewById(R.id.tvAffectedCountries);

        DataHandler handler = new DataHandler(this.getApplicationContext());
        updateView(handler);
    }

    private void updateView(DataHandler handler) {
        tvCases.setText(handler.getTvCases());
        tvRecovered.setText(handler.getTvRecovered());
        tvCritical.setText(handler.getTvCritical());
        tvActive.setText(handler.getTvActive());
        tvTodayCases.setText(handler.getTvTodayCases());
        tvTotalDeaths.setText(handler.getTvTotalDeaths());
        tvTodayDeaths.setText(handler.getTvTodayDeaths());
        tvAffectedCountries.setText(handler.getTvAffectedCountries());
    }
}