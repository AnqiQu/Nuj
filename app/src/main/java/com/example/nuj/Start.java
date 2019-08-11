package com.example.nuj;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Start extends AppCompatActivity {

    private ImageButton btnStart;
    private EditText nameInput;
    private TextView txtNameError;
    private TextView txtBirthdayError;
    SharedPreferences sp;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

    //Builds the GUI screen on launch
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = this.getSharedPreferences("login", MODE_PRIVATE);

        // Automatically logs the user in and takes them to the home screen when they launch the program again
        if (sp.getBoolean("logged", false)) {
            startActivity(new Intent(this, MainActivity.class));
        }

        //Calls the addStartButton() method to save user data and launch the home screen
        addStartButton();
    }

    //This method codes for all the functionality of the start button
    public void addStartButton() {

        final Context context = this;
        final ManageTextFile createUser = new ManageTextFile(); // Allows the program to access the text file
        final Calendar myCalendar = Calendar.getInstance();

        // Links the class to the GUI input
        final EditText edittext = findViewById(R.id.Birthday);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            private void updateLabel() {
                edittext.setText(sdf.format(myCalendar.getTime()));
            }

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        // Creates the pop-up calendar where the user can pick their date of birth
        edittext.setOnClickListener(new View.OnClickListener() {

            //Displays the pop-up calendar when the user click on the date of birth field
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Start.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Links the Start button to the GUI
        btnStart = findViewById(R.id.btnStart);

        //Saves the user's data and launches the home screen once the user presses the start button
        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //Validates the data before proceeding
                boolean valid = false;
                while (!valid) {
                    //Presence check on name field
                    if (notPresent(getNameInput())) {
                        Toast.makeText(getBaseContext(), "Enter your name", Toast.LENGTH_SHORT).show();
                        txtNameError.findViewById(R.id.txtNameError);
                        txtNameError.setText("*Please enter your name"); //Displays error message
                    }
                    //Type check on name field
                    else if (!isAlpha(getNameInput())) {
                        Toast.makeText(getBaseContext(), "Name invalid", Toast.LENGTH_SHORT).show();
                        txtNameError.findViewById(R.id.txtNameError);
                        txtNameError.setText("*You name cannot include numbers or symbols"); //Displays error message
                    }
                    //Presence check on date field
                    if (notPresent(sdf.format(myCalendar.getTime()))) {
                        Toast.makeText(getBaseContext(), "Select your birthday", Toast.LENGTH_SHORT).show();
                        txtBirthdayError.findViewById(R.id.txtBirthdayError);
                        txtBirthdayError.setText("*Please select your birthday"); //Displays error message
                    }
                    //Type check on date field
                    else if (isFuture(myCalendar.getTime())) {
                        Toast.makeText(getBaseContext(), "Invalid birthday", Toast.LENGTH_SHORT).show();
                        txtBirthdayError.findViewById(R.id.txtBirthdayError);
                        txtBirthdayError.setText("*You birthday cannot be in the future. You are not Terminator"); //Displays error message
                    }

                    if (!notPresent(getNameInput()) && isAlpha(getNameInput()) && !notPresent(sdf.format(myCalendar.getTime())) && !isFuture(myCalendar.getTime())) {
                        valid = true;
                    }
                }
                if (valid) {
                    //stores all the user's info into a text file
                    createUser.saveInfo(
                            getNameInput(),
                            sdf.format(myCalendar.getTime()),
                            sdf.format(getCurrentDate()),
                            getApplicationContext());

                    // Edits the Shared preference to automatically log the user in when they launch the program again
                    sp.edit().putBoolean("logged", true).apply();

                    // Launches the home screen
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        });

    }

    //Allows the user to enter their name in the text field
    public String getNameInput() {

        // Finds the editable text field component on the screen
        nameInput = findViewById(R.id.nameInput);

        // Stores the name that the user enters as a String
        String name = nameInput.getText().toString();

        // Returns the name that the user enters in the login screen
        return name;
    }

    //Gets the current date from the device
    public Date getCurrentDate() {

        // Generates the current date
        Date currentDate = Calendar.getInstance().getTime();

        // Returns the current date as a String using the format "yyyy-MM-dd"
        return currentDate;
    }

    // Presence check (used on multiple fields)
    public boolean notPresent(String text) {
        return (text.length() == 0);
    }

    // Type check (only alpha characters)
    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z ]*");
    }

    // Logic check on date
    public boolean isFuture(Date date) {
        return (date.compareTo(getCurrentDate()) > 0);
    }

}
