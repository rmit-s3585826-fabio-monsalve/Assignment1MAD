package s3585826.assignment1.Fragments;

import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import s3585826.assignment1.Model.Meeting;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;

/**
 * Created by Callum on 8/08/2017.
 */

public class NewMeetingFragment extends android.support.v4.app.Fragment {
    int id = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_meeting, container, false);

        /* Display a list of checkboxes */
        Button friendsButton = (Button) view.findViewById(R.id.chooseFriendsButton);
        friendsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                new ChooseFriendDialog().show(getFragmentManager(), "mc");
            }
        });
          /* Display date picker */
        Button dateButton = (Button) view.findViewById(R.id.chooseDateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                new ChooseDateDialog().show(getFragmentManager(), "mc");
            }
        });

        Button doneButton = (Button) view.findViewById(R.id.doneButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
               //TODO
                EditText et = (EditText) view.findViewById(R.id.editTextId);
                Meeting meeting = new Meeting(id + 1, et.getText(), );
                Model.getInstance().getUser().addMeeting(meeting);
            }
        });


        return view;
    }
}