package com.example.nuj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class GetHelp extends AppCompatActivity {

    private ImageButton btnBack;

    //Builds the GUI screen on launch
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_help);

        // Links buttons to their corresponding views in the GUI
        btnBack = findViewById(R.id.btnBackFromGetHelp);

        // Button click takes user back to home screen
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toHome();
            }
        });

    }

    // Method linked to back button that takes user to home screen
    public void toHome() {
        this.finish();
    }
}
