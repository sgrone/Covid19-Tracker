package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    private String currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Intent intent = getIntent();
        currentState = intent.getStringExtra(MainActivity.EXTRA_TEXT);

        TextView stateName = (TextView) findViewById(R.id.stateName);
        stateName.setText(currentState);
    }
}