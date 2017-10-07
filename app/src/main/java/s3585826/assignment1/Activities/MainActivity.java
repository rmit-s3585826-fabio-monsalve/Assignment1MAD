package s3585826.assignment1.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import s3585826.assignment1.Adapters.SectionsPageAdapter;
import s3585826.assignment1.Database.DatabaseHandler;
import s3585826.assignment1.Fragments.FriendsFragment;
import s3585826.assignment1.Fragments.MapsFragment;
import s3585826.assignment1.Fragments.MeetingsFragment;
import s3585826.assignment1.Model.Location;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;
import s3585826.assignment1.Support_Code.LocationListener;
import s3585826.assignment1.Support_Code.WalkTimeCalculator;

/**
 * Main Activity
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Main Activity";
    private static LocationListener locationListener;


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load dummy data from /assets.dummy_data.txt on first entry to MainActivity
        if (Model.getInstance().firstTimeMain) {
            Model.getInstance().loadDummyData(this);
            DatabaseHandler databaseHandler = new DatabaseHandler(this, null, null, 1);
            databaseHandler.loadFriendsFromDb();
            databaseHandler.loadMeetingsFromDb();
            Model.getInstance().firstTimeMain=false;
            Model.getInstance().getUser().setLocation(new Location(-37.80,144.92));
        }

        // Create and run a location listener thread
        locationListener = new LocationListener(this);
        AsyncTask.execute(locationListener);

        //test the json thread
        Location location1 = new Location(-37.8107,144.9544);
        Location location2 = new Location(-37.8054,144.9714);
        WalkTimeCalculator walkTimeCalculator = new WalkTimeCalculator(location1,location2);
        walkTimeCalculator.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        setContentView(R.layout.activity_main);

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup tabs with view pager
        ViewPager mainViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mainViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mainViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.user_profile:
                startActivity(new Intent(MainActivity.this,UserProfileActivity.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Setup fragments
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        sectionsPageAdapter.addFragment(new FriendsFragment(), "Friends");
        sectionsPageAdapter.addFragment(new MeetingsFragment(), "Meetings");
        sectionsPageAdapter.addFragment(new MapsFragment(), "Maps");
        viewPager.setAdapter(sectionsPageAdapter);
    }

}