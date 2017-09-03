package s3585826.assignment1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
public class FriendInfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String LOG_TAG = "Friend Info Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(LOG_TAG, "OnCreate");
        setContentView(R.layout.activity_friend_info);

        // Reference all relevant elements from the view
        TextView friendInfoName = (TextView) findViewById(R.id.friendInfoName);
        TextView friendInfoEmail = (TextView)findViewById(R.id.friendInfoEmail);
        TextView friendInfoBirthday = (TextView)findViewById(R.id.friendInfoBirthday);

        // Set the current friend details
        friendInfoName.setText(Model.getInstance().getFocusFriend().getName());
        friendInfoEmail.setText(Model.getInstance().getFocusFriend().getEmail());
        friendInfoBirthday.setText(Model.getInstance().getFocusFriend().getBirthday());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double latitude;
        double longitude;
        LatLng location;

        //display friends location with pin
        if (Model.getInstance().getFocusFriend().getLocation()!=null) {
            latitude = Model.getInstance().getFocusFriend().getLocation().getLatitude();
            longitude = Model.getInstance().getFocusFriend().getLocation().getLongitude();
            location = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(location)).setTitle(location.toString());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,12.0f));

        }else//display Melbourne
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-37.81,144.96),12.0f));
    }

    /**
     * Method for changing navigating to MainActivity or EditFriend Activity depending for options in toolbar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings: Intent intent = new Intent(FriendInfoActivity.this, EditFriendActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home: Intent intent2 = new Intent(FriendInfoActivity.this, MainActivity.class);
                startActivity(intent2);
                break;
        }
        return true;
    }

    // Inflate toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }
}