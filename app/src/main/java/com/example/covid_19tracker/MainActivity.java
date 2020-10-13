package com.example.covid_19tracker;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity
        extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    public static final String EXTRA_TEXT = "com.example.covid_19tracker.EXTRA_TEXT";

    // Create the object of TextView
    TextView tvCases, tvActive,
            tvTodayCases, tvTotalDeaths,
            tvTodayDeaths,
            tvAffectedCountries;

    String currentState = "Michigan";
    DataHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link those objects with their respective id's
        // that we have given in .XML file
        tvCases = findViewById(R.id.tvCases);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvAffectedCountries = findViewById(R.id.tvAffectedCountries);
        this.handler = new DataHandler(this.getApplicationContext());

        Spinner stateSpinner = findViewById(R.id.stateSpinner);
        Button viewChanceBtn = (Button) findViewById(R.id.viewChangeBtn);
        viewChanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });

        // Button Creation
        ArrayAdapter<CharSequence> stateSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
        stateSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateSpinnerAdapter);
        stateSpinner.setOnItemSelectedListener(this);

        // Updates the TextViews with JQuery Data
        updateView();
    }

    private void updateView() {
        tvCases.setText(handler.getTvCases());
        tvActive.setText(handler.getTvActive());
        tvTodayCases.setText(handler.getTvTodayCases());
        tvTotalDeaths.setText(handler.getTvTotalDeaths());
        tvTodayDeaths.setText(handler.getTvTodayDeaths());
        tvAffectedCountries.setText(handler.getTvAffectedCountries());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        currentState = adapterView.getItemAtPosition(i).toString();
        updateView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //
    }

    public void openActivity2() {
        Intent intent = new Intent(this, Activity2.class);
        intent.putExtra(EXTRA_TEXT, currentState);
        startActivity(intent);
    }
}