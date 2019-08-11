package com.example.nuj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class Goals extends AppCompatActivity {

    private ImageButton btnNewGoal2;
    private ImageButton btnBackFromGoals;
    private ArrayAdapter<String> mAdapter;
    private ListView allGoalsListView;

    private ManageDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        //Calls method to display goals on the screen
        displayGoals();

        // Links buttons to their corresponding views in the GUI
        btnNewGoal2 = findViewById(R.id.btnNewGoal2);
        btnBackFromGoals = findViewById(R.id.btnBackFromGoals);

        // Button click allows user to add a new goal
        btnNewGoal2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toNewGoal();
            }
        });
        btnBackFromGoals.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                goBack();
            }
        });
    }

    // Links button to New Goal screen
    public void toNewGoal() {
        Intent intent = new Intent(this, NewGoal.class);
        startActivity(intent);
    }

    //Takes the user back to the home screen
    public void goBack() {
        this.finish();
    }

    //Method displays all the user's goals
    public void displayGoals() {
        // Accesses the user's goals from the goals database
        db = new ManageDatabase(this);

        //Creates an ArrayList of all the ongoing goals using data from the database
        //Ongoing goals are first added to the list followed by completed goals
        ArrayList<String> allGoalsList = new ArrayList<>();
        for (int i = 1; i <= db.getOngoingGoals().size(); i++) {
            allGoalsList.add(db.getOngoingGoals().get(i).getDescription());
        }
        for (int i = 1; i <= db.getCompletedGoals().size(); i++) {
            allGoalsList.add(db.getCompletedGoals().get(i).getDescription());
        }
        db.close();

        // Set the ListView to its corresponding view
        allGoalsListView = findViewById(R.id.allGoalsListView);

        // Initialize the adapter and attach it to the RecyclerView
        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.list_item, // what view to use for the items
                    allGoalsList); // where to get all the data

            allGoalsListView.setAdapter(mAdapter); // set it as the adapter of the ListView instance
        } else {
            mAdapter.clear();
            mAdapter.addAll(allGoalsList);
            mAdapter.notifyDataSetChanged();
        }
    }

}
