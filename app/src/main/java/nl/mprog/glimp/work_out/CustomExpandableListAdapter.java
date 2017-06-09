package nl.mprog.glimp.work_out;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by glimp on 9-6-2017.
 */

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> categoriesList;
    private HashMap<String, List<Exercise>> childItemsList;

    public CustomExpandableListAdapter(Context context, List<String> categoriesList,
                                       HashMap<String, List<Exercise>> childItemsList) {
        this.context = context;
        this.categoriesList = categoriesList;
        this.childItemsList = childItemsList;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, parent, false);
        }

        // TODO: misschien werkt dit niet, moet nog testen
        String category = (String) getGroup(groupPosition);
        TextView categoryTextView = (TextView) convertView.findViewById(R.id.categoryTextView);
        categoryTextView.setText(category);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        Exercise exercise = (Exercise) getChild(groupPosition, childPosition);
        String exerciseName = exercise.getName();
        TextView exerciseTextView = (TextView) convertView.findViewById(R.id.exerciseTextView);
        exerciseTextView.setText(exerciseName);
        return convertView;
    }

    @Override
    public int getGroupCount() {
        return categoriesList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // category corresponding to child
        String category = categoriesList.get(groupPosition);
        return childItemsList.get(category).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categoriesList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // category corresponding to child
        String category = categoriesList.get(groupPosition);
        return childItemsList.get(category).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
