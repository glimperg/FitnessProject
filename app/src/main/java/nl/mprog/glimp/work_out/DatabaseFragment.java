package nl.mprog.glimp.work_out;

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

/**
 * Created by Gido Limperg on 8-6-2017.
 */

public class DatabaseFragment extends Fragment {

    private static final String TAG = "DatabaseFragment";

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> categoriesList = new ArrayList<>();
    private HashMap<String, List<Exercise>> childItemsList = new HashMap<>();
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("exercises");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.database_fragment, container, false);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        setAdapter();
        setListener();
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

    private void setListener() {
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Exercise exercise = (Exercise) expandableListAdapter.getChild(groupPosition, childPosition);
                Intent intent = new Intent(getActivity(), ExerciseActivity.class);
                intent.putExtra("exercise", exercise);
                startActivity(intent);
                return true;
            }
        });
    }
}
