package s3585826.assignment1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;

public class FriendInfoActivity extends AppCompatActivity {
    private static final String LOG_TAG = "Friend Info Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(Model.getInstance().getFocusFriend().getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(LOG_TAG, "OnCreate");
        setContentView(R.layout.activity_friend_info);

        TextView friendInfoId = (TextView) findViewById(R.id.friendInfoId);
        TextView friendInfoName = (TextView) findViewById(R.id.friendInfoName);
        TextView friendInfoEmail = (TextView)findViewById(R.id.friendInfoEmail);
        TextView friendInfoBirthday = (TextView)findViewById(R.id.friendInfoBirthday);
        TextView friendInfoLocation = (TextView)findViewById(R.id.friendInfoLocation);

        friendInfoId.setText(Model.getInstance().getFocusFriend().getId());
        friendInfoName.setText(Model.getInstance().getFocusFriend().getName());
        friendInfoEmail.setText(Model.getInstance().getFocusFriend().getEmail());
        friendInfoBirthday.setText(Model.getInstance().getFocusFriend().getBirthday());
        friendInfoLocation.setText(Model.getInstance().getFocusFriend().getLocationString());
    }

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }
}