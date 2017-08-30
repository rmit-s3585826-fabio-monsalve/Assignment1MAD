package s3585826.assignment1;

import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Callum on 8/08/2017.
 */

public class NewMeetingFragment extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_meeting, container, false);

        /* Display a list of checkboxes */
        Button friendsButton = (Button) view.findViewById(R.id.chooseFriendsButton);
        friendsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                new ChooseFriendFragment().show(getFragmentManager(), "mc");
            }
        });
          /* Display date picker */
        Button dateButton = (Button) view.findViewById(R.id.chooseDateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                new ChooseDateFragment().show(getFragmentManager(), "mc");
            }
        });

        return view;
    }
}
