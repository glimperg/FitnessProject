package nl.mprog.glimp.work_out;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Gido Limperg on 8-6-2017.
 */

public class PlannerFragment extends Fragment {

    private static final String TAG = "PlannerFragment";

    private ArrayList<Workout> plannerArrayList = new ArrayList<>();
    private ListView plannerListView;
    private PlannerAdapter plannerAdapter;
    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.planner_fragment, container, false);

        plannerListView = (ListView) view.findViewById(R.id.plannerListView);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.plannerActionButton);

        setAdapter();
        setListener();
        setButtonListener();

        return view;

    }

    public void setAdapter() {

        plannerAdapter = new PlannerAdapter(getActivity(), plannerArrayList);
        plannerListView.setAdapter(plannerAdapter);

    }

    public void setListener() {

        plannerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Workout workout = plannerAdapter.getItem(position);
                if (workout.getExercises() == null) {

                    Intent intent = new Intent(getActivity(), WorkoutActivity.class);
                    intent.putExtra("workout", workout);
                    startActivity(intent);
                }
            }
        });
    }

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
