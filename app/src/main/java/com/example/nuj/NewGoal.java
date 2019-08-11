package com.example.nuj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewGoal extends AppCompatActivity {

    private EditText goalDescription;
    private TextView txtDescriptionError;
    private SeekBar difficultySlider;
    private ImageButton btnAdd;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

    //Builds the GUI of the NewGoal screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        addGoalButton();

    }

    //Allows the user to call all the methods and create a new goal when the "Add goal" button is clicked
    public void addGoalButton() {

        final ManageDatabase addGoal = new ManageDatabase(this); // Allows the program to access the database

        // Creates the start button and allows it to be pressed
        btnAdd = findViewById(R.id.btnAdd);

        //updates the database once the + button is clicked
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //Validates the description field by making sure that the user has entered a description
                boolean valid = false;
                while (!valid) {
                    if (notPresent(getGoalDescription())) {
                        Toast.makeText(getBaseContext(), "Description please!", Toast.LENGTH_SHORT).show();
                        txtDescriptionError.findViewById(R.id.txtDescriptionError);
                        txtDescriptionError.setText("*Please enter a goal description"); //Displays error message
                    } else {
                        valid = true;
                    }
                }
                if (valid) {
                    //stores a new goal's info into the database
                    addGoal.addGoal(
                            getGoalDescription(),
                            getDifficulty(),
                            sdf.format(getCurrentDate()));

                    // Goes back to the the home screen
                    finish();
                }
            }

        });

    }

    //Access the user input in the description text field of the GUI
    public String getGoalDescription() {

        // Finds the editable text field component on the screen
        goalDescription = findViewById(R.id.goalDescription);

        // Stores the description that the user enters as a String
        String description = goalDescription.getText().toString();

        // Returns the description that the user enters in the login screen
        return description;
    }

    //Gets the current date from the device
    public Date getCurrentDate() {

        // Generates the current date
        Date currentDate = Calendar.getInstance().getTime();

        // Returns the current date as a String using the format "yyyy-MM-dd"
        return currentDate;
    }

    //Returns the difficulty of the task that the user has selected on a sliding scale
    public int getDifficulty() {

        //Initiates a sliding bar scale for the difficulty
        difficultySlider = findViewById(R.id.sbDifficulty);
        difficultySlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int difficulty = 3;

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getBaseContext(), ":)", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getBaseContext(), "Difficulty = " + difficulty, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Constantly changes the value
                Toast.makeText(getBaseContext(), progress, Toast.LENGTH_SHORT).show();
                difficulty = progress;
            }
        });

        //Returns the value of the seekbar scale
        return difficultySlider.getProgress();
    }

    // Presence check on goal description
    public boolean notPresent(String description) {
        return (description.length() == 0);
    }

}
