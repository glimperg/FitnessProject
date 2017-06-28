package nl.mprog.glimp.work_out.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.mprog.glimp.work_out.Adapters.CustomExpandableListAdapter;
import nl.mprog.glimp.work_out.CheckNetwork;
import nl.mprog.glimp.work_out.Exercise;
import nl.mprog.glimp.work_out.R;

public class ChooseExerciseActivity extends AppCompatActivity {

    private static final String TAG = "ChooseExerciseActivity";

    private ExpandableListView exerciseListView;
    private ExpandableListAdapter exerciseListAdapter;
    private List<String> categoriesList = new ArrayList<>();
    private HashMap<String, List<Exercise>> childItemsList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exercise);

        // check internet connection
        if (CheckNetwork.isInternetAvailable(ChooseExerciseActivity.this)) {

            exerciseListView = (ExpandableListView) findViewById(R.id.chooseExerciseListView);

            // set up Toolbar
            Toolbar toolbar = (Toolbar) findViewById(R.id.chooseExerciseToolbar);
            setSupportActionBar(toolbar);

            setListAdapter();
            setChildClickListener();
            setLongClickListener();

        } else {
        CheckNetwork.displayAlertDialog(ChooseExerciseActivity.this);
        }
    }

    /**
     * Set ListAdapter containing Exercises (obtained from Firebase) to ListView.
     */
    private void setListAdapter() {

        // get reference to Firebase database
        DatabaseReference mDatabase = FirebaseDatabase
                .getInstance().getReference().child("exercises");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                // ArrayList containing all Exercises from a particular category
                ArrayList<Exercise> exerciseList = new ArrayList<>();

                // add every exercise in category to exerciseList
                for (DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()) {

                    Exercise exercise = exerciseSnapshot.getValue(Exercise.class);
                    exerciseList.add(exercise);
                }

                // add exerciseList to main list
                String category = dataSnapshot.getKey();
                categoriesList.add(category);
                childItemsList.put(category, exerciseList);

                // set ListAdapter to ListView
                exerciseListAdapter = new CustomExpandableListAdapter(
                        ChooseExerciseActivity.this, categoriesList, childItemsList);
                exerciseListView.setAdapter(exerciseListAdapter);
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
     * Set listener to ListView, choosing the Exercise upon child item click.
     */
    private void setChildClickListener() {

        exerciseListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent,
                                        View view, int groupPosition, int childPosition, long id) {

                // get Exercise corresponding to position
                Exercise exercise = (Exercise)
                        exerciseListAdapter.getChild(groupPosition, childPosition);

                // return to CreateWorkoutActivity with chosen Exercise
                Intent intent = new Intent();
                intent.putExtra("exercise", exercise);
                setResult(Activity.RESULT_OK, intent);
                finish();
                return true;
            }
        });
    }

    /**
     * Add LongClickListener to child items, sending the user to ExerciseActivity. Source used:
     * https://stackoverflow.com/questions/2353074/android-long-click-on-the-child-views-of-a-expandablelistview
     */
    private void setLongClickListener() {

        exerciseListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int childPosition = ExpandableListView.getPackedPositionChild(id);

                    // get Exercise corresponding to position
                    Exercise exercise = (Exercise) exerciseListAdapter.getChild(groupPosition,childPosition);

                    // go to ExerciseActivity
                    Intent intent = new Intent(ChooseExerciseActivity.this, ExerciseActivity.class);
                    intent.putExtra("exercise", exercise);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });
    }
}
