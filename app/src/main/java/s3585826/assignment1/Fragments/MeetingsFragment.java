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
import android.widget.ListView;

import java.util.ArrayList;

import s3585826.assignment1.Activities.MeetingInfoActivity;
import s3585826.assignment1.Activities.NewMeetingActivity;
import s3585826.assignment1.Model.Friend;
import s3585826.assignment1.Model.Meeting;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;

public class MeetingsFragment extends Fragment {
    private static final String LOG_TAG = "Meetings fragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetings,container, false);
        final ListView flv = view.findViewById(R.id.mlw1);

        final ArrayList<String> meetings = new ArrayList<>();
        for(Meeting e: Model.getInstance().getUser().getMeetings().values()){
            meetings.add(e.getTitle());
        }
        Log.d(LOG_TAG, Integer.toString(meetings.size()));

        final BaseAdapter meetingsAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1,
            meetings) {
        };
        flv.setAdapter(meetingsAdapter);

        flv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
            }
        });

        flv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int i, long l) {

                Log.d(LOG_TAG, "OnItemLongClick");

                adapterView.getChildAt(i).setBackgroundColor(Color.RED);

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Delete");
                alert.setMessage("Are you sure you want to delete meeting?");

                alert.setPositiveButton(android.R.string.yes,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            flv.postDelayed(new Runnable() {

                                @Override
                                public void run() {
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
                                }
                            }, 400);
                        }
                    });

                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        adapterView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                        dialog.cancel();
                    }
                });

                alert.show();
                return true;
            }
        });

        FloatingActionButton flb = view.findViewById(R.id.ffab);
        flb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.getInstance().setMeetingFocus(true);
                Intent addMeetingIntent = new Intent(getActivity(),NewMeetingActivity.class);
                startActivity(addMeetingIntent);
            }
        });
        return view;
    }
}