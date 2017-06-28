package nl.mprog.glimp.work_out.Activities.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import nl.mprog.glimp.work_out.Activities.ExerciseActivity;
import nl.mprog.glimp.work_out.Adapters.CustomExpandableListAdapter;
import nl.mprog.glimp.work_out.Exercise;
import nl.mprog.glimp.work_out.R;

/**
 * Created by Gido Limperg on 8-6-2017.
 */

public class DatabaseFragment extends Fragment {

    private static final String TAG = "DatabaseFragment";

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> categoriesList = new ArrayList<>();
    private HashMap<String, List<Exercise>> childItemsList = new HashMap<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.database_fragment, container, false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);

        setListAdapter();
        setChildClickListener();
        return view;
    }

    /**
     * @inheritDoc
     *
     * Empty ListView when Fragment is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        categoriesList = new ArrayList<>();
        childItemsList = new HashMap<>();
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
                expandableListAdapter = new CustomExpandableListAdapter(
                        getActivity(), categoriesList, childItemsList);
                expandableListView.setAdapter(expandableListAdapter);
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
     * Set listener to ListView, sending the user to ExerciseActivity upon child item click.
     */
    private void setChildClickListener() {

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent,
                                        View view, int groupPosition, int childPosition, long id) {

                // get Exercise corresponding to position
                Exercise exercise = (Exercise)
                        expandableListAdapter.getChild(groupPosition, childPosition);

                // go to ExerciseActivity
                Intent intent = new Intent(getActivity(), ExerciseActivity.class);
                intent.putExtra("exercise", exercise);
                startActivity(intent);
                return true;
            }
        });
    }
}
