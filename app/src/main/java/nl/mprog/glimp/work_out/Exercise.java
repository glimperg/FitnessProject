package nl.mprog.glimp.work_out;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Gido Limperg on 8-6-2017.
 */

public class Exercise {

    private String name;
    private String description;
    private String category;
    private ArrayList<String> equipment;
    private ArrayList<String> muscles;
    private ArrayList<String> images;
    private int sets;
    private int reps;

    // constructor for Firebase
    public Exercise() {}

    public Exercise(JSONObject exercise) {

        String name, description, category;
        name = description = category = "";
        int sets, reps;
        sets = reps = 0;
        ArrayList<String> equipment, muscles, images;
        equipment = muscles = images = new ArrayList<String>();

        try {
            name = exercise.getString("name");
            description = exercise.getString("description");
            category = exercise.getString("category");

            if (exercise.has("equipment")) {
                JSONArray array = exercise.getJSONArray("equipment");
                for (int i = 0; i < array.length(); i++) {
                    equipment.add(array.getString(i));
                    // TODO: weet niet of dit werkt
                }
            }
            if (exercise.has("muscles")) {
                JSONArray array = exercise.getJSONArray("muscles");
                for (int i = 0; i < array.length(); i++) {
                    muscles.add(array.getString(i));
                }
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

    public ArrayList<String> getMuscles() {

        return muscles;
    }

    public ArrayList<String> getEquipment() {

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
