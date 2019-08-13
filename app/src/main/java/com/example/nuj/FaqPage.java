package com.example.nuj;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class FaqPage extends AppCompatActivity {

    private TextView[] questions;
    private ImageButton btnBack;

    //2D array stores the question-answer pairs
    private String[][] QA = new String[][] {
        {"How do I add a new goal?",
            "You can add a new goal using the \"New goal\" button on the home screen"
        },
        {"How do I mark a goal off as completed?",
            "Simply tap and hold on the goal on the list on the home screen"
        },
        {"I accidentally said I completed a goal but I actually haven't. How do I undo it?",
            "Unfortunately at this point you can't undo any completed goals"
        },
        {"Do you have an iOS version of Nuj?",
            "Unfortunately not :( However, its development is in progress so watch this space"
        },
        {"Does Nuj steal your personal data?",
                "Not at all! All you data is safe with us :) (we are not Facebook)"
        }
    };

    //Launches the GUI screen on on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_page);

        // Links textviews and buttons to their corresponding views in the GUI
        questions = new TextView[]{findViewById(R.id.Q1), findViewById(R.id.Q2), findViewById(R.id.Q3), findViewById(R.id.Q4), findViewById(R.id.Q5)};
        btnBack = findViewById(R.id.btnBackFromFAQs);

        //Sets the questions using the array of questions
        for (int i = 0; i < 5; i++) {
            questions[i].setText(QA[i][0]);
        }

        //Displays the different answers when the questions are clicked
        questions[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer(0);
            }
        });

        questions[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer(1);
            }
        });

        questions[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer(2);
            }
        });

        questions[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer(3);
            }
        });

        questions[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer(4);
            }
        });

        //Links the back button to the goBack() method
        btnBack.setOnClickListener(new View.OnClickListener() {
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

    //Shows the answers in a pop-up dialog format
    //Takes in the number of the question as a parameter
    //And displays the corresponding answer
    public void showAnswer(int q) {
        AlertDialog.Builder answer = new AlertDialog.Builder(this);
        answer.setCancelable(true);
        answer.setTitle(QA[q][0]);
        answer.setMessage(QA[q][1]);

        answer.show();
    }

}
