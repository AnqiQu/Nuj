package com.example.nuj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;


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

        //reload();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Accesses the user's data and goals from the user text file and goals database
        textFile = new ManageTextFile();
        textFile.readUserInfo(this);
        db = new ManageDatabase(this);

        //Calls method to instantiate new user
        instantiateUser();

        //Calls the method to populate the goals
        populateGoals();

        //Links the TextViews to their corresponding views in the GUI
        txtWelcome = findViewById(R.id.txtWelcome);
        txtYourGoals = findViewById(R.id.txtYourGoals);
        txtMessage = findViewById(R.id.txtMessage);

        // Clicking on text takes user to screen displaying all their goals
        txtYourGoals.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                toGoals();
            }
        });

        // Clicking on text takes user to screen displaying their stats
        txtMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                toStats();
            }
        });

        //Personalises the welcome message to the user
        txtWelcome.setText("Welcome " + user.getName() + " :)");

        //Displays a personal message based on the user's productivity
        txtMessage.setText(displayMessage());

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

    // Instantiates a user object using the user's info stored in the text file
    public void instantiateUser(){

        user = new User(
                textFile.getUserName(),
                textFile.getBirthday(),
                textFile.getJoinedDate(),
                db.getAllGoals(),
                db.getCompletedGoals(),
                db.getOngoingGoals());
    }

    //Creates and populates the scrollable list of goals
    public void populateGoals(){
        //Creates an ArrayList of all the ongoing goals using data from the database
        ArrayList<String> goalList = new ArrayList<>();
        for (int i = 1; i <= db.getOngoingGoals().size(); i++) {
            goalList.add(db.getOngoingGoals().get(i).getDescription());
        }

        // Set the ListView to its corresponding view
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
    }

    //Displays an appropriate message for the user based on their productivity
    public String displayMessage(){
        double average = user.getAverage();
        double deviation = user.getDeviation();
        double recentAverage = user.getRecentAverage();

        if (recentAverage < average - deviation){
            return "Your goals are sad that they are not being completed... Is everything okay?";
        }else if (recentAverage > average + deviation){
            return "Wow, you are doing amazing! Keep smashing your goals!";
        }else{
            return "You are on track with your goals! Keep up the good work!";
        }
    }

    // Links button to Goals screen
    public void toGoals(){
        Intent intent = new Intent(this, Goals.class);
        startActivity(intent);
    }

    // Links button to Stats screen
    public void toStats(){
        Intent intent = new Intent(this, Stats.class);
        startActivity(intent);
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
