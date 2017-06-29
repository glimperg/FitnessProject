package nl.mprog.glimp.work_out;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Gido Limperg on 8-6-2017.
 * Exercise class containing information about an exercise.
 */

public class Exercise implements Serializable {

    private String name;
    private String description;
    private String category;
    private String equipment;
    private String muscles;
    private ArrayList<String> images;

    // constructor for Firebase
    public Exercise() {
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

    public String getDescription() {

        return description;
    }

    public String getName() {

        return name;
    }
}