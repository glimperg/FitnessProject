package nl.mprog.glimp.work_out.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import nl.mprog.glimp.work_out.Adapters.EditPlannerAdapter;
import nl.mprog.glimp.work_out.Activities.MainActivity.MainActivity;
import nl.mprog.glimp.work_out.CheckNetwork;
import nl.mprog.glimp.work_out.R;
import nl.mprog.glimp.work_out.Workout;

/**
 * Created by Gido Limperg on 8-6-2017.
 * Activity in which the planner can be edited.
 */

public class EditPlannerActivity extends AppCompatActivity {

    private static final String TAG = "EditPlannerActivity";

    private EditPlannerAdapter editPlannerAdapter;
    private ListView editPlannerListView;
    private ArrayList<Workout> plannerArrayList;
    private ArrayList<Workout> workoutList = new ArrayList<>();

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_planner);

        // check internet connection
        if (CheckNetwork.isInternetAvailable(EditPlannerActivity.this)) {

            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // get reference to Firebase database
            mDatabase = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(userId);

            // get planner array from intent
            Workout[] plannerWorkouts = (Workout[]) getIntent().getSerializableExtra("planner");

            // convert Workout[] to ArrayList<Workout>
            plannerArrayList = new ArrayList<>(Arrays.asList(plannerWorkouts));

            // add non-workout options to list of Workouts
            workoutList.add(new Workout("Rest day", null));
            workoutList.add(new Workout("Cycling", null));
            workoutList.add(new Workout("Running", null));

            // initialise Toolbar
            Toolbar toolbar = (Toolbar) findViewById(R.id.editPlannerToolbar);
            setSupportActionBar(toolbar);

            // created ListAdapter and set to ListView
            editPlannerListView = (ListView) findViewById(R.id.editPlannerListView);
            editPlannerAdapter = new EditPlannerAdapter(this, plannerArrayList);
            editPlannerListView.setAdapter(editPlannerAdapter);

            getWorkouts();
            setListViewListener();

        } else {
            CheckNetwork.displayAlertDialog(EditPlannerActivity.this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_complete, menu);
        return true;
    }

    /**
     * @inheritDoc
     * Saves planner when button is clicked and go back to MainActivity.
     * @param item the clicked MenuItem.
     * @return a boolean (true or false).
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.actionComplete) {

            // save planner
            mDatabase.child("planner").setValue(plannerArrayList);

            // go back to MainActivity
            Intent intent = new Intent(EditPlannerActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

            Toast.makeText(EditPlannerActivity.this, "Saved planner", Toast.LENGTH_SHORT).show();
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Gets a list of Workouts from Firebase database.
     */
    public void getWorkouts() {

        mDatabase.child("workouts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                // get Workout and add to list
                Workout workout = dataSnapshot.getValue(Workout.class);
                workoutList.add(workout);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // getting the data failed, log a message
                Log.w(TAG, "Something went wrong: ", databaseError.toException());
            }
        });
    }

    /**
     * Sets listener to the ListView, letting the user choose a Workout on the clicked day.
     */
    public void setListViewListener() {

        editPlannerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                int size = workoutList.size();

                // list with every Workout title
                final String[] workoutTitles = new String[size];
                for (int i = 0; i < size; i++) {
                    workoutTitles[i] = workoutList.get(i).getName();
                }

                // display AlertDialog for selecting a Workout
                AlertDialog.Builder builder = new AlertDialog.Builder(EditPlannerActivity.this);
                builder.setTitle("Choose a workout");
                builder.setItems(workoutTitles, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // get corresponding Workout and set to planner
                        Workout workout = workoutList.get(which);
                        plannerArrayList.set(position, workout);
                        editPlannerAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });
    }
}
