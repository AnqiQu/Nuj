package com.example.nuj;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static java.lang.String.valueOf;

public class ManageDatabase extends SQLiteOpenHelper {

    //Declares all the field names in the database as static and final
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserGoalsDB";
    private static final String TABLE_NAME = "tblGoals";
    private static final String KEY_ID = "id";
    private static final String KEY_DESCRIPTION = "Description";
    private static final String KEY_DIFFICULTY = "Difficulty";
    private static final String KEY_STARTDATE = "StartDate";
    private static final String KEY_ENDDATE = "EndDate";
    private static final String KEY_COMPLETED = "Completed";
    private static final String[] COLUMNS = { KEY_ID, KEY_DESCRIPTION, KEY_DIFFICULTY,
            KEY_STARTDATE, KEY_ENDDATE, KEY_COMPLETED };

    //Constructor method for the class
    ManageDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creates a new database table using SQL
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
                                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + KEY_DESCRIPTION + " TEXT, "
                                + KEY_DIFFICULTY + " TINYINT, "
                                + KEY_STARTDATE + " DATE, "
                                + KEY_ENDDATE + " DATE, "
                                + KEY_COMPLETED + " BOOLEAN)";
        System.out.println(CREATION_TABLE);
        db.execSQL(CREATION_TABLE);
    }

    // Called if the database version must be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    //Converts a date from String format to date
    private Date getDate(String day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy/MM/dd", Locale.getDefault());
        Date date = new Date();
        try {
            date = dateFormat.parse(day);
        } catch (ParseException e) {
            System.out.println("error");
        }
        return date;
    }

    //Gets the current date from the device
    public Date getCurrentDate(){

        // Generates the current date
        Date currentDate = Calendar.getInstance().getTime();

        // Returns the current date as a String using the format "yyyy-MM-dd"
        return currentDate;
    }

    //Creates a goal object using data from the database
    //Used with the methods that return lists of Goal items
    public Goal newGoal(Cursor cursor) {
        Goal goal = new Goal(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getInt(2),
                getDate(cursor.getString(3)),
                getDate(cursor.getString(4)),
                cursor.getInt(5) > 0);

        return goal;
    }

    //Queries the database for a goal with special primary key using SQL
    public Goal getGoal(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        //Query the database
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Goal goal = newGoal(cursor);

        return goal;
    }

    //Gets the goal description for a specific goal in the database using SQL
    public String getGoalDescription(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        //Query the database
        Cursor cursor = db.query(TABLE_NAME,
                new String[] {KEY_DESCRIPTION},
                "id = ?",
                new String[] { valueOf(id) },
                null,
                null,
                null,
                null);

        String output = "";

        //if the description field contains data
        if (cursor != null && cursor.moveToFirst()) {
            int idx = cursor.getColumnIndex(KEY_DESCRIPTION);
            output = cursor.getString(idx);
            cursor.close();
        }

        return output;

    }

    //Returns a list of all the goals in the database
    public List<Goal> getAllGoals() {

        List<Goal> goals = new LinkedList<Goal>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Goal goal = newGoal(cursor);
                goals.add(goal);
            } while (cursor.moveToNext());
        }

        return goals;
    }

    //Returns a list of the completed goals in the database
    public List<Goal> getCompletedGoals() {

        List<Goal> goals = new LinkedList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_COMPLETED + " = 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Goal goal = newGoal(cursor);
                goals.add(goal);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return goals;
    }

    //Returns a list of the incomplete goals in the database
    public List<Goal> getOngoingGoals() {

        List<Goal> goals = new LinkedList<>();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_COMPLETED + " = 0";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Goal goal = newGoal(cursor);
                goals.add(goal);
            } while (cursor.moveToNext());
        }

        return goals;
    }

    //Adds a goal to the database
    public void addGoal(String description, int difficulty, String startDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRIPTION, description);
        values.put(KEY_DIFFICULTY, difficulty);
        values.put(KEY_STARTDATE, "#" + startDate + "#");
        values.put(KEY_COMPLETED, false);
        // insert values into the table
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    //Updates a goal to make it completed
    public void updateCompleted(Goal goal) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ENDDATE, "#" + getCurrentDate().toString() +"#");
        values.put(KEY_COMPLETED, 1);
        db.update(TABLE_NAME, values, "id = " + goal.getId(),null);
        db.close();
    }

    //Updates a goal in the database (unused for now, for prospective future development)
    public void updateGoal(Goal goal) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRIPTION, goal.getDescription());
        values.put(KEY_DIFFICULTY, goal.getDifficulty());
        values.put(KEY_STARTDATE, "#" + goal.getStart().toString() +"#");
        db.update(TABLE_NAME, values, "id = " + goal.getId(),null);
        db.close();
    }

    //Deletes a goal from the database using SQL (unused for now, for prospective future development)
    public void deleteGoal(Goal goal) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { valueOf(goal.getId()) });
        db.close();
    }




}
