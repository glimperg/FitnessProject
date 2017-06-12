package nl.mprog.glimp.work_out;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {

    ImageView imageView;
    Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        exercise = (Exercise) getIntent().getSerializableExtra("exercise");
        setTextViews();
    }

    private void setTextViews() {

        imageView = (ImageView) findViewById(R.id.imageView);

        // pass image URL to AsyncTask together with ImageView
        imageView.setTag(exercise.getImages().get(0));
        new ImageAsyncTask(imageView).execute();

    }


}
