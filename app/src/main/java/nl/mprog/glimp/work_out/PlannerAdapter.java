package nl.mprog.glimp.work_out;

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

/**
 * Created by Gido Limperg on 19-6-2017.
 */

public class PlannerAdapter extends ArrayAdapter<Workout> {

    private static final String PREFS_NAME = "plannerPrefs";
    private Context context;
    private ArrayList<Workout> workoutList;
    private boolean[] checkBoxState;
    private int workoutCount;

    public PlannerAdapter(Context context, ArrayList<Workout> workoutList, boolean[] checkBoxState) {
        super(context, 0, workoutList);
        this.context = context;
        this.workoutList = workoutList;
        this.checkBoxState = checkBoxState;

        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, 0);
        this.workoutCount = preferences.getInt("workoutCount", 0);
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
        final String workoutName = workout.getName();
        TextView workoutTextView = (TextView) convertView.findViewById(R.id.plannerWorkoutTextView);
        workoutTextView.setText(workoutName);

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.plannerCheckBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                checkBoxState[position] = isChecked;
                if (!workoutName.equals("Rest day")) {
                    if (isChecked) {
                        workoutCount++;
                    } else {
                        workoutCount--;
                    }
                }
            }
        });
        checkBox.setChecked(checkBoxState[position]);

        return convertView;
    }

    public boolean[] getCheckBoxState() {
        return checkBoxState;
    }

    public int getWorkoutCount() {
        return workoutCount;
    }
}
