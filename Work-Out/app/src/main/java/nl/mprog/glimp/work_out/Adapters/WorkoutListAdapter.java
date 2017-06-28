package nl.mprog.glimp.work_out.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nl.mprog.glimp.work_out.R;
import nl.mprog.glimp.work_out.Workout;

/**
 * Created by Gido Limperg on 13-6-2017.
 * ArrayAdapter to be used in WorkoutListFragment
 */

public class WorkoutListAdapter extends ArrayAdapter<Workout> {

    private Context context;
    private ArrayList<Workout> workoutList;

    public WorkoutListAdapter(Context context, ArrayList<Workout> workoutList) {
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
            convertView = inflater.inflate(R.layout.list_group, parent, false);
        }

        // set workout title to TextView
        Workout workout = workoutList.get(position);
        TextView textView = (TextView) convertView.findViewById(R.id.categoryTextView);
        textView.setText(workout.getName());

        return convertView;
    }
}
