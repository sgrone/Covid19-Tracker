package com.example.covid_19tracker;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity
        extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    /*---------------------------------------------------
        GLOBAL VARIABLES
      ---------------------------------------------------*/
    private ArrayList<StateData> tempStateList = new ArrayList<StateData>(); // REPLACE ALL REFERENCES WITH stateList
    private ArrayList<StateData> stateList;
    private final static int CASES = 1, DEATHS = 2, HOSPITALIZED = 3;
    private final static int ASC = 1, DESC = 2;
    private int metric = CASES, sortBy = ASC;
    private TextView sortedStateList;
    private StateData currentState;
    private DataHandler handler;
    private Spinner stateSpinner, metricSpinner, sortSpinner;
    private Button fetchDataBtn, viewChanceBtn;

    /*---------------------------------------------------
        This method will run when the app is loaded
      ---------------------------------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link those objects with their respective id's
        // that we have given in .XML file
        sortedStateList = findViewById(R.id.sortedStateList);
        spinnerSetup(); // Initialize spinners (drop-down lists)
    }

    /*---------------------------------------------------
        Drop-down menu setup
     ---------------------------------------------------*/
    private void spinnerSetup() {
        stateSpinner = findViewById(R.id.stateSpinner);
        metricSpinner = findViewById(R.id.metricSpinner);
        sortSpinner = findViewById(R.id.sortSpinner);

        // stateSpinner setup
        ArrayAdapter<CharSequence> stateSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
        stateSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateSpinnerAdapter);
        stateSpinner.setOnItemSelectedListener(this);
        stateSpinner.setSelection(21); // Michigan by default

        //metricSpinner setup
        ArrayAdapter<CharSequence> metricSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.metrics, android.R.layout.simple_spinner_item);
        metricSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        metricSpinner.setAdapter(metricSpinnerAdapter);
        metricSpinner.setOnItemSelectedListener(this);
        metricSpinner.setSelection(1); // Cases by default

        //sortSpinner setup
        ArrayAdapter<CharSequence> sortSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.sortOptions, android.R.layout.simple_spinner_item);
        sortSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortSpinnerAdapter);
        sortSpinner.setOnItemSelectedListener(this);
        sortSpinner.setSelection(0); // Ascending by default

        // Button Creation
        fetchDataBtn = (Button) findViewById(R.id.fetchDataBtn);
        viewChanceBtn = (Button) findViewById(R.id.viewChangeBtn);

        fetchDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData(); // This will collect data from DataHanndler and load it into StateData objects
                // which get stored in stateList ArrayList
            }
        });

        viewChanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2(); // Open second screen on app
            }
        });
    }

    /*---------------------------------------------------
        Runs DataHandler class
        Will create ArrayList of StateData objects for each state
        Use getter methods on state class to access data
    ---------------------------------------------------*/
    public void fetchData() {
        //Test check to see if fetchData recognizes stateList as already filled with
        // data if you click it a second time.
        if (stateList != null && stateList.size() > 0) {
            int testLength = stateList.size();

            Log.i("Check", "ArrayList SECONDARY Length Check: " + testLength);
        }
        this.stateList = new ArrayList<StateData>();
        this.handler = new DataHandler(this.getApplicationContext());
        stateList = handler.pullData(stateList);
        populateStateList();
        Toast toast = Toast.makeText(this, "Fetching data", Toast.LENGTH_SHORT);
        toast.show();

        int testLength = stateList.size();

        Log.i("Check", "ArrayList Length Check: " + testLength);
    }

    /*---------------------------------------------------
        Determine what occurs when selected drop-down items change
    ---------------------------------------------------*/
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.stateSpinner:
                currentState = selectState(adapterView.getItemAtPosition(i).toString());
                break;
            case R.id.metricSpinner:
                if (adapterView.getItemAtPosition(i).toString().equals("Total Cases")) {
                    setMetric(CASES);
                }
                if (adapterView.getItemAtPosition(i).toString().equals("Total Deaths")) {
                    setMetric(DEATHS);
                }
                if (adapterView.getItemAtPosition(i).toString().equals("Currently Hospitalized")) {
                    setMetric(HOSPITALIZED);
                }

                sortStateList(getMetric(), getSortBy());
                break;
            case R.id.sortSpinner:
                if (adapterView.getItemAtPosition(i).toString().equals("Ascending")) {
                    setSortBy(ASC);
                }
                if (adapterView.getItemAtPosition(i).toString().equals("Descending")) {
                    setSortBy(DESC);
                }

                sortStateList(getMetric(), getSortBy());
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

    /*---------------------------------------------------
        Switches to screen 2
        Sends current stateObject selected with stateSpinner (MI by default)
    ---------------------------------------------------*/
    public void openActivity2() {
        Intent intent = new Intent(this, Activity2.class);
        intent.putExtra("currentState", currentState);
        if (currentState != null)
            startActivity(intent);
        else {
            Toast toast = Toast.makeText(this, "State data not found", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /*---------------------------------------------------
        UPDATE
        Currently a dummy-list of 5 states is created for testing purposes
    ---------------------------------------------------*/
    private void populateStateList() {

        // TEMP STATE DATA
        StateData california = new StateData();
        california.setStateInitials("CA");
        california.setDeaths(50000);
        california.setHospCur(2000);
        california.setPosResult(249);

        StateData michigan = new StateData();
        michigan.setStateInitials("MI");
        michigan.setDeaths(3500);
        michigan.setHospCur(1570);
        michigan.setPosResult(499);

        StateData florida = new StateData();
        florida.setStateInitials("FL");
        florida.setDeaths(4500);
        florida.setHospCur(1600);
        florida.setPosResult(9999);

        StateData ohio = new StateData();
        ohio.setStateInitials("OH");
        ohio.setDeaths(5600);
        ohio.setHospCur(800);
        ohio.setPosResult(1999);

        StateData texas = new StateData();
        texas.setStateInitials("TX");
        texas.setDeaths(1000);
        texas.setHospCur(10000);
        texas.setPosResult(3999);

        // TO_DO - REPLACE tempStateList WITH stateList ONCE IMPLEMENTED
        tempStateList.add(california);
        tempStateList.add(michigan);
        tempStateList.add(florida);
        tempStateList.add(ohio);
        tempStateList.add(texas);

        currentState = michigan;

        sortStateList(getMetric(), getSortBy());
    }

    /*---------------------------------------------------
        When a state in chosen in stateSpinner, this method
        matches that state to a state in stateList
    ---------------------------------------------------*/
    public StateData selectState(String stateName) {
        StateData current = null;
        for (int i = 0; i < tempStateList.size(); i++) {
            if (stateName.equals(tempStateList.get(i).getStateName()))
                current = tempStateList.get(i);
        }
        return current;
    }

    /*---------------------------------------------------
        Prints sorted list of states at bottom of app
    ---------------------------------------------------*/
    public void printStateList() {
        String stateString = "";
        for (int i = 0; i < tempStateList.size(); i++) {
            Integer temp = i + 1;
            stateString = stateString + temp.toString() + ". " + tempStateList.get(i).getStateName() + "\n";
        }
        sortedStateList.setText(stateString);
    }

    /*---------------------------------------------------
        This method will sort the states based on parameters selected in
        metricSpinner and sortSpinner

        code modified from Rajat Mishra's bubble sort
        on https://geeksforgeeks.org/bubble-sort/
    ---------------------------------------------------*/
    public void sortStateList(int metric, int sortBy) {
        //SORT BY CASES
        if (metric == CASES) {
            // Sort by ASC
            int n = tempStateList.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (tempStateList.get(j).getPosResult() > tempStateList.get(j + 1).getPosResult()) {
                        StateData temp = tempStateList.get(j);
                        tempStateList.set(j, tempStateList.get(j + 1));
                        tempStateList.set(j + 1, temp);
                    }
                }
            }
            // IF DESC, reverse order
            if (sortBy == DESC) {
                Collections.reverse(tempStateList);
            }
        }

        //SORT BY DEATHS
        if (metric == DEATHS) {
            // Sort by ASC
            int n = tempStateList.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (tempStateList.get(j).getDeaths() > tempStateList.get(j + 1).getDeaths()) {
                        StateData temp = tempStateList.get(j);
                        tempStateList.set(j, tempStateList.get(j + 1));
                        tempStateList.set(j + 1, temp);
                    }
                }
            }
            // IF DESC, reverse order
            if (sortBy == DESC) {
                Collections.reverse(tempStateList);
            }
        }

        //SORT BY HOSPITALIZATIONS
        if (metric == HOSPITALIZED) {
            // Sort by ASC
            int n = tempStateList.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (tempStateList.get(j).getHospCur() > tempStateList.get(j + 1).getHospCur()) {
                        StateData temp = tempStateList.get(j);
                        tempStateList.set(j, tempStateList.get(j + 1));
                        tempStateList.set(j + 1, temp);
                    }
                }
            }
            // IF DESC, reverse order
            if (sortBy == DESC) {
                Collections.reverse(tempStateList);
            }
        }
        printStateList();
    }

    public int getMetric() {
        return metric;
    }

    public void setMetric(int metric) {
        this.metric = metric;
    }

    public int getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }

}