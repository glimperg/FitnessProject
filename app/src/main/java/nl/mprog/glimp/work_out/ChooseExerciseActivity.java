package nl.mprog.glimp.work_out;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChooseExerciseActivity extends AppCompatActivity {

    private static final String TAG = "ChooseExerciseActivity";

    private ExpandableListView exerciseListView;
    private ExpandableListAdapter exerciseListAdapter;
    private List<String> categoriesList = new ArrayList<>();
    private HashMap<String, List<Exercise>> childItemsList = new HashMap<>();
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exercise);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("exercises");
        exerciseListView = (ExpandableListView) findViewById(R.id.chooseExerciseListView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.chooseExerciseToolbar);
        setSupportActionBar(toolbar);

        setAdapter();
        setClickListener();
        setLongClickListener();
    }

    private void setAdapter() {

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                ArrayList<Exercise> exerciseList = new ArrayList<>();

                for (DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()) {

                    // add every exercise in category to ArrayList
                    Exercise exercise = exerciseSnapshot.getValue(Exercise.class);
                    exerciseList.add(exercise);
                }

                String category = dataSnapshot.getKey();
                categoriesList.add(category);
                childItemsList.put(category, exerciseList);

                exerciseListAdapter = new CustomExpandableListAdapter(
                        ChooseExerciseActivity.this, categoriesList, childItemsList);
                exerciseListView.setAdapter(exerciseListAdapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // getting the data failed, log a message
                Log.w(TAG, "Something went wrong: ", databaseError.toException());
            }
        });
    }


    private void setClickListener() {
        
        exerciseListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent,
                                        View v, int groupPosition, int childPosition, long id) {

                Exercise exercise = (Exercise)
                        exerciseListAdapter.getChild(groupPosition, childPosition);
                Intent intent = new Intent();
                intent.putExtra("exercise", exercise);
                setResult(Activity.RESULT_OK, intent);
                finish();
                return true;
            }
        });
    }

    /**
     * Add LongClickListener to child items. Source used:
     * https://stackoverflow.com/questions/2353074/android-long-click-on-the-child-views-of-a-expandablelistview
     */
    private void setLongClickListener() {

        exerciseListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int childPosition = ExpandableListView.getPackedPositionChild(id);
                    Exercise exercise = (Exercise) exerciseListAdapter.getChild(groupPosition,childPosition);

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
