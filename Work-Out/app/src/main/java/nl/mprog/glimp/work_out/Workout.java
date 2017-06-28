package nl.mprog.glimp.work_out;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Gido Limperg on 8-6-2017.
 * Workout class containing a name and a list of Exercise objects.
 */

public class Workout implements Serializable {

    private String name;
    private ArrayList<Exercise> exercises = new ArrayList<>();

    // constructor for Firebase
    public Workout() {}

    public Workout(String name, ArrayList<Exercise> exercises) {
        this.name = name;
        this.exercises = exercises;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public String getName() {
        return name;
    }
}
