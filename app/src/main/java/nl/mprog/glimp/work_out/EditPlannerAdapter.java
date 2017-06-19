package nl.mprog.glimp.work_out;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gido Limperg on 19-6-2017.
 */

public class EditPlannerAdapter extends ArrayAdapter<Workout> {

    private Context context;
    private ArrayList<Workout> workoutList;

    public EditPlannerAdapter(Context context, ArrayList<Workout> workoutList) {
        super(context, 0, workoutList);
        this.context = context;
        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.edit_planner_item, parent, false);
        }

        // set correct day to TextView
        String[] dayArray = context.getResources().getStringArray(R.array.days_array);
        TextView dayTextView = (TextView) convertView.findViewById(R.id.editPlannerDayTextView);
        dayTextView.setText(dayArray[position]);

        // set workout title to TextView
        Workout workout = workoutList.get(position);
        TextView workoutTextView = (TextView) convertView.findViewById(R.id.editPlannerWorkoutTextView);
        workoutTextView.setText(workout.getName());

        return convertView;
    }
}
