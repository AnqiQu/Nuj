package com.example.nuj;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    // Constants for logging and referring to a unique loader
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int TASK_LOADER_ID = 0;

    private ImageButton btnNewGoal;
    private ImageButton btnHelpOthers;
    private ImageButton btnGetHelp;
    private ImageButton btnAbout;
    private ArrayAdapter<String> mAdapter;
    ListView goalsListView;

    private ManageDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //reload();

        // Accesses the user's data and goals from the user text file and goals database
        ManageTextFile textFile = new ManageTextFile();
        textFile.readUserInfo(this);
        db = new ManageDatabase(this);

        //Creates an ArrayList of all the ongoing goals using data from the database
        ArrayList<String> goalList = new ArrayList<>();
        for (int i = 1; i <= db.getOngoingGoals().size(); i++) {
            goalList.add(db.getGoalDescription(i));
        }

        // Instantiates a user object using the user's info stored in the text file
        User user = new User(
                textFile.getUserName(),
                textFile.getBirthday(),
                textFile.getJoinedDate(),
                db.getAllGoals(),
                db.getCompletedGoals(),
                db.getOngoingGoals());

        db.close();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the RecyclerView to its corresponding view
        goalsListView = findViewById(R.id.goalsListView);

//        // Set the layout for the RecyclerView to be a linear layout, which measures and positions items within a RecyclerView into a linear list
//        goalsListView.setLayoutManager(new LinearLayoutManager(this));

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



//        /*
//         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
//         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
//         and uses callbacks to signal when a user is performing these actions.
//         */
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(ListView listView, ListView.View viewHolder, RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            // Called when a user swipes left or right on a ViewHolder
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//
//                // COMPLETED (1) Construct the URI for the item to delete
//                //[Hint] Use getTag (from the adapter code) to get the id of the swiped item
//                // Retrieve the id of the task to delete
//                Integer id = (Integer) viewHolder.itemView.getTag();
//
//                mAdapter.remove(id.intValue());
//                mAdapter.notifyDataSetChanged();
//
//
//                // COMPLETED (3) Restart the loader to re-query for all tasks after a deletion
//                getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, MainActivity.this);
//
//                Toast.makeText(getBaseContext(), "yeah! you completed it!", Toast.LENGTH_SHORT).show();
//
//            }
//        }).attachToListView(goalsListView);



        // Links buttons to their corresponding views in the GUI
        btnNewGoal = findViewById(R.id.btnNewGoal);
        btnHelpOthers = findViewById(R.id.btnHelpOthers);
        btnGetHelp = findViewById(R.id.btnGetHelp);
        btnAbout = findViewById(R.id.btnAbout);

        // Button click allows user to add a new goal
        btnNewGoal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                toNewGoal();
            }
        });

        // Button click takes user to Help Others screen
        btnHelpOthers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                toHelpOthers();
            }
        });

        // Button click takes user to Get Help screen
        btnGetHelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                toGetHelp();
            }
        });

        // Button click takes user to About screen
        btnAbout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                toAbout();
            }
        });

    }

    // This method is called after this activity has been paused or restarted.
    // Often, this is after new data has been inserted through an AddTaskActivity,
    // so this restarts the loader to re-query the underlying data for any changes.
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // re-queries for all tasks
//        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, (LoaderManager.LoaderCallbacks<Object>) this);
//    }

    public void reload (){
        finish();
        startActivity(getIntent());
    }

    // Links button to New Goal screen
    public void toNewGoal(){
        Intent intent = new Intent(this, NewGoal.class);
        startActivity(intent);
    }

    // Links button to Get Help screen
    public void toGetHelp(){
        Intent intent = new Intent(this, GetHelp.class);
        startActivity(intent);
    }

    // Links button to About screen
    public void toAbout(){
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    // Links button to Help Others screen
    public void toHelpOthers(){
        Intent intent = new Intent(this, HelpOthers.class);
        startActivity(intent);
    }

    // Stops the user from navigating back to the login screen
    @Override
    public void onBackPressed() {
    }

    // Reloads the goal list
    @Override
    public void onResume() {
        super.onResume();

        ArrayList<String> goalList = new ArrayList<>();
        for (int i = 1; i <= db.getOngoingGoals().size(); i++) {
            goalList.add(db.getGoalDescription(i));
        }

        mAdapter.clear();
        mAdapter.addAll(goalList);
        mAdapter.notifyDataSetChanged();
    }

}
