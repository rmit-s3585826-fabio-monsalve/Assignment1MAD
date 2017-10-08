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
import java.util.concurrent.TimeUnit;

import s3585826.assignment1.Activities.MeetingInfoActivity;
import s3585826.assignment1.Activities.NewMeetingActivity;
import s3585826.assignment1.Database.DatabaseHandler;
import s3585826.assignment1.Model.Meeting;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;

/**
 * Fragment class for meetings tab
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class MeetingsFragment extends Fragment {

    private static final String LOG_TAG = "Meetings fragment";
    ArrayList<Meeting> meetings;
    BaseAdapter meetingsAdapter;

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create thread to show dialog every 30 seconds
        new Thread (new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        Log.d(LOG_TAG, "Thread asleep now..");
                        Thread.sleep(TimeUnit.SECONDS.toMillis(Model.getInstance().getUser().getSuggestionInterval()));

                        if(Model.getInstance().getConnected()) {
                            if (Model.getInstance().getUser().generateSuggestedMeetings().size() > 0) {
                                int index = 0;
                                Bundle bundle = new Bundle();
                                bundle.putInt("index", index);
                                SuggestMeetingDialog dialog = new SuggestMeetingDialog();
                                dialog.setArguments(bundle);
                                dialog.setTargetFragment(MeetingsFragment.this, 1);
                                dialog.show(MeetingsFragment.this.getFragmentManager(), "SuggestMeeting");
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Log.d(LOG_TAG, "onCreate()");

        View view = inflater.inflate(R.layout.fragment_meetings,container, false);
        final ListView flv = view.findViewById(R.id.mlw1);

        //setup adapter for listView
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
                Meeting listItem = (Meeting)adapterView.getItemAtPosition(i);

                Meeting m = null;
                for (Meeting e : Model.getInstance().getUser().getMeetings().values()) {
                    if (listItem.getId().equals(e.getId())) {
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
                                    Meeting listItem = (Meeting) adapterView.getItemAtPosition(i);

                                    Meeting m = null;
                                    for (Meeting e : Model.getInstance().getUser().getMeetings().values()) {
                                        if (listItem.getId().equals(e.getId())) {
                                            m = e;
                                        }
                                    }

                                    //remove meeting from model and update view
                                    Model.getInstance().getUser().getMeetings().values().remove(m);
                                    DatabaseHandler db = new DatabaseHandler(getContext(), null, null, 1);
                                    db.deleteMeeting(m.getId());
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
        Button sortingByTime = view.findViewById(R.id.sortingButton);
        sortingByTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Meeting> sortedMeetings = Model.getInstance().getUser().sortMeetingsByTimeAscending();
                meetings.clear();
                for(Meeting e: sortedMeetings){
                    meetings.add(e);
                }
                meetingsAdapter.notifyDataSetChanged();
            }
        });

        // Assign function to suggest button
        Button suggestButton = view.findViewById(R.id.suggestButton);
        suggestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Model.getInstance().getConnected()) {
                    if (Model.getInstance().getUser().generateSuggestedMeetings().size() > 0) {
                        int index = 0;
                        Bundle bundle = new Bundle();
                        bundle.putInt("index", index);

                        SuggestMeetingDialog dialog = new SuggestMeetingDialog();
                        dialog.setArguments(bundle);
                        dialog.setTargetFragment(MeetingsFragment.this, 1);
                        dialog.show(MeetingsFragment.this.getFragmentManager(), "SuggestMeeting");
                    } else {
                        Toast.makeText(getActivity(), "No suggestions", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Need connectivity to make suggestions", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }



    // Populate array list of meetings for ListView with Titles from the users meetings hashmap
    public void updateView(){
        meetings.clear();
        for(Meeting e: Model.getInstance().getUser().getMeetings().values()){
            meetings.add(e);
        }
        meetingsAdapter.notifyDataSetChanged();
    }

}