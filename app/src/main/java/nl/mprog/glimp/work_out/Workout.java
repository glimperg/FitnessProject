package nl.mprog.glimp.work_out;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Gido Limperg on 8-6-2017.
 */

public class Workout {

    private String name;
    private ArrayList<Exercise> exercises = new ArrayList<>();

    // constructor for Firebase
    public Workout() {}

    public Workout(String name, ArrayList<JSONObject> exerciseArrayList) {
        this.name = name;

        for (JSONObject jsonObject : exerciseArrayList) {
            exercises.add(new Exercise(jsonObject));
        }
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public String getName() {
        return name;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
    }
}
