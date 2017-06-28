package nl.mprog.glimp.work_out.Activities.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nl.mprog.glimp.work_out.Activities.EditPlannerActivity;
import nl.mprog.glimp.work_out.Activities.WorkoutActivity;
import nl.mprog.glimp.work_out.Adapters.PlannerAdapter;
import nl.mprog.glimp.work_out.CheckNetwork;
import nl.mprog.glimp.work_out.R;
import nl.mprog.glimp.work_out.Workout;

/**
 * Created by Gido Limperg on 8-6-2017.
 */

public class PlannerFragment extends Fragment {

    private static final String TAG = "PlannerFragment";
    private static final String PREFS_NAME = "plannerPrefs";
    private static final int daysOfWeek = 7;

    private ArrayList<Workout> plannerArrayList;
    private ListView plannerListView;
    private PlannerAdapter plannerAdapter;
    private FloatingActionButton floatingActionButton;
    private boolean[] checkBoxState = new boolean[daysOfWeek];

    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.planner_fragment, container, false);

        plannerListView = (ListView) view.findViewById(R.id.plannerListView);
        plannerArrayList = new ArrayList<>(daysOfWeek);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.plannerActionButton);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userId).child("planner");

        loadSharedPreferences();

        setDefaultPlanner();
        setAdapter();
        setListener();
        setFloatingActionButton();

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences preferences = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();

        checkBoxState = plannerAdapter.getCheckBoxState();

        // save CheckBox states to SharedPreferences
        for (int i = 0; i < daysOfWeek; i++) {
            editor.putBoolean("checkBoxState" + i, checkBoxState[i]);
        }
        editor.apply();
    }

    public void loadSharedPreferences() {

        SharedPreferences preferences = getActivity().getSharedPreferences(PREFS_NAME, 0);

        // load CheckBox states from SharedPreferences
        for (int i = 0; i < daysOfWeek; i++) {
            checkBoxState[i] = preferences.getBoolean("checkBoxState" + i, false);
        }
    }

    public void setDefaultPlanner() {

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // check if planner exists
                if (!dataSnapshot.exists()) {

                    // add default values to planner
                    ArrayList<Workout> defaultPlanner = new ArrayList<>(daysOfWeek);
                    for (int i = 0; i < daysOfWeek; i++) {
                        defaultPlanner.add(new Workout("Rest day", null));
                    }

                    // save default planner to Firebase
                    mDatabase.setValue(defaultPlanner);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // getting the data failed, log a message
                Log.w(TAG, "Something went wrong: ", databaseError.toException());
            }
        });
    }

    public void setAdapter() {

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Workout workout = dataSnapshot.getValue(Workout.class);
                plannerArrayList.add(workout);

                plannerAdapter = new PlannerAdapter(getActivity(), plannerArrayList, checkBoxState);
                plannerListView.setAdapter(plannerAdapter);
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

        plannerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // get workout corresponding to clicked item
                Workout workout = plannerAdapter.getItem(position);
                if (workout.getExercises().size() > 0) {

                    Intent intent = new Intent(getActivity(), WorkoutActivity.class);
                    intent.putExtra("workout", workout);
                    startActivity(intent);
                }
            }
        });
    }

    public void setFloatingActionButton() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // convert ArrayList<Workout> to Workout[]
                Workout[] plannerWorkouts = new Workout[plannerArrayList.size()];
                plannerWorkouts = plannerArrayList.toArray(plannerWorkouts);

                Intent intent = new Intent(getActivity(), EditPlannerActivity.class);
                intent.putExtra("planner", plannerWorkouts);
                startActivity(intent);
            }
        });
    }

}
