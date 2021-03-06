package nl.mprog.glimp.work_out.Activities.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import nl.mprog.glimp.work_out.Activities.CreateWorkoutActivity;
import nl.mprog.glimp.work_out.Activities.WorkoutActivity;
import nl.mprog.glimp.work_out.Adapters.WorkoutListAdapter;
import nl.mprog.glimp.work_out.R;
import nl.mprog.glimp.work_out.Workout;

/**
 * Created by Gido Limperg on 8-6-2017.
 * Fragment of MainActivity, containing a list of created Workouts.
 */

public class WorkoutListFragment extends Fragment {

    private static final String TAG = "WorkoutListFragment";

    private ListView listView;
    private WorkoutListAdapter listAdapter;
    private ArrayList<Workout> workoutList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private FloatingActionButton floatingActionButton;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.workout_list_fragment, container, false);
        activity = getActivity();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // get reference to Firebase database
            mDatabase = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(user.getUid()).child("workouts");
        }

        listView = (ListView) view.findViewById(R.id.workoutListView);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.workoutActionButton);

        setListAdapter();
        setListViewListener();
        setFloatingActionButton();

        return view;
    }

    /**
     * Adds all Workouts to workoutList and sets ListAdapter to the ListView.
     */
    public void setListAdapter() {

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                // get Workout from Firebase and add to list
                Workout workout = dataSnapshot.getValue(Workout.class);
                workoutList.add(workout);

                listAdapter = new WorkoutListAdapter(activity, workoutList);
                listView.setAdapter(listAdapter);
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
     * Sets OnClickListener to ListView, moving to WorkoutActivity upon click.
     */
    public void setListViewListener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // get workout corresponding to clicked item
                Workout workout = listAdapter.getItem(position);

                // go to WorkoutActivity
                Intent intent = new Intent(getActivity(), WorkoutActivity.class);
                intent.putExtra("workout", workout);
                startActivity(intent);
            }
        });
    }

    /**
     * Sets OnClickListener to FloatingActionButton, moving to CreateWorkoutActivity upon click.
     */
    public void setFloatingActionButton() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), CreateWorkoutActivity.class);
                startActivity(intent);
            }
        });
    }
}
