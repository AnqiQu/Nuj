package com.example.nuj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Stats extends AppCompatActivity {

    private TextView lblAverage;
    private TextView lblDeviation;
    private TextView lblRecent;
    private ImageButton btnBackFromStats;

    //Builds the GUI screen on launch
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //Links the TextViews to their corresponding views in the GUI
        lblAverage = findViewById(R.id.lblAverage);
        lblDeviation = findViewById(R.id.lblDeviation);
        lblRecent = findViewById(R.id.lblRecent);

        //Formats the stats to round each value off to 2 decimal places
        DecimalFormat df = new DecimalFormat("####0.00");

        //Sets the values of the average, deviation and recent average according to the user's personal data
       if(String.valueOf(MainActivity.user.getAverage()) != "NaN"){
           lblAverage.setText(String.valueOf(df.format(MainActivity.user.getAverage())));
       }
       if(String.valueOf(MainActivity.user.getDeviation()) != "NaN"){
           lblDeviation.setText(String.valueOf(df.format(MainActivity.user.getDeviation())));
       }
       if(String.valueOf(MainActivity.user.getRecentAverage()) != "NaN"){
           lblRecent.setText(String.valueOf(df.format(MainActivity.user.getRecentAverage())));
       }

        //Links the back button to corresponding GUI element and takes the user back to the home screen
        btnBackFromStats = findViewById(R.id.btnBackFromStats);
        btnBackFromStats.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                goBack();
            }
        });

    }

    //Takes the user back to the home screen
    public void goBack() {
        this.finish();
    }
}
