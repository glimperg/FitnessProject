package nl.mprog.glimp.work_out;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Gido Limperg on 8-6-2017.
 */

public class Exercise implements Serializable{

    private String name;
    private String description;
    private String category;
    private String equipment;
    private String muscles;
    private ArrayList<String> images;
    private int sets;
    private int reps;

    // constructor for Firebase
    public Exercise() {}

    public Exercise(JSONObject exercise) {

        String name, description, category, equipment, muscles;
        name = description = category = equipment = muscles = "None";
        int sets, reps;
        sets = reps = 0;
        ArrayList<String> images = new ArrayList<>();

        try {
            name = exercise.getString("name");
            category = exercise.getString("category");

            if (exercise.has("description")) {
                description = exercise.getString("description");
            }
            if (exercise.has("equipment")) {
                equipment = exercise.getString("equipment");
            }
            if (exercise.has("muscles")) {
                muscles = exercise.getString("muscles");
            }

            if (exercise.has("images")) {
                JSONArray array = exercise.getJSONArray("images");
                for (int i = 0; i < array.length(); i++) {
                    images.add(array.getString(i));
                }
            }

            if (exercise.has("sets") && exercise.has("reps")) {
                sets = exercise.getInt("sets");
                reps = exercise.getInt("reps");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.name = name;
        this.description = description;
        this.category = category;
        this.equipment = equipment;
        this.muscles = muscles;
        this.images = images;
        this.sets = sets;
        this.reps = reps;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public String getMuscles() {

        return muscles;
    }

    public String getEquipment() {

        return equipment;
    }

    public String getCategory() {

        return category;
    }

    public String getDescription() {

        return description;
    }

    public String getName() {

        return name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
