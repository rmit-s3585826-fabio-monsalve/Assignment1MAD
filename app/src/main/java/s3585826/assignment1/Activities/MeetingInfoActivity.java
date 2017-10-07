package s3585826.assignment1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;

/**
 * Activity for displaying a friend's details
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class MeetingInfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String LOG_TAG = "Meeting Info Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(Model.getInstance().getFocusMeeting().getTitle());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(LOG_TAG, "OnCreate");
        setContentView(R.layout.activty_meeting_info);

        TextView meetingInfoId = (TextView) findViewById(R.id.meetingInfoId);
        TextView meetingInfoTitle = (TextView) findViewById(R.id.meetingInfoTitle);
        TextView meetingInfoDate = (TextView) findViewById(R.id.meetingInfoDate);
        TextView meetingInfoStartTime = (TextView)findViewById(R.id.meetingInfoStartTime);
        TextView meetingInfoEndTime = (TextView)findViewById(R.id.meetingInfoEndTime);
        TextView meetingInfoLocation = (TextView)findViewById(R.id.meetingInfoLocation);
        ListView meetingAttendeesList = (ListView)findViewById(R.id.meetingAttendeesList);


        final BaseAdapter meetingsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
            Model.getInstance().getFocusMeeting().getAttendeesAsStringArrayList()) {
        };

        meetingAttendeesList.setAdapter(meetingsAdapter);

        meetingInfoId.setText(Model.getInstance().getFocusMeeting().getId());
        meetingInfoTitle.setText(Model.getInstance().getFocusMeeting().getTitle());
        meetingInfoDate.setText(Model.getInstance().getFocusMeeting().getDate());
        meetingInfoStartTime.setText(Model.getInstance().getFocusMeeting().getStartTime());
        meetingInfoEndTime.setText(Model.getInstance().getFocusMeeting().getEndTime());
        meetingInfoLocation.setText(Model.getInstance().getFocusMeeting().getLocationString());


        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double latitude;
        double longitude;
        LatLng location;
        //display friends location with pin
        if (Model.getInstance().getFocusMeeting().getLocation()!=null) {
            latitude = Model.getInstance().getFocusMeeting().getLocation().getLatitude();
            longitude = Model.getInstance().getFocusMeeting().getLocation().getLongitude();
            location = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(location));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,12.0f));
        }else//display Melbourne
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-37.81,144.96),12.0f));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings: Intent intent = new Intent(MeetingInfoActivity.this, EditMeetingActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home: Intent intent2 = new Intent(MeetingInfoActivity.this, MainActivity.class);
                startActivity(intent2);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }
}