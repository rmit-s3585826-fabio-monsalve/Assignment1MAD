package s3585826.assignment1.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;

/**
 * Created by Callum on 29/09/2017.
 */

public class UserProfileActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_profile);

        TextView userInfoId = findViewById(R.id.userInfoId);
        TextView userInfoName = findViewById(R.id.userInfoName);
        TextView userInfoEmail = findViewById(R.id.userInfoEmail);
        TextView userInfoFriendCount = findViewById(R.id.userInfoFriendCount);

        userInfoId.setText(Model.getInstance().getUser().getId());
        userInfoName.setText(Model.getInstance().getUser().getName());
        userInfoEmail.setText(Model.getInstance().getUser().getEmail());
        userInfoFriendCount.setText(Integer.toString(Model.getInstance().getUser().getFriends().size()));

    }
}
