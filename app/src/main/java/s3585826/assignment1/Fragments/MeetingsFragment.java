package s3585826.assignment1.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

import s3585826.assignment1.Activities.MeetingInfoActivity;
import s3585826.assignment1.Activities.NewMeetingActivity;
import s3585826.assignment1.Model.Location;
import s3585826.assignment1.Model.Meeting;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;

/**
 * Fragment class for meetings tab
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class MeetingsFragment extends Fragment {
    private static final String LOG_TAG = "Meetings fragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetings,container, false);
        final ListView flv = view.findViewById(R.id.mlw1);

        String  [] attendees = new String [4];
        attendees[0] = "Bob Bonucci";
        attendees[1] = "Alice Anderson";
        attendees[2] = "Javier Jimenez";
        attendees[3] = "Fredrick Fransz";

        Location location = new Location(-37.820488, 144.973784);

        Meeting meeting2 = new Meeting("80", "bbq", "1230", "13:30", "13/14/17", attendees, location);
        Model.getInstance().getUser().addMeeting(meeting2);

        Meeting meeting1 = new Meeting("9", "Picnic", "1130", "13:30", "13/14/17", attendees, location);
        Model.getInstance().getUser().addMeeting(meeting1);

        Meeting meeting = new Meeting("8", "Picnic on the Yarra", "1030", "13:30", "13/14/17", attendees, location);
        Model.getInstance().getUser().addMeeting(meeting);

        // Create list for listView
        final ArrayList<String> meetings = new ArrayList<>();

        // Populate array list of meetings for ListView with Titles from the users meetings hashmap
        for(Meeting e: Model.getInstance().getUser().getMeetings().values()){
            meetings.add(e.getTitle());
        }

        Log.d(LOG_TAG, Integer.toString(meetings.size()));
        final BaseAdapter meetingsAdapter = new ArrayAdapter<String>(this.getContext(),
            android.R.layout.simple_list_item_1, meetings) {
        };

        flv.setAdapter(meetingsAdapter);

        // Navigate to meeting info page
        flv.setOnItemClickListener((adapterView, view1, i, l) -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(), MeetingInfoActivity.class);
            String listItem = (String) adapterView.getItemAtPosition(i);

            Meeting m = null;
            for(Meeting e: Model.getInstance().getUser().getMeetings().values()){
                if(listItem.equals(e.getTitle())){
                    m = e;
                }
            }
            Model.getInstance().setFocusMeeting(m);
            getActivity().startActivity(intent);
            Log.d(LOG_TAG, "OnItemClick" + listItem);
        });

        // Delete meeting on longClick
        flv.setOnItemLongClickListener((adapterView, view12, i, l) -> {

            Log.d(LOG_TAG, "OnItemLongClick");

            adapterView.getChildAt(i).setBackgroundColor(Color.RED);

            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("Delete");
            alert.setMessage("Are you sure you want to delete meeting?");

            alert.setPositiveButton(android.R.string.yes,
                (dialog, which) -> flv.postDelayed(() -> {
                    String listItem = (String) adapterView.getItemAtPosition(i);

                    Meeting m = null;
                    for(Meeting e: Model.getInstance().getUser().getMeetings().values()){
                        if(listItem.equals(e.getTitle())){
                            m = e;
                        }
                    }

                    Model.getInstance().getUser().getMeetings().values().remove(m);

                    meetings.remove(listItem);
                    Model.getInstance().setFocusMeeting(m);
                    meetingsAdapter.notifyDataSetChanged();
                    adapterView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }, 400));

            alert.setNegativeButton(android.R.string.no, (dialog, which) -> {
                adapterView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                dialog.cancel();
            });

            alert.show();
            return true;
        });

        // Assign add new meeting function to floating action button
        FloatingActionButton flb = view.findViewById(R.id.ffab);
        flb.setOnClickListener(view13 -> {
            Model.getInstance().setMeetingFocus(true);
            Intent addMeetingIntent = new Intent(getActivity(),NewMeetingActivity.class);
            startActivity(addMeetingIntent);
        });

        // Assign function to sort to Button
        final boolean clicked = false;
        Button sortingByTime = view.findViewById(R.id.sortingButton);
        sortingByTime.setOnClickListener(view14 -> {
                //meetings = Model.getInstance().getUser().sortMeetingsByTimeAscending();
                meetingsAdapter.notifyDataSetChanged();

        });
        return view;
    }
}