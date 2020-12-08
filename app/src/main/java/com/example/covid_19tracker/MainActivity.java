package com.example.covid_19tracker;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;

public class MainActivity
        extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    /*---------------------------------------------------
        GLOBAL VARIABLES
      ---------------------------------------------------*/
    private ArrayList<StateData> stateList;
    private final static int DAILY_CASES = 1, TOTAL_CASES = 2, DAILY_DEATHS = 3, TOTAL_DEATHS = 4, DAILY_HOSPITALIZED = 5, TOTAL_HOSPITALIZED = 6;
    private final static int ASC = 1, DESC = 2;
    private int metric = TOTAL_CASES, sortBy = ASC;
    private TextView sortedStateList, stateNumbers;
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
        stateNumbers = findViewById(R.id.stateNumbers);
        spinnerSetup(); // Initialize spinners (drop-down lists)

        //Initial API data pulling.
        fetchData();
    }

    private void createMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.heatMap);
        MapActivity mapActivity = new MapActivity(this.getApplicationContext(), mapFragment, stateList);

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
        this.stateList = new ArrayList<StateData>();
        DataHandler dataPuller = new DataHandler(this.getApplicationContext(), this.stateList);
        dataPuller.start();

        /*While the Retrofit library does provide synchronously JSON pulling,
        some actions in DataHandler don't behave synchronously without the use of join()
        forcing the main thread (MainActivity) to wait for DataHandler.*/
        try {
            dataPuller.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stateList = dataPuller.getData();
        createMap();
        sortStateList(getMetric(), getSortBy());

        Toast toast = Toast.makeText(this, "Fetching data", Toast.LENGTH_SHORT);
        toast.show();
        barchart();

        //Test code for checking if State data was pulled in correctly.
        /*int testLength = stateList.size();
        Log.i("Check", "ArrayList Length Check: " + testLength);


=======
        int test1 = stateList.get(0).getPositiveIncrease();
        int test2 = stateList.get(3).getDeathIncrease();
        int test3 = stateList.get(5).getHospitalizedIncrease();
        Log.i("Check", "Variables Check: " + test1 + " | " + test2 + " | " + test3 + " | ");*/

    }
    public void barchart() {
        HorizontalBarChart barChart = (HorizontalBarChart) findViewById(R.id.chart);

        BarData data = new BarData();

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        ArrayList<String> labels = new ArrayList<>();
        for (int i=0;i<stateList.size();i++){
            labels.add(stateList.get(i).getStateName());
        }
        ArrayList<String> ylabels = new ArrayList<>();
        int dataCount=0;
        for (int i=0;i<stateList.size();++i) {
            BarEntry entry = new BarEntry(dataCount,stateList.get(i).getPositiveIncrease());
            valueSet1.add(entry);
            ylabels.add(" "+i);
            dataCount++;
        }
        List<IBarDataSet> dataSets = new ArrayList<>();
        BarDataSet bds = new BarDataSet(valueSet1, "Daily Cases ");
        bds.setColor(Color.RED);
        //bds.setColors(ColorTemplate.MATERIAL_COLORS);
        String[] xAxisLabels = labels.toArray(new String[0]);
        String[] yAxisLabels = ylabels.toArray(new String[0]);
        bds.setStackLabels(xAxisLabels);
        dataSets.add(bds);
        data.addDataSet(bds);
        data.setDrawValues(true);
        data.setBarWidth(0.7f);

        XAxis xaxis = barChart.getXAxis();
        xaxis.setDrawGridLines(false);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setGranularityEnabled(true);
        xaxis.setGranularity(1);
        xaxis.setDrawLabels(true);
        xaxis.setLabelCount(dataCount);
        // xaxis.setXOffset(10);
        xaxis.setDrawAxisLine(false);
        barChart.getXAxis().setSpaceMax(10f);
        //CategoryBarChartXaxisFormatter xaxisFormatter = new CategoryBarChartXaxisFormatter(xAxisLabels);
        //xaxis.setValueFormatter(xaxisFormatter);

        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setEnabled(false);

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawAxisLine(false);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        //barChart.getXAxis().setLabelCount(100);
        //barChart.getAxisLeft().setLabelCount(50);
        Legend legend = barChart.getLegend();
        legend.setEnabled(false);

        barChart.setFitBars(true);
        barChart.setData(data);
        barChart.setDescription(null);
        barChart.setXAxisRenderer(new CustomXAxisRendererHorizontalBarChart(barChart.getViewPortHandler(),xaxis,barChart.getTransformer(YAxis.AxisDependency.LEFT),barChart,labels.size()));
        barChart.animateY(2000);
        barChart.invalidate();
        barChart.setPinchZoom(true);

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
                if (adapterView.getItemAtPosition(i).toString().equals("Daily Cases")) {
                    setMetric(DAILY_CASES);
                }
                if (adapterView.getItemAtPosition(i).toString().equals("Total Cases")) {
                    setMetric(TOTAL_CASES);
                }
                if (adapterView.getItemAtPosition(i).toString().equals("Daily Deaths")) {
                    setMetric(DAILY_DEATHS);
                }
                if (adapterView.getItemAtPosition(i).toString().equals("Total Deaths")) {
                    setMetric(TOTAL_DEATHS);
                }
                if (adapterView.getItemAtPosition(i).toString().equals("Daily Hospitalizations")) {
                    setMetric(DAILY_HOSPITALIZED);
                }
                if (adapterView.getItemAtPosition(i).toString().equals("Currently Hospitalized")) {
                    setMetric(TOTAL_HOSPITALIZED);
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
        When a state in chosen in stateSpinner, this method
        matches that state to a state in stateList
    ---------------------------------------------------*/
    public StateData selectState(String stateName) {
        StateData current = null;
        for (int i = 0; i < stateList.size(); i++) {
            if (stateName.equals(stateList.get(i).getStateName()))
                current = stateList.get(i);
        }
        return current;
    }

    /*---------------------------------------------------
        Prints sorted list of states at bottom of app
    ---------------------------------------------------*/
    public void printStateList() {
        String stateString = "";
        for (int i = 0; i < stateList.size(); i++) {
            Integer temp = i + 1;
            stateString = stateString + temp.toString() + ". " + stateList.get(i).getStateName() + "\n";
        }
        sortedStateList.setText(stateString);
        printStateMetrics();
    }

    /*---------------------------------------------------
        Prints metrics for the sorted states
    ---------------------------------------------------*/

    public void printStateMetrics() {
        String metricString = "";
        for (int i = 0; i < stateList.size(); i++) {
            if (metric == DAILY_CASES)
                metricString = metricString + stateList.get(i).getPositiveIncrease() + "\n";
            if (metric == TOTAL_CASES)
                metricString = metricString + stateList.get(i).getPositive() + "\n";
            if (metric == DAILY_DEATHS)
                metricString = metricString + stateList.get(i).getDeathIncrease() + "\n";
            if (metric == TOTAL_DEATHS)
                metricString = metricString + stateList.get(i).getDeath() + "\n";
            if (metric == DAILY_HOSPITALIZED)
                metricString = metricString + stateList.get(i).getHospitalizedIncrease() + "\n";
            if (metric == TOTAL_HOSPITALIZED)
                metricString = metricString + stateList.get(i).getHospitalizedIncrease() + "\n";
        }

        stateNumbers.setText(metricString);
    }

    /*---------------------------------------------------
        This method will sort the states based on parameters selected in
        metricSpinner and sortSpinner

        code modified from Rajat Mishra's bubble sort
        on https://geeksforgeeks.org/bubble-sort/
    ---------------------------------------------------*/
    public void sortStateList(int metric, int sortBy) {
        //SORT BY DAILY CASES
        if (metric == DAILY_CASES) {
            // Sort by ASC
            int n = stateList.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (stateList.get(j).getPositiveIncrease() > stateList.get(j + 1).getPositiveIncrease()) {
                        StateData temp = stateList.get(j);
                        stateList.set(j, stateList.get(j + 1));
                        stateList.set(j + 1, temp);
                    }
                }
            }
            // IF DESC, reverse order
            if (sortBy == DESC) {
                Collections.reverse(stateList);
            }
        }

        //SORT BY TOTAL CASES
        if (metric == TOTAL_CASES) {
            // Sort by ASC
            int n = stateList.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (stateList.get(j).getPositive() > stateList.get(j + 1).getPositive()) {
                        StateData temp = stateList.get(j);
                        stateList.set(j, stateList.get(j + 1));
                        stateList.set(j + 1, temp);
                    }
                }
            }
            // IF DESC, reverse order
            if (sortBy == DESC) {
                Collections.reverse(stateList);
            }
        }

        //SORT BY DAILY DEATHS
        if (metric == DAILY_DEATHS) {
            // Sort by ASC
            int n = stateList.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (stateList.get(j).getDeathIncrease() > stateList.get(j + 1).getDeathIncrease()) {
                        StateData temp = stateList.get(j);
                        stateList.set(j, stateList.get(j + 1));
                        stateList.set(j + 1, temp);
                    }
                }
            }
            // IF DESC, reverse order
            if (sortBy == DESC) {
                Collections.reverse(stateList);
            }
        }

        //SORT BY TOTAL DEATHS
        if (metric == TOTAL_DEATHS) {
            // Sort by ASC
            int n = stateList.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (stateList.get(j).getDeath() > stateList.get(j + 1).getDeath()) {
                        StateData temp = stateList.get(j);
                        stateList.set(j, stateList.get(j + 1));
                        stateList.set(j + 1, temp);
                    }
                }
            }
            // IF DESC, reverse order
            if (sortBy == DESC) {
                Collections.reverse(stateList);
            }
        }

        //SORT BY DAILY HOSPITALIZATIONS
        if (metric == DAILY_HOSPITALIZED) {
            // Sort by ASC
            int n = stateList.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (stateList.get(j).getHospitalizedIncrease() > stateList.get(j + 1).getHospitalizedIncrease()) {
                        StateData temp = stateList.get(j);
                        stateList.set(j, stateList.get(j + 1));
                        stateList.set(j + 1, temp);
                    }
                }
            }
            // IF DESC, reverse order
            if (sortBy == DESC) {
                Collections.reverse(stateList);
            }
        }

        //SORT BY TOTAL HOSPITALIZATIONS
        if (metric == TOTAL_HOSPITALIZED) {
            // Sort by ASC
            int n = stateList.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (stateList.get(j).getHospitalizedCurrently() > stateList.get(j + 1).getHospitalizedCurrently()) {
                        StateData temp = stateList.get(j);
                        stateList.set(j, stateList.get(j + 1));
                        stateList.set(j + 1, temp);
                    }
                }
            }
            // IF DESC, reverse order
            if (sortBy == DESC) {
                Collections.reverse(stateList);
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