package nl.mprog.glimp.work_out;

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

public class EditPlannerActivity extends AppCompatActivity {

    private static final String TAG = "EditPlannerActivity";

    EditPlannerAdapter editPlannerAdapter;
    ListView editPlannerListView;
    ArrayList<Workout> plannerArrayList;
    ArrayList<Workout> workoutList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_planner);

        Workout[] plannerWorkouts = (Workout[]) getIntent().getSerializableExtra("planner");
        plannerArrayList = new ArrayList<>(Arrays.asList(plannerWorkouts));

        // add non-workout options to list of Workouts
        workoutList.add(new Workout("Rest day", null));
        workoutList.add(new Workout("Cycling", null));
        workoutList.add(new Workout("Running", null));

        Toolbar toolbar = (Toolbar) findViewById(R.id.editPlannerToolbar);
        setSupportActionBar(toolbar);

        editPlannerListView = (ListView) findViewById(R.id.editPlannerListView);
        editPlannerAdapter = new EditPlannerAdapter(this, plannerArrayList);
        editPlannerListView.setAdapter(editPlannerAdapter);

        getWorkouts();
        setListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.actionComplete) {
            savePlanner();

            Intent intent = new Intent(EditPlannerActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

            Toast.makeText(EditPlannerActivity.this, "Saved planner", Toast.LENGTH_SHORT).show();
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

    public void getWorkouts() {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // get reference to Firebase database containing Workouts
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userId).child("workouts");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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

    public void setListener() {

        editPlannerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                int size = workoutList.size();
                final String[] workoutTitles = new String[size];
                for (int i = 0; i < size; i++) {
                    workoutTitles[i] = workoutList.get(i).getName();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(EditPlannerActivity.this);
                builder.setTitle("Choose a workout");
                builder.setItems(workoutTitles, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Workout workout = workoutList.get(which);
                        plannerArrayList.set(position, workout);
                        editPlannerAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });
    }

    public void savePlanner() {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // get reference to Firebase database containing Driver objects
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userId).child("planner").setValue(plannerArrayList);
    }

}
