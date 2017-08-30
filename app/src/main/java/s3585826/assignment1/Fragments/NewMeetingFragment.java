package s3585826.assignment1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import s3585826.assignment1.R;

public class NewMeetingFragment extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_meeting, container, false);


      /* Display a list of checkboxes */
        Button friendsButton = (Button) view.findViewById(R.id.select_friends_button);
        friendsButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                new ChooseFriendDialog().show(getFragmentManager(), "mc");
            }
        });
          /* Display date picker */
        Button dateButton = (Button) view.findViewById(R.id.select_date_button);
        dateButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                new ChooseDateDialog().show(getFragmentManager(), "mc");
            }
        });


        return view;
    }
}
