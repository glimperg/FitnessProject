package nl.mprog.glimp.work_out.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nl.mprog.glimp.work_out.Exercise;
import nl.mprog.glimp.work_out.R;

/**
 * Created by Gido Limperg on 15-6-2017.
 * ArrayAdapter to be used in WorkoutActivity.
 */

public class ExerciseListAdapter extends ArrayAdapter<Exercise> {

    private Context context;
    private ArrayList<Exercise> exerciseList;

    public ExerciseListAdapter(Context context, ArrayList<Exercise> exerciseList) {
        super(context, 0, exerciseList);
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, parent, false);
        }

        // set exercise title to TextView
        Exercise exercise = exerciseList.get(position);
        TextView textView = (TextView) convertView.findViewById(R.id.categoryTextView);
        textView.setText(exercise.getName());

        return convertView;
    }
}
