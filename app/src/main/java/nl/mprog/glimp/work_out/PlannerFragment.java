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
import android.widget.AdapterView;
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

public class PlannerFragment extends Fragment {

    private static final String TAG = "PlannerFragment";
    private static final int daysOfWeek = 7;

    private ArrayList<Workout> plannerArrayList = new ArrayList<>(daysOfWeek);
    private ListView plannerListView;
    private PlannerAdapter plannerAdapter;
    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.planner_fragment, container, false);

        plannerListView = (ListView) view.findViewById(R.id.plannerListView);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.plannerActionButton);

        setPlanner();
        setAdapter();
        setListener();
        setButtonListener();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        plannerArrayList = new ArrayList<>(daysOfWeek);
    }

    public void setPlanner() {

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final DatabaseReference mDatabase = FirebaseDatabase
                .getInstance().getReference().child("users");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (!dataSnapshot.child(userId).child("planner").hasChildren()) {

                    // add default values to planner
                    ArrayList<Workout> defaultPlanner = new ArrayList<>(daysOfWeek);
                    for (int i = 0; i < daysOfWeek; i++) {
                        defaultPlanner.add(new Workout("Rest day", null));
                    }
                    mDatabase.child(userId).child("planner").setValue(defaultPlanner);
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

    public void setAdapter() {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userId).child("planner");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Workout workout = dataSnapshot.getValue(Workout.class);
                plannerArrayList.add(workout);

                plannerAdapter = new PlannerAdapter(getActivity(), plannerArrayList);
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

                Workout workout = plannerAdapter.getItem(position);
                if (workout.getExercises().size() > 0) {

                    Intent intent = new Intent(getActivity(), WorkoutActivity.class);
                    intent.putExtra("workout", workout);
                    startActivity(intent);
                }
            }
        });
    }

    // TODO: hoe maak ik de eerste keer een planner (of hoeft dat alleen in EditPlannerActivity)
    // TODO: de checkboxes moeten iets doen
    // TODO: kijken wat er gebeurt als je een workout verwijdert die in de planner staat

    public void setButtonListener() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), EditPlannerActivity.class);

                Workout[] plannerWorkouts = new Workout[plannerArrayList.size()];
                plannerWorkouts = plannerArrayList.toArray(plannerWorkouts);
                intent.putExtra("planner", plannerWorkouts);
                startActivity(intent);
            }
        });
    }

}
