package nl.mprog.glimp.work_out;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

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

        setListener();
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
