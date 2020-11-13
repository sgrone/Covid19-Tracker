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


import java.util.ArrayList;

public class MainActivity
        extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_TEXT = "com.example.covid_19tracker.EXTRA_TEXT";
    private ArrayList<String> tempStateList = new ArrayList<String>();

    // Create the object of TextView
    TextView tvCases, tvTotalDeaths, sortedStateList;

    String currentState = "Michigan";
    DataHandler handler;
    Spinner stateSpinner;
    Spinner metricSpinner;
    Spinner sortSpinner;
    Button fetchDataBtn;
    Button viewChanceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link those objects with their respective id's
        // that we have given in .XML file
        tvCases = findViewById(R.id.tvCases);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        sortedStateList = findViewById(R.id.sortedStateList);
        this.handler = new DataHandler(this.getApplicationContext());

        // Updates the TextViews with JQuery Data
        updateView();
        spinnerSetup();
        populateStateList();
    }

    private void spinnerSetup() {
        stateSpinner = findViewById(R.id.stateSpinner);
        metricSpinner = findViewById(R.id.metricSpinner);
        sortSpinner = findViewById(R.id.sortSpinner);

        // stateSpinner setup
        ArrayAdapter<CharSequence> stateSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
        stateSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateSpinnerAdapter);
        stateSpinner.setOnItemSelectedListener(this);
        stateSpinner.setSelection(21);

        //metricSpinner setup
        ArrayAdapter<CharSequence> metricSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.metrics, android.R.layout.simple_spinner_item);
        metricSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        metricSpinner.setAdapter(metricSpinnerAdapter);
        metricSpinner.setOnItemSelectedListener(this);

        //sortSpinner setup
        ArrayAdapter<CharSequence> sortSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.sortOptions, android.R.layout.simple_spinner_item);
        sortSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortSpinnerAdapter);
        sortSpinner.setOnItemSelectedListener(this);

        // Button Creation
        fetchDataBtn = (Button) findViewById(R.id.fetchDataBtn);
        viewChanceBtn = (Button) findViewById(R.id.viewChangeBtn);

        fetchDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData();
            }
        });

        viewChanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });
    }

    public void fetchData() {
        //TEMP:
        Toast toast = Toast.makeText(this, "Fetching data", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void updateView() {
        tvCases.setText(handler.getTvCases());
        tvTotalDeaths.setText(handler.getTvTotalDeaths());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId()) {
            case R.id.stateSpinner:
                currentState = adapterView.getItemAtPosition(i).toString();
                updateView();
                break;
            case R.id.metricSpinner:
                //TO_DO add method to change metric that states are sorted by
                break;
            case R.id.sortSpinner:
                //TO_DO add method to change ascending or descening
                break;
            default:
                Toast toast = Toast.makeText(this, "ERROR WITH SPINNERS", Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
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

    private void populateStateList() {
        String stateString = "";
        tempStateList.add("Alabama");
        tempStateList.add("Alaska");
        tempStateList.add("Arizona");
        tempStateList.add("Arkansas");
        tempStateList.add("California");
        tempStateList.add("Colorado");
        tempStateList.add("Connecticut");
        tempStateList.add("Delaware");
        tempStateList.add("Florida");
        tempStateList.add("Georgia");
        tempStateList.add("Hawaii");
        tempStateList.add("Idaho");
        tempStateList.add("Illinois");
        tempStateList.add("Indiana");
        tempStateList.add("Iowa");
        tempStateList.add("Kansas");
        tempStateList.add("Kentucky");
        tempStateList.add("Louisiana");
        tempStateList.add("Maine");
        tempStateList.add("Maryland");
        tempStateList.add("Massachusets");
        tempStateList.add("Michigan");
        tempStateList.add("Minnesota");
        tempStateList.add("Mississippi");
        tempStateList.add("Missouri");
        tempStateList.add("Montana");
        tempStateList.add("Nebraska");
        tempStateList.add("Nevada");
        tempStateList.add("New Hampshire");
        tempStateList.add("New Jersey");
        tempStateList.add("New Mexico");
        tempStateList.add("New York");
        tempStateList.add("North Carolina");
        tempStateList.add("North Dakota");
        tempStateList.add("Ohio");
        tempStateList.add("Oklahoma");
        tempStateList.add("Oregon");
        tempStateList.add("Pennsylvania");
        tempStateList.add("Rhode Island");
        tempStateList.add("South Carolina");
        tempStateList.add("South Dakota");
        tempStateList.add("Tennessee");
        tempStateList.add("Texas");
        tempStateList.add("Utah");
        tempStateList.add("Vermont");
        tempStateList.add("Virginia");
        tempStateList.add("Washington");
        tempStateList.add("West Virginia");
        tempStateList.add("Wisconsin");
        tempStateList.add("Wyoming");

        for (int i = 0; i < tempStateList.size(); i++) {
            Integer temp = i + 1;
            stateString = stateString + temp.toString() + ". " + tempStateList.get(i) + "\n";
        }
        sortedStateList.setText(stateString);
    }


}

