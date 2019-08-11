package com.example.nuj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class AboutAnqi extends AppCompatActivity {

    private ImageButton btnBackFromAboutAnqi;

    //Builds GUI on launch
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_anqi);

        //Links back button to corresponding view in GUI
        btnBackFromAboutAnqi = findViewById(R.id.btnBackFromAboutAnqi);

        //Links the back button to the goBack() method
        btnBackFromAboutAnqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    //Takes the user back to the previous screen
    public void goBack() {
        this.finish();
    }
}
