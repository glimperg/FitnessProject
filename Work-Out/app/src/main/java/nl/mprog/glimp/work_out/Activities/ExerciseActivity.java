package nl.mprog.glimp.work_out.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import nl.mprog.glimp.work_out.Exercise;
import nl.mprog.glimp.work_out.ImageAsyncTask;
import nl.mprog.glimp.work_out.R;

public class ExerciseActivity extends AppCompatActivity {

    Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        exercise = (Exercise) getIntent().getSerializableExtra("exercise");
        setViews();
    }

    private void setViews() {

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView titleView = (TextView) findViewById(R.id.titleView);
        TextView descriptionView = (TextView) findViewById(R.id.descriptionView);
        TextView equipmentView = (TextView) findViewById(R.id.equipmentView);
        TextView musclesView = (TextView) findViewById(R.id.musclesView);

        // pass image URL to AsyncTask together with ImageView
        imageView.setTag(exercise.getImages().get(0));
        new ImageAsyncTask(imageView).execute();

        String description = "Description: " + exercise.getDescription();
        String equipment = "Equipment: " + exercise.getEquipment();
        String muscles = "Muscles: " + exercise.getMuscles();

        titleView.setText(exercise.getName());
        descriptionView.setText(description);
        equipmentView.setText(equipment);
        musclesView.setText(muscles);
    }


}
