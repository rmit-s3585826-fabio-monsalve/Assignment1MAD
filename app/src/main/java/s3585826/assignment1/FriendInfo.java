package s3585826.assignment1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class FriendInfo extends AppCompatActivity {
    private String friendName;
    private static final String LOG_TAG = "Friend Info Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG, "OnCreate");
        setContentView(R.layout.activity_friend_info);

        Bundle extras = getIntent().getExtras();
       if(extras !=null) {
            friendName = extras.getString("friend");
        }

        Log.d(LOG_TAG, friendName);

        Friend f = null;
        for(Friend e: MainActivity.user1.getFriendMap().values()){
            if(friendName.equals(e.getName())){
                f = e;
            }else{
                Log.d(LOG_TAG, friendName);
            }
        }

        TextView friendInfoId = (TextView) findViewById(R.id.friendInfoId);
        TextView friendInfoName = (TextView) findViewById(R.id.friendInfoName);
        TextView friendInfoEmail = (TextView)findViewById(R.id.friendInfoEmail);
        TextView friendInfoBirthday = (TextView)findViewById(R.id.friendInfoBirthday);

        friendInfoId.setText(f.getId());
        friendInfoName.setText(f.getName());
        friendInfoEmail.setText(f.getEmail());
        friendInfoBirthday.setText(f.getBirthday());
    }
}