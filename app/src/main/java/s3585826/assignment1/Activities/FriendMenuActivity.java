package s3585826.assignment1.Activities;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import s3585826.assignment1.R;

public class FriendMenuActivity extends Activity{

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(FriendMenuActivity.this, EditFriendActivity.class);
        startActivity(intent);
        return true;
    }
}