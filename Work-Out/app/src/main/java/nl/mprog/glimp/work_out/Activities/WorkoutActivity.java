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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import nl.mprog.glimp.work_out.CheckNetwork;
import nl.mprog.glimp.work_out.Adapters.ExerciseListAdapter;
import nl.mprog.glimp.work_out.Exercise;
import nl.mprog.glimp.work_out.Activities.MainActivity.MainActivity;
import nl.mprog.glimp.work_out.R;
import nl.mprog.glimp.work_out.Workout;

/**
 * Created by Gido Limperg on 8-6-2017.
 * Activity which displays information about a Workout.
 * Also lets the user edit or delete this Workout.
 */

public class WorkoutActivity extends AppCompatActivity {
    private static final String TAG = "WorkoutActivity";

    ListView exerciseListView;
    ArrayList<Exercise> exerciseList;
    Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        // check internet connection
        if (CheckNetwork.isInternetAvailable(WorkoutActivity.this)) {

            // get Workout from intent
            workout = (Workout) getIntent().getSerializableExtra("workout");
            exerciseList = workout.getExercises();

            setViews();

            setListener();

        } else {
            CheckNetwork.displayAlertDialog(WorkoutActivity.this);
        }
    }

    /**
     * Set various Views and set adapter to ListView.
     */
    public void setViews() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.workoutToolbar);
        toolbar.setTitle(workout.getName());
        setSupportActionBar(toolbar);

        // set ListAdapter to ListView
        exerciseListView = (ListView) findViewById(R.id.exerciseWorkoutListView);
        ExerciseListAdapter exerciseListAdapter = new ExerciseListAdapter(this, exerciseList);
        exerciseListView.setAdapter(exerciseListAdapter);

        // initialise equipment TextView
        String equipment = getEquipment();
        String equipmentString = "Equipment: " + equipment;
        TextView equipmentView = (TextView) findViewById(R.id.equipmentTextView);
        equipmentView.setText(equipmentString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_workout, menu);
        return true;
    }

    /**
     * @inheritDoc
     * Lets the user edit or delete the Workout.
     * @param item the clicked MenuItem.
     * @return a boolean (true or false).
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.actionEdit) {

            // go to CreateWorkoutActivity
            Intent intent = new Intent(WorkoutActivity.this, CreateWorkoutActivity.class);
            intent.putExtra("workout", workout);
            intent.putExtra("editWorkout", true);
            startActivity(intent);
            finish();

            return true;

        } else if (item.getItemId() == R.id.actionDelete) {

            createAlertDialog();
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Creates and displays an AlertDialog for confirmation of deletion.
     */
    private void createAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(WorkoutActivity.this);
        builder.setMessage("Are you sure you want to delete this workout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                removeWorkout(workout.getName());

                // go back to MainActivity
                Intent intent = new Intent(WorkoutActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

                Toast.makeText(WorkoutActivity.this, "Deleted workout", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Removes Workout from Firebase database.
     * @param name: title of the Workout to be removed.
     */
    public void removeWorkout(final String name) {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userId);

        // remove Workout from list of Workouts
        mDatabase.child("workouts").child(name).removeValue();

        // check if planner contains Workout
        mDatabase.child("planner").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.child("name").getValue().equals(name)) {
                    // change Workout to default Workout
                    Workout restDay = new Workout("Rest day", null);
                    mDatabase.child("planner").child(dataSnapshot.getKey()).setValue(restDay);
                }
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
     * Gets all necessary equipment for a workout.
     * @return String with required equipment for the workout.
     */
    private String getEquipment() {
        // ArrayList with all equipment for the workout
        ArrayList<String> equipmentList = new ArrayList<>();

        for (Exercise exercise : exerciseList) {

            // String with equipment for one exercise, separated by a comma
            String exerciseEquipment = exercise.getEquipment();

            // ArrayList with all equipment for one exercise
            ArrayList<String> exerciseEquipmentList = new ArrayList<>(Arrays.asList(exerciseEquipment.split(", ")));

            // add equipment if not yet in equipmentList
            for (String equipment : exerciseEquipmentList) {
                if (!equipmentList.contains(equipment)) {
                    equipmentList.add(equipment);
                }
            }
        }

        // convert ArrayList to String
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : equipmentList) {
            String stringComma = string + ", ";
            stringBuilder.append(stringComma);
        }
        String equipment = stringBuilder.toString();

        // remove last comma and space
        return equipment.substring(0, equipment.length() - 2);
    }


    /**
     * Sets OnItemClickListener to ListView, sending the user to ExerciseActivity upon click.
     */
    public void setListener() {

        exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra("exercise", exerciseList.get(position));
                startActivity(intent);
            }
        });
    }
}
