package com.example.nuj;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AboutNuj extends AppCompatActivity {

    private TextView aboutNujDescription;
    private ImageButton btnBack;

    //This is the text file that the Nuj description will be stored in
    private static final String ABOUT_NUJ_TXT = "AboutNuj.txt";

    //Builds GUI screen on launch
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_nuj);

        // Links textviews and buttons to their corresponding views in the GUI
        aboutNujDescription = findViewById(R.id.aboutNujDescription);
        btnBack = findViewById(R.id.btnBackFromAboutNuj);

        //Sets the text on the screen using the text file containing info about Nuj
        readDescription();

        //Links the back button to the goBack() method
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    //Reads the questions and answers from the text file and saves the data into 2D array
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void readDescription(){

        String text = "";
        try {
            text = new String ( Files.readAllBytes(Paths.get(ABOUT_NUJ_TXT) ));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        aboutNujDescription.setText(text);

    }

    //Takes the user back to the previous screen
    public void goBack(){
        this.finish();
    }
}
