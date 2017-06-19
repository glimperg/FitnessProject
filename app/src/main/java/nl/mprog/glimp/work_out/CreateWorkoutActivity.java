package nl.mprog.glimp.work_out;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class CreateWorkoutActivity extends AppCompatActivity {

    private static final String TAG = "CreateWorkoutActivity";
    private static final int CHOOSE_EXERCISE_RESULT = 2;

    private ArrayList<Exercise> exerciseList = new ArrayList<>();
    private ListView exerciseListView;
    private ExerciseListAdapter exerciseListAdapter;

    private Spinner templateSpinner;
    private String template;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        exerciseListView = (ListView) findViewById(R.id.createWorkoutListView);
        exerciseListAdapter = new ExerciseListAdapter(CreateWorkoutActivity.this, exerciseList);
        exerciseListView.setAdapter(exerciseListAdapter);

        templateSpinner = setSpinner(R.id.templateSpinner, R.array.template_array);

        Toolbar toolbar = (Toolbar) findViewById(R.id.createWorkoutToolbar);
        setSupportActionBar(toolbar);

        //setTemplateListener();
        setListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.actionComplete) {
            saveWorkout();

            Intent intent = new Intent(CreateWorkoutActivity.this, MainActivity.class);
            startActivity(intent);

            Toast.makeText(CreateWorkoutActivity.this, "Saved workout", Toast.LENGTH_SHORT).show();
            // TODO: moet naar WorkoutListFragment gaan
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_complete, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CHOOSE_EXERCISE_RESULT) {
            if (resultCode == RESULT_OK) {

                Exercise exercise = (Exercise) data.getSerializableExtra("exercise");
                exerciseList.add(exercise);
                exerciseListAdapter.notifyDataSetChanged();
            } else {
                Log.d(TAG, "choosing exercise failed");
            }
        }
    }

    private Spinner setSpinner(int spinnerId, int arrayId) {

        Spinner spinner = (Spinner) findViewById(spinnerId);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CreateWorkoutActivity.this,
                arrayId, android.R.layout.simple_spinner_item);

        // specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return spinner;
    }

    public void setTemplateListener() {

        // TODO: templates implementeren
        templateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // get selected item
                template = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                template = "None";
            }
        });
    }

    public void setListener() {

        // TODO: evt. OnItemClickListener zodat je items kunt aanpassen ipv verwijderen en weer aanmaken
        // TODO: exerciseListView sorteerbaar maken

        exerciseListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // TODO: evt. dit veranderen naar selecteren van exercises om te verwijderen (of mss in WorkoutListFragment)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Exercise exercise = exerciseListAdapter.getItem(position);
                exerciseList.remove(exercise);
                exerciseListAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    public void newExercise(View view) {

        Intent intent = new Intent(CreateWorkoutActivity.this, ChooseExerciseActivity.class);
        startActivityForResult(intent, CHOOSE_EXERCISE_RESULT);
    }

    public void saveWorkout() {

        // TODO: ervoor zorgen dat workouts niet worden overschreven
        // TODO: niet saven als je geen exercises hebt toegevoegd

        EditText workoutEditText = (EditText) findViewById(R.id.workoutTitleEditText);
        String title = workoutEditText.getText().toString();
        Workout workout = new Workout(title, exerciseList);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // get reference to Firebase database containing Driver objects
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users").child(userId).child("workouts").child(title)
                .setValue(workout, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    System.out.println("Data could not be saved " + databaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
            }
        });
    }

}
