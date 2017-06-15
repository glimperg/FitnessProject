package nl.mprog.glimp.work_out;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        setAdapter();
        setListener();
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


    private void setListener() {

        // TODO: evt. longclicklistener om de exercise te kunnen bekijken

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
}
