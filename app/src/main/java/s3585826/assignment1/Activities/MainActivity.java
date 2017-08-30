package s3585826.assignment1.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import s3585826.assignment1.Adapters.SectionsPageAdapter;
import s3585826.assignment1.Fragments.FriendsFragment;
import s3585826.assignment1.Fragments.MeetingsFragment;
import s3585826.assignment1.Fragments.MyDetailsFragment;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;
import s3585826.assignment1.Support_Code.LocationListener;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Main Activity";
    private static LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load dummy data from /assets.dummy_data.txt on first entry to MainActivity
        if (Model.getInstance().firstTimeMain) {
            Model.getInstance().loadDummyData(this);
            Model.getInstance().firstTimeMain=false;
        }

        //create and run a location listener thread
        locationListener = new LocationListener(this);
        AsyncTask.execute(locationListener);

        //setup main toolbar and tabs with view pager
        setContentView(R.layout.activity_main);
        ViewPager mainViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mainViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mainViewPager);

    }


    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        sectionsPageAdapter.addFragment(new FriendsFragment(), "Friends");
        sectionsPageAdapter.addFragment(new MeetingsFragment(), "Meetings");
        sectionsPageAdapter.addFragment(new MyDetailsFragment(), "My Details");
        viewPager.setAdapter(sectionsPageAdapter);
    }


}