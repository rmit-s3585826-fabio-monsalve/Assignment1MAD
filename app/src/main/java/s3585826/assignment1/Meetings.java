package s3585826.assignment1;

import android.content.Intent;
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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

//Implement expandable list view for friends attending the scheduled meetings

public class Meetings extends Fragment {

    private static final String LOG_TAG = "1";
    private int meetingCount = 0;
    protected static final int PICK_CONTACTS_RC = 100;
    protected static final int ADD_MEETING_RC = 200;
    private ArrayList<Meeting> meetings = new ArrayList<>();
    private BaseAdapter meetingsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetings,container,
            false);
        ListView flv = view.findViewById(R.id.mlw1);
        meetingsAdapter = new ArrayAdapter<Meeting>(this.getContext(),
            android.R.layout.simple_list_item_1, meetings) {
        };
        flv.setAdapter(meetingsAdapter);

        if(meetingCount == 0) {
            Toast.makeText(getActivity(), "You have no meetings",
                Toast.LENGTH_LONG).show();
        }else{
            //TODO
        }
        FloatingActionButton flb = view.findViewById(R.id.ffab);
        flb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMeetingIntent = new Intent(getActivity(),NewMeetingActivity.class);
                startActivity(addMeetingIntent);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_CONTACTS_RC) {
            if (resultCode == RESULT_OK) {
                ContactDataManager contactsManager = new
                        ContactDataManager(this.getContext(), data);

                try {
                    String name = contactsManager.getContactName();
                    Intent addMeetingIntent = new Intent(this.getContext(), NewMeetingActivity.class);
                    addMeetingIntent.putExtra("name", "John");
                    startActivityForResult(addMeetingIntent, ADD_MEETING_RC);
                } catch (ContactDataManager.ContactQueryException e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }
        }
    }
}