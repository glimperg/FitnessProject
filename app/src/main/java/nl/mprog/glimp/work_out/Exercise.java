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
