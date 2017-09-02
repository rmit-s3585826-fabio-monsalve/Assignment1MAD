package s3585826.assignment1.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import s3585826.assignment1.Model.*;
import s3585826.assignment1.R;


public class EditMeetingActivity extends AppCompatActivity{
    private static final String LOG_TAG = "Edit Meeting Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meeting_details);
        Log.d(LOG_TAG, Model.getInstance().getFocusMeeting().getTitle());

        final TextView meetingTextId = (TextView) findViewById(R.id.meetingTextId);
        final EditText editTextTitle = (EditText) findViewById(R.id.editTextTitle);

        meetingTextId.setText(Model.getInstance().getFocusMeeting().getId());
        editTextTitle.setText(Model.getInstance().getFocusMeeting().getTitle());

        Button editButton = (Button) findViewById(R.id.editMeetingButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.getInstance().getFocusMeeting().setTitle(editTextTitle.getText().toString());

                Intent intent = new Intent(EditMeetingActivity.this, MeetingInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
