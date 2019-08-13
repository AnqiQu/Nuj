package com.example.nuj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AboutNuj extends AppCompatActivity {

    private TextView aboutNujDescription;
    private ImageButton btnBack;

    //Builds GUI screen on launch
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_nuj);

        // Links textviews and buttons to their corresponding views in the GUI
        aboutNujDescription = findViewById(R.id.aboutNujDescription);
        btnBack = findViewById(R.id.btnBackFromAboutNuj);

        //Sets the text on the screen using the text file containing info about Nuj
        setDescription();

        //Links the back button to the goBack() method
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    //Sets the text description to the data
    public void setDescription() {

        String text = "A goal-tracking app, reimagined. To provide support, to motivate, to help take care of mental health, and to nudge you towards greater productivity. That is the philosophy behind Nuj, which is designed as a platform for you to track and manage your goals, with a particular focus on motivation, support and self-care. Nuj was designed thoughtfully, with the understanding that motivation and productivity are some of the greatest daily challenges, particularly for those who are struggling with their mental health\n" +
                "\n" +
                "Goals can be rated on a sliding scale of difficulty which helps you to prioritise. Break down big, overwhelming goals into smaller goals which are easier to manage and complete\n" +
                "\n" +
                "Nuj tracks the number of goals you complete each week, so if it detects a drop in productivity, it can let you know. A sudden decrease in motivation - and consequently productivity - could be a warning sign of a more serious health issue\n" +
                "\n" +
                "Nuj also provides tips on how to take care of your mental health, as well as steps showing you how to seek help if needed. There are also tips and information for you if you wish to help others take care of their mental health\n";

        aboutNujDescription.setText(text);

    }

    //Takes the user back to the previous screen
    public void goBack() {
        this.finish();
    }
}
