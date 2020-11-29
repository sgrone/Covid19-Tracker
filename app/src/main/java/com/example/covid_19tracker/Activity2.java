package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {

    private TextView stateName;
    private ImageView stateImage;
    private StateData currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        // Pull current state object from first screen
        Intent intent = getIntent();
        currentState = (StateData)intent.getSerializableExtra("currentState");

        // Update screen information
        stateName = (TextView) findViewById(R.id.stateName);
        stateImage = findViewById(R.id.stateMap);
        stateName.setText(currentState.getStateName());
        setStateImage();
    }

    private void setStateImage() {
        switch(currentState.getStateName()) {
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
            case "Colorado":
                stateImage.setImageResource(R.drawable.ic_colorado);
                break;
            case "Connecticut":
                stateImage.setImageResource(R.drawable.ic_connecticut);
                break;
            case "Delaware":
                stateImage.setImageResource(R.drawable.ic_delaware);
                break;
            case "Florida":
                stateImage.setImageResource(R.drawable.ic_florida);
                break;
            case "Georgia":
                stateImage.setImageResource(R.drawable.ic_georgia);
                break;
            case "Hawaii":
                stateImage.setImageResource(R.drawable.ic_hawaii);
                break;
            case "Idaho":
                stateImage.setImageResource(R.drawable.ic_idaho);
                break;
            case "Illinois":
                stateImage.setImageResource(R.drawable.ic_illinois);
                break;
            case "Indiana":
                stateImage.setImageResource(R.drawable.ic_indiana);
                break;
            case "Iowa":
                stateImage.setImageResource(R.drawable.ic_iowa);
                break;
            case "Kansas":
                stateImage.setImageResource(R.drawable.ic_kansas);
                break;
            case "Kentucky":
                stateImage.setImageResource(R.drawable.ic_kentucky);
                break;
            case "Louisiana":
                stateImage.setImageResource(R.drawable.ic_louisiana);
                break;
            case "Maine":
                stateImage.setImageResource(R.drawable.ic_maine);
                break;
            case "Maryland":
                stateImage.setImageResource(R.drawable.ic_maryland);
                break;
            case "Massachusets":
                stateImage.setImageResource(R.drawable.ic_massachusetts);
                break;
            case "Michigan":
                stateImage.setImageResource(R.drawable.ic_michigan);
                break;
            case "Minnesota":
                stateImage.setImageResource(R.drawable.ic_minnesota);
                break;
            case "Mississippi":
                stateImage.setImageResource(R.drawable.ic_mississippi);
                break;
            case "Missouri":
                stateImage.setImageResource(R.drawable.ic_missouri);
                break;
            case "Montana":
                stateImage.setImageResource(R.drawable.ic_montana);
                break;
            case "Nebraska":
                stateImage.setImageResource(R.drawable.ic_nebraska);
                break;
            case "Nevada":
                stateImage.setImageResource(R.drawable.ic_nevada);
                break;
            case "New Hampshire":
                stateImage.setImageResource(R.drawable.ic_newhampshire);
                break;
            case "New Jersey":
                stateImage.setImageResource(R.drawable.ic_newjersey);
                break;
            case "New Mexico":
                stateImage.setImageResource(R.drawable.ic_newmexico);
                break;
            case "New York":
                stateImage.setImageResource(R.drawable.ic_newyork);
                break;
            case "North Carolina":
                stateImage.setImageResource(R.drawable.ic_northcarolina);
                break;
            case "North Dakota":
                stateImage.setImageResource(R.drawable.ic_northdakota);
                break;
            case "Ohio":
                stateImage.setImageResource(R.drawable.ic_ohio);
                break;
            case "Oklahoma":
                stateImage.setImageResource(R.drawable.ic_oklahoma);
                break;
            case "Oregon":
                stateImage.setImageResource(R.drawable.ic_newmexico);
                break;
            case "Pennsylvania":
                stateImage.setImageResource(R.drawable.ic_pennsylvania);
                break;
            case "Rhode Island":
                stateImage.setImageResource(R.drawable.ic_rhodeisland);
                break;
            case "South Carolina":
                stateImage.setImageResource(R.drawable.ic_southcarolina);
                break;
            case "South Dakota":
                stateImage.setImageResource(R.drawable.ic_southdakota);
                break;
            case "Tennessee":
                stateImage.setImageResource(R.drawable.ic_tennessee);
                break;
            case "Texas":
                stateImage.setImageResource(R.drawable.ic_texas);
                break;
            case "Utah":
                stateImage.setImageResource(R.drawable.ic_utah);
                break;
            case "Vermont":
                stateImage.setImageResource(R.drawable.ic_vermont);
                break;
            case "Virginia":
                stateImage.setImageResource(R.drawable.ic_virginia);
                break;
            case "Washington":
                stateImage.setImageResource(R.drawable.ic_washington);
                break;
            case "West Virginia":
                stateImage.setImageResource(R.drawable.ic_westvirginia);
                break;
            case "Wisconsin":
                stateImage.setImageResource(R.drawable.ic_wisconsin);
                break;
            case "Wyoming":
                stateImage.setImageResource(R.drawable.ic_wyoming);
                break;
            default:
                break;
        }
        setStateColor(currentState.getPositive()); //ADD CALL TO STATES CURRENT NUMBERS
    }

    // NOTE: Colors can be accessed in app --> res --> values --> colors.xml
    private void setStateColor(int cases) {
        if (cases < 250)
            stateImage.setColorFilter(ContextCompat.getColor(this, R.color.best));
        else if (cases < 500)
            stateImage.setColorFilter(ContextCompat.getColor(this, R.color.good));
        else if (cases < 1000)
            stateImage.setColorFilter(ContextCompat.getColor(this, R.color.fine));
        else if (cases < 2000)
            stateImage.setColorFilter(ContextCompat.getColor(this, R.color.above_average));
        else if (cases < 4000)
            stateImage.setColorFilter(ContextCompat.getColor(this, R.color.bad));
        else if (cases >= 4000)
            stateImage.setColorFilter(ContextCompat.getColor(this, R.color.worst));
        else {
            stateImage.setColorFilter(Color.RED);
            Toast toast = Toast.makeText(this, "Error setting state color", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}