package com.example.nuj;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FaqPage extends AppCompatActivity {

    private TextView[] questions;
    private ImageButton btnBack;

    //2D array stores the question-answer pairs that is read from a text file
    private String[][] QA = new String[5][2];

    //This is the text file that the user's info will be stored in
    private static final String FAQs_TXT = "NujUser.txt";

    //Launches the GUI screen on on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_page);

        //Reads the questions and answers for the FAQ
        readQA();

        // Links textviews and buttons to their corresponding views in the GUI
        questions = new TextView[]{findViewById(R.id.Q1), findViewById(R.id.Q2), findViewById(R.id.Q3), findViewById(R.id.Q3), findViewById(R.id.Q4), findViewById(R.id.Q5)};
        btnBack = findViewById(R.id.btnBackFromFAQs);

        //Sets the questions using the array of questions
        for (int i = 0; i < 4; i++) {
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

        questions[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer(4);
            }
        });

        questions[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer(4);
            }
        });

        //Links the back button to the goBack() method
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    //Reads the questions and answers from the text file and saves the data into 2D array
    public void readQA(){

        try {
            Scanner readFromFile = new Scanner(new File(FAQs_TXT)).useDelimiter("#");
            for(int i = 0; i < 4; i++){
                for (int j = 0; j < 1; j++){
                    QA[i][j] = readFromFile.next();
                }
            }
            readFromFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    //Takes the user back to the previous screen
    public void goBack(){
        this.finish();
    }

    //Shows the answers in a pop-up dialog format
    public void showAnswer(int q){
        AlertDialog.Builder answer = new AlertDialog.Builder(this);
        answer.setCancelable(true);
        answer.setTitle(QA[q][0]);
        answer.setMessage(QA[q][1]);

        answer.show();
    }

}
