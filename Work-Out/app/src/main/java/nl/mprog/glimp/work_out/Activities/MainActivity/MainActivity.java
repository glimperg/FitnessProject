package nl.mprog.glimp.work_out.Activities.MainActivity;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import nl.mprog.glimp.work_out.Adapters.SectionsPageAdapter;
import nl.mprog.glimp.work_out.CheckNetwork;
import nl.mprog.glimp.work_out.R;

/**
 * Created by Gido Limperg on 8-6-2017.
 * MainActivity containing an Exercises, Workouts, and Planner tab.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager mViewPager;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check internet connection
        if (CheckNetwork.isInternetAvailable(MainActivity.this)) {

            // initialise Firebase authentication
            mAuth = FirebaseAuth.getInstance();
            signInAnonymously();

            // set up ViewPager with SectionsPageAdapter
            mViewPager = (ViewPager) findViewById(R.id.container);
            setViewPager();

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);

        } else {
            CheckNetwork.displayAlertDialog(MainActivity.this);
        }
    }

    /**
     * Signs in user anonymously using Firebase authentication.
     */
    private void signInAnonymously() {

        mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInAnonymously:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Sets adapter containing Fragments to the ViewPager.
     */
    private void setViewPager(){

        // initialise SectionsPageAdapter and add Fragments
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new DatabaseFragment(), "Exercises");
        adapter.addFragment(new WorkoutListFragment(), "Workouts");
        adapter.addFragment(new PlannerFragment(), "Planner");

        mViewPager.setAdapter(adapter);
    }
}
