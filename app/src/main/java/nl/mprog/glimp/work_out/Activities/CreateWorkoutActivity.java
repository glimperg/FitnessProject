package nl.mprog.glimp.work_out.Activities;

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
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nl.mprog.glimp.work_out.Adapters.ExerciseListAdapter;
import nl.mprog.glimp.work_out.CheckNetwork;
import nl.mprog.glimp.work_out.Exercise;
import nl.mprog.glimp.work_out.Activities.MainActivity.MainActivity;
import nl.mprog.glimp.work_out.R;
import nl.mprog.glimp.work_out.Workout;


public class CreateWorkoutActivity extends AppCompatActivity {

    private static final String TAG = "CreateWorkoutActivity";
    private static final int CHOOSE_EXERCISE_RESULT = 2;

    private ArrayList<Exercise> exerciseList = new ArrayList<>();
    private ListView exerciseListView;
    private ExerciseListAdapter exerciseListAdapter;

    private DatabaseReference mDatabase;
    private String userId;

    private Spinner templateSpinner;
    private EditText workoutEditText;
    private TextView lengthTextView;

    private String template;
    private int length = 1;
    private boolean editWorkout;
    private String oldWorkoutTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        if (CheckNetwork.isInternetAvailable(CreateWorkoutActivity.this)) {

            // get user ID and reference to Firebase database
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference();

            Intent intent = getIntent();
            editWorkout = intent.getBooleanExtra("editWorkout", false);

            Toolbar toolbar = (Toolbar) findViewById(R.id.createWorkoutToolbar);
            setSupportActionBar(toolbar);
            workoutEditText = (EditText) findViewById(R.id.workoutTitleEditText);

            if (editWorkout) {
                toolbar.setTitle("Edit workout");
                Workout workout = (Workout) intent.getSerializableExtra("workout");
                exerciseList = workout.getExercises();
                oldWorkoutTitle = workout.getName();
                workoutEditText.setText(oldWorkoutTitle);
            }

            // get ListView and set ListAdapter
            exerciseListView = (ListView) findViewById(R.id.createWorkoutListView);
            exerciseListAdapter = new ExerciseListAdapter(CreateWorkoutActivity.this, exerciseList);
            exerciseListView.setAdapter(exerciseListAdapter);

            setSpinner();
            setTemplateListener();
            setSeekBarListener();
            setListViewListener();
        } else {
            CheckNetwork.displayAlertDialog(CreateWorkoutActivity.this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.actionComplete) {

            final String workoutTitle = workoutEditText.getText().toString();

            // check if title only contains numbers and letters
            boolean validTitle = workoutTitle.matches("[a-zA-Z0-9]+");

            if (exerciseList.size() > 0 && validTitle) {

                mDatabase.child("users").child(userId).child("workouts").child(workoutTitle)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // check if workout with name workoutTitle already exists
                        // or (when editing workout) if the title is unchanged
                        if (!dataSnapshot.exists() || workoutTitle.equals(oldWorkoutTitle)) {

                            saveWorkout(workoutTitle);
                            Toast.makeText(CreateWorkoutActivity.this, "Saved workout",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CreateWorkoutActivity.this, "That title is already used",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
                return true;

            } else if (exerciseList.size() == 0) {
                Toast.makeText(CreateWorkoutActivity.this, "Please select at least one exercise",
                        Toast.LENGTH_SHORT).show();
                return true;

            } else {
                Toast.makeText(CreateWorkoutActivity.this, "Please enter a valid title",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
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

                // get exercise and add to list
                Exercise exercise = (Exercise) data.getSerializableExtra("exercise");
                exerciseList.add(exercise);
                exerciseListAdapter.notifyDataSetChanged();
            } else {
                Log.d(TAG, "choosing exercise failed");
            }
        }
    }

    public void saveWorkout(String title) {

        if (editWorkout) {
            // remove old workout
            mDatabase.child("users").child(userId).child("workouts").child(oldWorkoutTitle).removeValue();
        }

        // save workout to database
        Workout workout = new Workout(title, exerciseList);
        mDatabase.child("users").child(userId).child("workouts").child(title).setValue(workout);

        // go back to MainActivity
        Intent intent = new Intent(CreateWorkoutActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setSpinner() {

        templateSpinner = (Spinner) findViewById(R.id.templateSpinner);

        // create ArrayAdapter using template array and default layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                CreateWorkoutActivity.this, R.array.template_array, android.R.layout.simple_spinner_item);

        // specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        templateSpinner.setAdapter(adapter);
    }

    private void setSeekBarListener() {

        SeekBar lengthSeekBar = (SeekBar) findViewById(R.id.lengthSeekBar);
        lengthTextView = (TextView) findViewById(R.id.lengthNumberTextView);

        lengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                length = progress + 1;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // set length of SeekBar to TextView
                String lengthString = length + "/" + (seekBar.getMax()+1);
                lengthTextView.setText(lengthString);
                getTemplate();
            }
        });
    }

    public void setTemplateListener() {

        templateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // get selected item
                template = parent.getItemAtPosition(position).toString();
                getTemplate();
                // TODO: dit misschien efficienter maken zodat hij niet elke keer alle data hoeft op te vragen
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                template = "None";
            }
        });
    }

    public void getTemplate() {

        mDatabase.child("templates").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                // find workout corresponding to template
                if (dataSnapshot.getKey().equals(template)) {

                    Workout templateWorkout = dataSnapshot.getValue(Workout.class);

                    // set new Exercise list
                    ArrayList<Exercise> newExercises = templateWorkout.getExercises();
                    exerciseList.clear();

                    exerciseList.addAll(newExercises.subList(0,length));
                    exerciseListAdapter.notifyDataSetChanged();
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

    public void setListViewListener() {

        exerciseListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // remove exercise upon long click
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
}
