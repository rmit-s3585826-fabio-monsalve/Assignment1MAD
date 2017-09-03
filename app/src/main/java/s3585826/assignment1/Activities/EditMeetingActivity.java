package s3585826.assignment1.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import s3585826.assignment1.Model.*;
import s3585826.assignment1.R;

/**
 * Activity for editing a meeting's details
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class EditMeetingActivity extends AppCompatActivity{
    private static final String LOG_TAG = "Edit Meeting Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meeting_details);
        Log.d(LOG_TAG, Model.getInstance().getFocusMeeting().getTitle());

        // Reference all relevant elements from the view
        final TextView meetingTextId = (TextView) findViewById(R.id.meetingTextId);
        final EditText editTextTitle = (EditText) findViewById(R.id.editTextTitle);

        // Set the current friend details
        meetingTextId.setText(Model.getInstance().getFocusMeeting().getId());
        editTextTitle.setText(Model.getInstance().getFocusMeeting().getTitle());

        Button editButton = (Button) findViewById(R.id.editMeetingButton);

        editButton.setOnClickListener(view -> {

            //Set new details from user input
            Model.getInstance().getFocusMeeting().setTitle(editTextTitle.getText().toString());

            // Back to Meeting Info
            Intent intent = new Intent(EditMeetingActivity.this, MeetingInfoActivity.class);
            startActivity(intent);
        });
    }
}
