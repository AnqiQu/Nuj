package com.example.nuj;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ImageButton btnNewGoal;
    private ImageButton btnHelpOthers;
    private ImageButton btnGetHelp;
    private ImageButton btnAbout;
    private ArrayAdapter<String> mAdapter;
    private ListView goalsListView;
    private TextView txtWelcome;
    private TextView txtYourGoals;
    private TextView txtMessage;

    private ManageDatabase db;
    private ManageTextFile textFile;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Accesses the user's data and goals from the user text file and goals database
        textFile = new ManageTextFile();
        textFile.readUserInfo(this);
        db = new ManageDatabase(this);

        //Calls method to instantiate new user
        instantiateUser();

        //Links the TextViews to their corresponding views in the GUI
        txtWelcome = findViewById(R.id.txtWelcome);
        txtYourGoals = findViewById(R.id.txtYourGoals);
        txtMessage = findViewById(R.id.txtMessage);

        // Clicking on text takes user to screen displaying all their goals
        txtYourGoals.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toGoals();
            }
        });

        //Personalises the welcome message to the user
        txtWelcome.setText("Welcome " + user.getName() + " :)");

        //Displays a personal message based on the user's productivity
        txtMessage.setText(displayMessage());

        // Clicking on text takes user to screen displaying their stats
        txtMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toStats();
            }
        });

        // Links buttons to their corresponding views in the GUI
        btnNewGoal = findViewById(R.id.btnNewGoal);
        btnHelpOthers = findViewById(R.id.btnHelpOthers);
        btnGetHelp = findViewById(R.id.btnGetHelp);
        btnAbout = findViewById(R.id.btnAbout);

        // Button click allows user to add a new goal
        btnNewGoal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toNewGoal();
            }
        });

        // Button click takes user to Help Others screen
        btnHelpOthers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toHelpOthers();
            }
        });

        // Button click takes user to Get Help screen
        btnGetHelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toGetHelp();
            }
        });

        // Button click takes user to About screen
        btnAbout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toAbout();
            }
        });

    }

    // Instantiates a user object using the user's info stored in the text file
    public void instantiateUser() {

        user = new User(
                textFile.getUserName(),
                textFile.getBirthday(),
                textFile.getJoinedDate(),
                db.getAllGoals(),
                db.getCompletedGoals(),
                db.getOngoingGoals());
    }

    //Displays an appropriate message for the user based on their productivity
    public String displayMessage() {
        double average = user.getAverage();
        double deviation = user.getDeviation();
        double recentAverage = user.getRecentAverage();

        if (db.getCompletedGoals().size() == 0){
            return "Complete some goals to get started :)";
        }else if (recentAverage < average - deviation) {
            return "Your goals are sad that they are not being completed... Is everything okay?";
        } else if (recentAverage > average + deviation) {
            return "Wow, you are doing amazing! Keep smashing your goals!";
        } else {
            return "You are on track with your goals! Keep up the good work!";
        }
    }

    // Links button to Goals screen
    public void toGoals() {
        Intent intent = new Intent(this, Goals.class);
        startActivity(intent);
    }

    // Links button to Stats screen
    public void toStats() {
        Intent intent = new Intent(this, Stats.class);
        startActivity(intent);
    }

    // Links button to New Goal screen
    public void toNewGoal() {
        Intent intent = new Intent(this, NewGoal.class);
        startActivity(intent);
    }

    // Links button to Get Help screen
    public void toGetHelp() {
        Intent intent = new Intent(this, GetHelp.class);
        startActivity(intent);
    }

    // Links button to About screen
    public void toAbout() {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    // Links button to Help Others screen
    public void toHelpOthers() {
        Intent intent = new Intent(this, HelpOthers.class);
        startActivity(intent);
    }

    // Stops the user from navigating back to the login screen
    @Override
    public void onBackPressed() {
    }

    // Reloads the goal list
    // This method is called after this activity has been paused or restarted.
    // Usually, this is after a new task has been added
    // so this restarts the loader to re-query the underlying data for any changes.
    @Override
    public void onResume() {
        super.onResume();

        //Repopulates the list of goals

        //Creates an ArrayList of all the ongoing goals using data from the database
        final ArrayList<String> goalList = new ArrayList<>();

        // List of the ongoing goals
        final List<Goal> ongoing = db.getOngoingGoals();
        for (int i = 0; i < db.getOngoingGoals().size(); i++) {
            goalList.add(ongoing.get(i).getDescription());
        }

        // Set the ListView to its corresponding view
        goalsListView = findViewById(R.id.goalsListView);

        // Initialize the adapter and attach it to the RecyclerView
        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.list_item, // what view to use for the items
                    goalList); // where to get all the data

            goalsListView.setAdapter(mAdapter); // set it as the adapter of the ListView instance
        } else {
            mAdapter.clear();
            mAdapter.addAll(goalList);
            mAdapter.notifyDataSetChanged();
        }

        //Deletes a goal from the list when the user long clicks on it
        goalsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //Removes the goal from the list that is displayed
                goalList.remove(position);

                //Updates it to be complete in the database
                db.updateCompleted(ongoing.get(position));

                mAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Goal completed", Toast.LENGTH_LONG).show();

                //Restarts the adapter to update list
                //Called when a goal is deleted and the list must be updates
                mAdapter.clear();
                mAdapter.addAll(goalList);
                mAdapter.notifyDataSetChanged();

                return true;
            }

        });

    }
}
