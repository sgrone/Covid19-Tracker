package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    private TextView stateName;
    private ImageView stateImage;
    private String currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Intent intent = getIntent();
        currentState = intent.getStringExtra(MainActivity.EXTRA_TEXT);

        stateName = (TextView) findViewById(R.id.stateName);
        stateImage = findViewById(R.id.stateMap);
        stateName.setText(currentState);
        setStateImage();


    }

    private void setStateImage() {
        switch(currentState) {
            case "Alabama":
                stateImage.setImageResource(R.drawable.ic_alabama);
                break;
            case "Alaska":
                stateImage.setImageResource(R.drawable.ic_alaska);
                break;
            case "Arizona":
                stateImage.setImageResource(R.drawable.ic_arizona);
                break;
            case "Arkansas":
                stateImage.setImageResource(R.drawable.ic_arkansas);
                break;
            case "California":
                stateImage.setImageResource(R.drawable.ic_california);
                break;
            default:
                break;
        }
        setStateColor();
    }

    private void setStateColor() {
        stateImage.setColorFilter(Color.RED);
    }
}