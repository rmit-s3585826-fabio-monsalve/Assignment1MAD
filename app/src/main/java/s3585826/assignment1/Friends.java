package s3585826.assignment1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class Friends extends Fragment {

    private static final String LOG_TAG = "Friends Activity";
    private View view;
    private int friendCount = 0;
    protected static final int PICK_CONTACTS = 100;
    private ArrayList<String> names = new ArrayList<>();
    private BaseAdapter adapter;
    private boolean firstVisit = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_friends, container, false);
        final ListView flv = view.findViewById(R.id.flw1);

        for (Friend e : MainActivity.user1.getFriendList()) {
            if(firstVisit) {
                names.add(e.getName());
            }
        }
        firstVisit = false;

        adapter = new ArrayAdapter<>(this.getContext(),
            android.R.layout.simple_list_item_1, names);

        flv.setAdapter(adapter);
        flv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final
            View view, final int position, long id) {
                Log.d(LOG_TAG, "OnItemLongClick");

                parent.getChildAt(position).
                    setBackgroundColor(Color.RED);

                AlertDialog.Builder alert =
                    new AlertDialog.Builder(getContext());
                alert.setTitle("Delete");
                alert.setMessage("Are you sure you want to delete contact?");

                alert.setPositiveButton(android.R.string.yes, new
                    DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            flv.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    String listItem = (String)
                                        parent.getItemAtPosition(position);
                                    names.remove(listItem);
                                    adapter.notifyDataSetChanged();
                                    parent.getChildAt(position).
                                        setBackgroundColor(Color.TRANSPARENT);
                                }
                            }, 400);
                        }
                    });

                alert.setNegativeButton(android.R.string.no, new
                    DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                        }
                    });

                alert.show();
                return true;
            }
        });
/*
                flv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String listItem = (String) parent.getItemAtPosition
                            (position);
                        names.remove(listItem);
                        adapter.notifyDataSetChanged();
                    }
                }, 400);
                return false;
            }
        });
*/
        if(MainActivity.user1.getFriendList().size() == 0) {
            Toast.makeText(getActivity(), "You have no friends",
                Toast.LENGTH_SHORT).show();
        }else{
            //TODO
        }

        FloatingActionButton flb = view.findViewById(R.id.ffab);

        flb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(contactPickerIntent, PICK_CONTACTS);
            }
        });

        Log.d(LOG_TAG, "onCreateView");

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String id = "";
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
                    Friend friend = new Friend (name, id, email);
                    MainActivity.user1.getFriendList().add(friend);
                    adapter.notifyDataSetChanged();
                } catch (ContactDataManager.ContactQueryException e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }
        }
        Log.d(LOG_TAG, "onActivityResult");
    }
}