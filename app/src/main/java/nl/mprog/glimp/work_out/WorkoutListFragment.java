package nl.mprog.glimp.work_out;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Gido Limperg on 8-6-2017.
 */

public class WorkoutListFragment extends Fragment {
    private static final String TAG = "WorkoutListFragment";

    private ListView listView;
    private WorkoutListAdapter listAdapter;
    private ArrayList<Workout> workoutList = new ArrayList<>();
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workout_list_fragment, container, false);

        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // get reference to Firebase database containing Workouts
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user_id);

        listView = (ListView) view.findViewById(R.id.workoutListView);
        setAdapter();

        return view;
    }

    public void setAdapter() {

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {

                    Workout workout = workoutSnapshot.getValue(Workout.class);
                    workoutList.add(workout);
                }

                listAdapter = new WorkoutListAdapter(getActivity(), workoutList);
                listView.setAdapter(listAdapter);
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

}
