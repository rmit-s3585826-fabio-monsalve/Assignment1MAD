package s3585826.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import s3585826.assignment1.Model.Meeting;
import s3585826.assignment1.Model.Model;

public class Meetings extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetings,container,
            false);
        ListView flv = view.findViewById(R.id.mlw1);

        ArrayList<String> meetings = new ArrayList<>();
        for(Meeting e: Model.getInstance().getUser().getMeetings().values()){
            meetings.add(e.getId());
        }

        BaseAdapter meetingsAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, meetings) {
        };
        flv.setAdapter(meetingsAdapter);

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
}