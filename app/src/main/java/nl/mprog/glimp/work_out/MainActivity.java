package nl.mprog.glimp.work_out;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //private SectionsPageAdapter mSectionsPageAdapter;
    //private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting.");

        //mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // set up ViewPager with SectionsPageAdapter
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        setViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void setViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new DatabaseFragment(), "Exercises");
        adapter.addFragment(new WorkoutListFragment(), "Workouts");
        adapter.addFragment(new PlannerFragment(), "Planner");

        viewPager.setAdapter(adapter);
    }
}
