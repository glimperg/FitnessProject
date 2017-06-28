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

import nl.mprog.glimp.work_out.Adapters.SectionsPageAdapter;
import nl.mprog.glimp.work_out.CheckNetwork;
import nl.mprog.glimp.work_out.R;

/**
 * Created by Gido Limperg on 8-6-2017.
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;

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
            ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
            setViewPager(mViewPager);

            // set up tabs with ViewPager
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
        } else {
            CheckNetwork.displayAlertDialog(MainActivity.this);
        }
    }

    /**
     * Signs in device anonymously using Firebase authentication.
     */
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

    /**
     * Sets adapter containing Fragments to the ViewPager.
     */
    private void setViewPager(ViewPager viewPager){

        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new DatabaseFragment(), "Exercises");
        adapter.addFragment(new WorkoutListFragment(), "Workouts");
        adapter.addFragment(new PlannerFragment(), "Planner");

        viewPager.setAdapter(adapter);
    }
}
