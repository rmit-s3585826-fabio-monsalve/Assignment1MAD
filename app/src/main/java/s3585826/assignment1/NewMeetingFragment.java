package s3585826.assignment1;

import android.app.Fragment;
import android.view.View;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Callum on 8/08/2017.
 */

public class NewMeetingFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_meeting, container, false);


      /* Display a list of checkboxes */
        Button friendsButton = (Button) view.findViewById(R.id.select_friends_button);
        friendsButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                new ChooseFriendFragment().show(getFragmentManager(), "mc");
            }
        });
          /* Display date picker */
        Button dateButton = (Button) view.findViewById(R.id.select_date_button);
        dateButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                new ChooseDateFragment().show(getFragmentManager(), "mc");
            }
        });


        return view;
    }
}
