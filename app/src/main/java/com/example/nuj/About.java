package com.example.nuj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class About extends AppCompatActivity {

    private ImageButton btnFaq;
    private ImageButton btnAboutNuj;
    private ImageButton btnAboutAnqi;
    private ImageButton btnBack;

    //Builds the GUI screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        // Links buttons to their corresponding views in the GUI
        btnFaq = findViewById(R.id.btnFaq);
        btnAboutNuj = findViewById(R.id.btnAboutNuj);
        btnAboutAnqi = findViewById(R.id.btnAboutAnqi);
        btnBack = findViewById(R.id.btnBack);

        // Button click takes user to FAQ acreen
        btnFaq.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                toFaqPage();
            }
        });

        // Button click takes user to About Nuj screen
        btnAboutNuj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                toAboutNuj();
            }
        });

        // Button click takes user to About Anqi screen
        btnAboutAnqi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                toAboutAnqi();
            }
        });

        // Button click takes user back to home screen
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                toHome();
            }
        });

    }

    //Takes the user back to the home screen
    public void toHome(){
        this.finish();
    }

    // Launches the Faq Page screen
    public void toFaqPage(){
        Intent intent = new Intent(this, FaqPage.class);
        startActivity(intent);
    }

    // Launches the About Nuj Screen
    public void toAboutNuj(){
        Intent intent = new Intent(this, AboutNuj.class);
        startActivity(intent);
    }

    // Launches the About Anqi screen
    public void toAboutAnqi(){
        Intent intent = new Intent(this, AboutAnqi.class);
        startActivity(intent);
    }

}
