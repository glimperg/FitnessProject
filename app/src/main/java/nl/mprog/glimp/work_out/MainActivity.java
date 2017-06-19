package nl.mprog.glimp.work_out;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Gido Limperg on 8-6-2017.
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private TabLayout tabLayout;

    // TODO: kijken of onStart belangrijk is
    // TODO: bug bij plannertab fixen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting.");

        // initialise Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        signInAnonymously();

        // set up ViewPager with SectionsPageAdapter
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        setViewPager(mViewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        setListener();

    }

    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // signed in successfully
                            String user_id = mAuth.getCurrentUser().toString();
                            Log.d(TAG, "signInAnonymously:success " + user_id);
                        } else {
                            // if sign in fails, display a message to the user
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setViewPager(ViewPager viewPager){

        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new DatabaseFragment(), "Exercises");
        adapter.addFragment(new WorkoutListFragment(), "Workouts");
        adapter.addFragment(new PlannerFragment(), "Planner");

        viewPager.setAdapter(adapter);
    }

    public void setListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // TODO: regelen dat de fab verdwijnt als je de workouts tab verlaat (en weer verschijnt)
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void newWorkout(View view) {

        Intent intent = new Intent(MainActivity.this, CreateWorkoutActivity.class);
        startActivity(intent);
    }

    public void editPlanner(View view) {

        Intent intent = new Intent(MainActivity.this, EditPlannerActivity.class);
        startActivity(intent);
    }
}
