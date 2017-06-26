package nl.mprog.glimp.work_out;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

// TODO: ergens plaatjes toevoegen in ListView die de categorie aangeven

public class WorkoutActivity extends AppCompatActivity {

    ListView exerciseListView;
    ArrayList<Exercise> exerciseList;
    Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

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
        equipment = equipment.substring(0,equipment.length() - 2);
        String equipmentString = "Equipment: " + equipment;
        TextView equipmentView = (TextView) findViewById(R.id.equipmentTextView);
        equipmentView.setText(equipmentString);

        setListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.actionEdit) {

            Intent intent = new Intent(WorkoutActivity.this, CreateWorkoutActivity.class);
            intent.putExtra("workout", workout);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_workout, menu);
        return true;
    }

    private void createBuilder() {

        AlertDialog.Builder builder = new AlertDialog.Builder(WorkoutActivity.this);
        builder.setMessage("Are you sure you want to delete this workout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference()
                        .child("users").child(userId).child("workouts");
                mDatabase.child(workout.getName()).removeValue();

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
