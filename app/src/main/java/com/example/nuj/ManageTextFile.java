package com.example.nuj;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class ManageTextFile {

    private String userName;
    private Date birthday;
    private Date joinedDate;

    //This is the text file that the user's info will be stored in
    private static final String NUJ_USER_TXT = "NujUser.txt";

    //Getters for the fields
    public String getUserName() {
        return userName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Date getJoinedDate() {
        return joinedDate;
    }

    //Saves user info to the text file
    public void saveInfo(String name, String birthday, String joinedDate, Context ctx) {

        try {
            Writer writeToFile = new FileWriter(new File(ctx.getFilesDir(), NUJ_USER_TXT));
            writeToFile.write(name + "#" + birthday + "#" + joinedDate);
            writeToFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Reads data from the text file and saves the data into the correct fields so that it can be accessed later by the program
    public void readUserInfo(Context ctx) {

        try {
            Scanner readFromFile = new Scanner(new File(ctx.getFilesDir(), NUJ_USER_TXT)).useDelimiter("#");

            while (readFromFile.hasNext()) {
                userName = readFromFile.next();
                birthday = getDate(readFromFile.next());
                joinedDate = getDate(readFromFile.next());
            }
            readFromFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    //Gets the current date from the device which is used to save the date that the user joined
    private Date getDate(String day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Date date = new Date();
        try {
            date = dateFormat.parse(day);
        } catch (ParseException e) {

        }
        return date;
    }

}
