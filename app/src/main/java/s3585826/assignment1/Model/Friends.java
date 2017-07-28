package s3585826.assignment1.Model;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import s3585826.assignment1.R;

import static android.app.Activity.RESULT_OK;

public class Friends extends Fragment{

    private static final String LOG_TAG = "1";
    private ContactDataManager cdm;
    private View view;
    private int friendCount = 0;
    protected static final int PICK_CONTACTS = 100;
    private Button fb1;
    private ListView flv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friends,container,false);

        if(friendCount == 0) {
            TextView ft1 = (TextView) view.findViewById(R.id.ft1);
            ft1.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "You have no friends", Toast.LENGTH_LONG).show();
        }else{
            //TODO
        }

        fb1 = view.findViewById(R.id.fb1);
        fb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
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
                ContactDataManager contactsManager = new ContactDataManager(this.getContext(), data);

                try {
                    name = contactsManager.getContactName();
                    email = contactsManager.getContactEmail();
                    String [] names = {name};
                    flv = view.findViewById(R.id.flw1);
                    ListAdapter la = new ArrayAdapter<String>(this.getContext(), R.layout.fragment_friends, names);
                    flv.setAdapter(la);

/*
                    flv.setOnItemClickListener(
                        new AdapterView.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }

                        }
                    );*/
                } catch (ContactDataManager.ContactQueryException e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }
        }


    }

}