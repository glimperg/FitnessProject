package nl.mprog.glimp.work_out;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class WorkoutActivity extends AppCompatActivity {

    ListView exerciseListView;
    ArrayList<Exercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        Workout workout = (Workout) getIntent().getSerializableExtra("workout");
        exerciseList = workout.getExercises();

        Toolbar toolbar = (Toolbar) findViewById(R.id.workoutToolbar);
        toolbar.setTitle(workout.getName());
        setSupportActionBar(toolbar);

        // TODO: aangeven welke equipment je nodig hebt

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
