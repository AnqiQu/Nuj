package com.example.nuj;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class HelpOthers extends AppCompatActivity {

    private ImageButton btnBack;
    private ImageButton btnGeneral;
    private ImageButton btnDepression;
    private ImageButton btnAnxiety;
    private ImageButton btnED;

    // Builds the GUI on launch
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_others);

        //Links the buttons to the corresponding GUI views
        btnBack = findViewById(R.id.btnBackFromHelpOthers);
        btnGeneral = findViewById(R.id.btnGeneral);
        btnDepression = findViewById(R.id.btnDepression);
        btnAnxiety = findViewById(R.id.btnAnxiety);
        btnED = findViewById(R.id.btnED);

        // Button click takes user back to home screen
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toHome();
            }
        });

        // Button click takes user to website for general help
        btnGeneral.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                goToGeneralHelp();
            }
        });

        // Button click takes user to website for help with depression
        btnDepression.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                goToDepressionHelp();
            }
        });

        // Button click takes user to website for help with anxiety
        btnAnxiety.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                goToAnxietyHelp();
            }
        });

        // Button click takes user to website for help with eating disorders
        btnED.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                goToEDHelp();
            }
        });

    }

    // Method that launches browser and sends user to specific website
    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    // Method linked to back button that takes user to home screen
    public void toHome() {
        this.finish();
    }

    // Sends user to website for general help
    public void goToGeneralHelp() {
        goToUrl("https://www.mentalhealth.org.uk/publications/supporting-someone-mental-health-problem");
    }

    // Sends user to website for depression help
    public void goToDepressionHelp() {
        goToUrl("https://www.helpguide.org/articles/depression/coping-with-depression.htm");
    }

    // Sends user to website for anxiety help
    public void goToAnxietyHelp() {
        goToUrl("https://www.helpguide.org/articles/anxiety/anxiety-disorders-and-anxiety-attacks.htm");
    }

    // Sends user to website for eating disorders help
    public void goToEDHelp() {
        goToUrl("https://www.helpguide.org/articles/eating-disorders/helping-someone-with-an-eating-disorder.htm");
    }

}
