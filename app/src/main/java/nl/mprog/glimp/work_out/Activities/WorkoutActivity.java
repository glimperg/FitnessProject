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

// TODO: ergens plaatjes toevoegen in ListView die de categorie aangeven

public class WorkoutActivity extends AppCompatActivity {
    private static final String TAG = "WorkoutActivity";

    ListView exerciseListView;
    ArrayList<Exercise> exerciseList;
    Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        if (CheckNetwork.isInternetAvailable(WorkoutActivity.this)) {
            workout = (Workout) getIntent().getSerializableExtra("workout");
            exerciseList = workout.getExercises();

            Toolbar toolbar = (Toolbar) findViewById(R.id.workoutToolbar);
            toolbar.setTitle(workout.getName());
            setSupportActionBar(toolbar);

            exerciseListView = (ListView) findViewById(R.id.exerciseWorkoutListView);
            ExerciseListAdapter exerciseListAdapter = new ExerciseListAdapter(this, exerciseList);
            exerciseListView.setAdapter(exerciseListAdapter);

            String equipment = getEquipment();
            // remove last comma and space
            equipment = equipment.substring(0, equipment.length() - 2);
            String equipmentString = "Equipment: " + equipment;
            TextView equipmentView = (TextView) findViewById(R.id.equipmentTextView);
            equipmentView.setText(equipmentString);

            setListener();
        } else {
            CheckNetwork.displayAlertDialog(WorkoutActivity.this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.actionEdit) {

            Intent intent = new Intent(WorkoutActivity.this, CreateWorkoutActivity.class);
            intent.putExtra("workout", workout);
            intent.putExtra("editWorkout", true);
            startActivity(intent);
            finish();
            return true;

        } else if (item.getItemId() == R.id.actionDelete) {
            createBuilder();
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void createBuilder() {

        AlertDialog.Builder builder = new AlertDialog.Builder(WorkoutActivity.this);
        builder.setMessage("Are you sure you want to delete this workout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                removeWorkout(workout.getName());

                Intent intent = new Intent(WorkoutActivity.this, MainActivity.class);
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

    public void removeWorkout(final String name) {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userId);

        mDatabase.child("workouts").child(name).removeValue();

        mDatabase.child("planner").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.child("name").getValue().equals(name)) {
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

    private String getEquipment() {

        ArrayList<String> equipmentList = new ArrayList<>();

        for (Exercise exercise : exerciseList) {
            String exerciseEquipment = exercise.getEquipment();
            ArrayList<String> exerciseEquipmentList = new ArrayList<>(Arrays.asList(exerciseEquipment.split(", ")));

            for (String equipment : exerciseEquipmentList) {
                if (!equipmentList.contains(equipment)) {
                    equipmentList.add(equipment);
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String string : equipmentList) {
            String stringComma = string + ", ";
            stringBuilder.append(stringComma);
        }
        return stringBuilder.toString();
    }

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
