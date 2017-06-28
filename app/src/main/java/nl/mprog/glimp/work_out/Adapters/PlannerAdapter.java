package nl.mprog.glimp.work_out.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import nl.mprog.glimp.work_out.R;
import nl.mprog.glimp.work_out.Workout;

/**
 * Created by Gido Limperg on 19-6-2017.
 * ArrayAdapter to be used in PlannerFragment.
 */

public class PlannerAdapter extends ArrayAdapter<Workout> {

    private Context context;
    private ArrayList<Workout> workoutList;
    private boolean[] checkBoxState;

    public PlannerAdapter(Context context, ArrayList<Workout> workoutList, boolean[] checkBoxState) {
        super(context, 0, workoutList);
        this.context = context;
        this.workoutList = workoutList;
        this.checkBoxState = checkBoxState;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.planner_item, parent, false);
        } else {
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.plannerCheckBox);
            checkBox.setOnCheckedChangeListener(null);
        }

        // set correct day to TextView
        String[] dayArray = context.getResources().getStringArray(R.array.days_array);
        TextView dayTextView = (TextView) convertView.findViewById(R.id.dayTextView);
        dayTextView.setText(dayArray[position]);

        // set workout title to TextView
        Workout workout = workoutList.get(position);
        String workoutName = workout.getName();
        TextView workoutTextView = (TextView) convertView.findViewById(R.id.plannerWorkoutTextView);
        workoutTextView.setText(workoutName);

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.plannerCheckBox);

        // save CheckBox state upon change
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                checkBoxState[position] = isChecked;
            }
        });
        checkBox.setChecked(checkBoxState[position]);

        return convertView;
    }

    public boolean[] getCheckBoxState() {
        return checkBoxState;
    }
}
