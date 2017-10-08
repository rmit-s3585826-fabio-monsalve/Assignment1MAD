package s3585826.assignment1.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import s3585826.assignment1.Adapters.SectionsPageAdapter;
import s3585826.assignment1.Database.DatabaseHandler;
import s3585826.assignment1.Fragments.FriendsFragment;
import s3585826.assignment1.Fragments.MapsFragment;
import s3585826.assignment1.Fragments.MeetingsFragment;
import s3585826.assignment1.Model.Location;
import s3585826.assignment1.Model.Meeting;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;
import s3585826.assignment1.Support_Code.LocationListener;
import s3585826.assignment1.Support_Code.NetworkChangeReceiver;

/**
 * Main Activity
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Main Activity";
    private static LocationListener locationListener;
    DatabaseHandler databaseHandler = new DatabaseHandler(this, null, null, 1);
    private AlarmManager alarmManager;
    private PendingIntent broadcast;


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart() MainActivity");

        ArrayList<Meeting> meetings = Model.getInstance().getUser().sortMeetingsByTimeAscending();
        if(meetings.size()  ==  0){
            Toast.makeText(this, "You have no meetings", Toast.LENGTH_SHORT).show();

        }else {
            Meeting meeting = meetings.get(0);
            Model.getInstance().setFocusMeeting(meeting);
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
            notificationIntent.addCategory("android.intent.category.DEFAULT");

            broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Calendar cal = Calendar.getInstance();

            if(Model.getInstance().getUser().getReminderPeriodAfterNotification() == 0){
                cal.setTime(meeting.getFormattedDate());
                Long time = cal.getTimeInMillis() - Model.getInstance().getUser().getReminderPeriodAsMilliseconds();
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, broadcast);
            }else {
                cal.add(Calendar.MINUTE, Model.getInstance().getUser().getReminderPeriodAfterNotification());
                Log.d(LOG_TAG, "MainActivity()" + Model.getInstance().getUser().getReminderPeriodAfterNotification());
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
            }
            processIntentAction(getIntent());
        }
    }

    private void processIntentAction(Intent intent) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case "DISMISS":
                    Toast.makeText(this, "Notification dismissed", Toast.LENGTH_SHORT).show();
                    alarmManager.cancel(broadcast);
                    break;
                case "CANCEL":
                    Toast.makeText(this, "Meeting cancelled", Toast.LENGTH_SHORT).show();
                    Model.getInstance().getUser().getMeetings().remove(Model.getInstance().getFocusMeeting().getId());
                    databaseHandler.deleteMeeting(Model.getInstance().getFocusMeeting().getId());
                    break;
                case "REMIND":
                    Toast.makeText(this, "REMIND", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,MeetingReminderActivity.class));
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load dummy data from /assets.dummy_data.txt on first entry to MainActivity
        if (Model.getInstance().firstTimeMain) {
            Model.getInstance().loadDummyData(this);
            databaseHandler.loadFriendsFromDb();
            databaseHandler.loadMeetingsFromDb();
            Model.getInstance().firstTimeMain=false;
            Model.getInstance().getUser().setLocation(new Location(-37.80,144.92));
        }

        // Create and run a location listener thread
        locationListener = new LocationListener(this);
        AsyncTask.execute(locationListener);

        // Determine initial connectivity state and register network broadcast receiver to update state changes
        Context context = getApplicationContext();
        ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            Model.getInstance().setConnected(true);
        }else{
            Model.getInstance().setConnected(false);
        }
        NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

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