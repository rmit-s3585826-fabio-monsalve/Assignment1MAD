package s3585826.assignment1.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;

import s3585826.assignment1.Adapters.SectionsPageAdapter;
import s3585826.assignment1.Database.DatabaseHandler;
import s3585826.assignment1.Fragments.FriendsFragment;
import s3585826.assignment1.Fragments.MeetingsFragment;
import s3585826.assignment1.Fragments.MapsFragment;
import s3585826.assignment1.Model.Meeting;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;
import s3585826.assignment1.Support_Code.LocationListener;

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
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart() MainActivity");

        ArrayList<Meeting> meetings = Model.getInstance().getUser().sortMeetingsByTimeAscending();
        Meeting meeting = meetings.get(0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar cal = Calendar.getInstance();
        cal.setTime(meeting.getFormattedDate());
        Long time = cal.getTimeInMillis()- Model.getInstance().getUser().getReminderPeriodAsMilliseconds();

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, broadcast);
        Log.d(LOG_TAG, "onStart() MainActivity END" + time);
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
        }

        // Create and run a location listener thread
        locationListener = new LocationListener(this);
        AsyncTask.execute(locationListener);

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