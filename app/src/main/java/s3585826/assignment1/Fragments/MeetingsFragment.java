package s3585826.assignment1.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import s3585826.assignment1.Activities.MeetingInfoActivity;
import s3585826.assignment1.Activities.NewMeetingActivity;
import s3585826.assignment1.Model.Meeting;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;

/**
 * Fragment class for meetings tab
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class MeetingsFragment extends Fragment {

    private static final String LOG_TAG = "Meetings fragment";
    ArrayList<String> meetings;
    BaseAdapter meetingsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meetings,container, false);
        final ListView flv = view.findViewById(R.id.mlw1);

//        String  [] attendees = new String [4];
//        attendees[0] = "Bob Bonucci";
//        attendees[1] = "Alice Anderson";
//        attendees[2] = "Javier Jimenez";
//        attendees[3] = "Fredrick Fransz";
//
//        Location location = new Location(-37.820488, 144.973784);
//
//        Meeting meeting2 = new Meeting("80", "bbq", "1230", "13:30", "13/14/17", attendees, location);
//        Model.getInstance().getUser().addMeeting(meeting2);
//
//        Meeting meeting1 = new Meeting("9", "Picnic", "1130", "13:30", "13/14/17", attendees, location);
//        Model.getInstance().getUser().addMeeting(meeting1);
//
//        Meeting meeting = new Meeting("8", "Picnic on the Yarra", "1030", "13:30", "13/14/17", attendees, location);
//        Model.getInstance().getUser().addMeeting(meeting);

        //setup adapter for listview
        meetings = new ArrayList<>();
        meetingsAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, meetings);
        flv.setAdapter(meetingsAdapter);
        updateView();

        // Navigate to meeting info page
        flv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(MeetingsFragment.this.getActivity(), MeetingInfoActivity.class);
                String listItem = (String) adapterView.getItemAtPosition(i);

                Meeting m = null;
                for (Meeting e : Model.getInstance().getUser().getMeetings().values()) {
                    if (listItem.equals(e.getTitle())) {
                        m = e;
                    }
                }
                Model.getInstance().setFocusMeeting(m);
                MeetingsFragment.this.getActivity().startActivity(intent);
                Log.d(LOG_TAG, "OnItemClick" + listItem);
            }
        });

        // Delete meeting on longClick
        flv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view12, final int i, long l) {

                Log.d(LOG_TAG, "OnItemLongClick");

                adapterView.getChildAt(i).setBackgroundColor(Color.RED);

                AlertDialog.Builder alert = new AlertDialog.Builder(MeetingsFragment.this.getContext());
                alert.setTitle("Delete");
                alert.setMessage("Are you sure you want to delete meeting?");
                alert.setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                flv.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        String listItem = (String) adapterView.getItemAtPosition(i);

                                        Meeting m = null;
                                        for (Meeting e : Model.getInstance().getUser().getMeetings().values()) {
                                            if (listItem.equals(e.getTitle())) {
                                                m = e;
                                            }
                                        }

                                        //remove meeting from model and update view
                                        Model.getInstance().getUser().getMeetings().values().remove(m);
                                        updateView();
                                        adapterView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                                    }
                                }, 400);
                            }
                        });

                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                        dialog.cancel();
                    }
                });

                alert.show();
                return true;
            }
        });

        // Assign add new meeting function to floating action button
        FloatingActionButton flb = view.findViewById(R.id.ffab);
        flb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view13) {
                Model.getInstance().setMeetingFocus(true);
                Intent addMeetingIntent = new Intent(MeetingsFragment.this.getActivity(), NewMeetingActivity.class);
                MeetingsFragment.this.startActivity(addMeetingIntent);
            }
        });

        // Assign function to sort to Button
        final boolean clicked = false;
        Button sortingByTime = view.findViewById(R.id.sortingButton);
        sortingByTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Meeting> sortedMeetings = Model.getInstance().getUser().sortMeetingsByTimeAscending();
                meetings.clear();
                for(Meeting e: sortedMeetings){
                    meetings.add(e.getTitle());
                }
                meetingsAdapter.notifyDataSetChanged();
            }
        });

        // Assign function to suggest button
        Button suggestButton = view.findViewById(R.id.suggestButton);
        suggestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Model.getInstance().getUser().getSuggestedMeetings().size()>0) {
                    int index = 0;
                    Bundle bundle = new Bundle();
                    bundle.putInt("index", index);

                    SuggestMeetingDialog dialog = new SuggestMeetingDialog();
                    dialog.setArguments(bundle);
                    dialog.setTargetFragment(MeetingsFragment.this, 1);
                    dialog.show(MeetingsFragment.this.getFragmentManager(), "SuggestMeeting");
                }else{
                    Toast.makeText(getActivity(), "No suggestions", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }

    // Populate array list of meetings for ListView with Titles from the users meetings hashmap
    public void updateView(){
        meetings.clear();
        for(Meeting e: Model.getInstance().getUser().getMeetings().values()){
            meetings.add(e.getTitle());
        }
        meetingsAdapter.notifyDataSetChanged();
    }

}