package s3585826.assignment1.Fragments;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import s3585826.assignment1.Activities.MainActivity;
import s3585826.assignment1.Database.DatabaseHandler;
import s3585826.assignment1.Model.Location;
import s3585826.assignment1.Model.Meeting;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;

/**
 * Fragment class for meetings tab
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class NewMeetingFragment extends android.support.v4.app.Fragment {
    public final static Meeting meeting = new Meeting();
    private static final String LOG_TAG = "newMeetingFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.add_meeting, container, false);

        // Display a list of checkboxes
        Button friendsButton = view.findViewById(R.id.chooseFriendsButton);
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChooseFriendDialog().show(NewMeetingFragment.this.getFragmentManager(), "mc");
            }
        });

        // Display date picker
        Button dateButton = view.findViewById(R.id.chooseDateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChooseDateDialog().show(NewMeetingFragment.this.getFragmentManager(), "mc");
            }
        });

        // Display startTime button
        Button startTimeButton = view.findViewById(R.id.chooseStartTimeButton);
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Bundle number to dictate the time picker dialog function
                Bundle bundle = new Bundle();
                bundle.putInt("DATE", 1);

                ChooseTimeDialog startTimeDialog = new ChooseTimeDialog();
                startTimeDialog.setArguments(bundle);

                // Show dialog
                startTimeDialog.show(NewMeetingFragment.this.getFragmentManager(), "mc");
            }
        });

        Button endTimeButton = view.findViewById(R.id.chooseEndTimeButton);
        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("DATE", 2);

                ChooseTimeDialog endTimeDialog = new ChooseTimeDialog();
                endTimeDialog.setArguments(bundle);
                endTimeDialog.show(NewMeetingFragment.this.getFragmentManager(), "mc");
            }
        });

        Button doneButton = view.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = view.findViewById(R.id.editTextId);
                Model.getInstance().incrementMeetingId();
                Model.getInstance();

                Location location = new Location(-37.820488, 144.973784);
                meeting.setLocation(location);

                // Create new meeting with new user input
                Meeting newMeeting = new Meeting(Integer.toString(Model.getMeetingId()), et.getText().toString(),
                    meeting.getStartTime(), meeting.getEndTime(), meeting.getDate(), meeting.getInvitedFriends(),
                    location);

                Log.d(LOG_TAG, newMeeting.getTitle());

                // add meeting and increment meetingID

                Model.getInstance().getUser().addMeeting(newMeeting);

                DatabaseHandler db  = new DatabaseHandler(getContext(), null, null, 1);
                db.addMeeting(newMeeting);

                Model.getInstance().incrementMeetingId();

                Intent intent = new Intent(NewMeetingFragment.this.getActivity(), MainActivity.class);
                NewMeetingFragment.this.startActivity(intent);
            }
        });

        return view;
    }
}