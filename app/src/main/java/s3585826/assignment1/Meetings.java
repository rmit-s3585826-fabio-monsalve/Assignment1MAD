package s3585826.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

//Implement expandable list view for friends attending the scheduled meetings

public class Meetings extends Fragment {

    private static final String LOG_TAG = "1";
    private int friendCount = 0;
    protected static final int PICK_CONTACTS = 100;
    private ArrayList<String> names = new ArrayList<>();
    private BaseAdapter la;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetings,container,
            false);
        ListView flv = view.findViewById(R.id.mlw1);
        la = new ArrayAdapter<String>(this.getContext(),
            android.R.layout.simple_list_item_1, names) {
        };
        flv.setAdapter(la);

        FloatingActionButton flb = view.findViewById(R.id.ffab);
        flb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(contactPickerIntent, PICK_CONTACTS);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String name = "";
        String email = "";

        if (requestCode == PICK_CONTACTS) {
            if (resultCode == RESULT_OK) {
                ContactDataManager contactsManager = new
                    ContactDataManager(this.getContext(), data);
                try {
                    name = contactsManager.getContactName();
                    email = contactsManager.getContactEmail();
                    names.add(name);
                    la.notifyDataSetChanged();
                } catch (ContactDataManager.ContactQueryException e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }
        }
    }
}