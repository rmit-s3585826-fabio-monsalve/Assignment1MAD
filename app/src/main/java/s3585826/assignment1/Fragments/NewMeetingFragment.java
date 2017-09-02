package s3585826.assignment1.Fragments;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import s3585826.assignment1.Activities.MainActivity;
import s3585826.assignment1.Model.Location;
import s3585826.assignment1.Model.Meeting;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;

/**
 * Created by Callum on 8/08/2017.
 */

public class NewMeetingFragment extends android.support.v4.app.Fragment {
    int id = 0;
    public final static Meeting meeting = new Meeting();
    private static final String LOG_TAG = "newMeetingFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_meeting, container, false);


        /* Display a list of checkboxes */
        Button friendsButton = view.findViewById(R.id.chooseFriendsButton);
        friendsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                new ChooseFriendDialog().show(getFragmentManager(), "mc");
            }
        });
          /* Display date picker */
        Button dateButton = view.findViewById(R.id.chooseDateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                new ChooseDateDialog().show(getFragmentManager(), "mc");
            }
        });

        Button startTimeButton = view.findViewById(R.id.chooseStartTimeButton);
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Bundle bundle = new Bundle();
                bundle.putInt("DATE",1);

                ChooseTimeDialog startTimeDialog = new ChooseTimeDialog();
                startTimeDialog.setArguments(bundle);

                startTimeDialog.show(getFragmentManager(), "mc");
            }
        });

        Button endTimeButton = view.findViewById(R.id.chooseEndTimeButton);
        endTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Bundle bundle = new Bundle();
                bundle.putInt("DATE",2);

                ChooseTimeDialog endTimeDialog = new ChooseTimeDialog();
                endTimeDialog.setArguments(bundle);

                endTimeDialog.show(getFragmentManager(), "mc");
            }
        });

        Button doneButton = view.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                id++;
                Log.d(LOG_TAG, "End");
                id = id +1;
                EditText et = view.findViewById(R.id.editTextId);
                meeting.setId(Integer.toString(id));
                meeting.setTitle(et.getText().toString());
                Location location = new Location(11212, 2121);
                meeting.setLocation(location);
                ArrayList<String> friends = new ArrayList<>();
                meeting.setInvitedFriends(friends);
                Meeting newMeeting = meeting;
                Model.getInstance().getUser().addMeeting(newMeeting);

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}