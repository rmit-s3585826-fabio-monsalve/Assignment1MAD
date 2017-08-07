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
    protected static final int PICK_CONTACTS = 100;
    private BaseAdapter adapter;
    private boolean firstVisit = true;
    private ArrayList<String> names;
    int idNo = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
        savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        final ListView flv = view.findViewById(R.id.flw1);

        if(firstVisit) {
            if(MainActivity.user1.getFriendMap().size() == 0) {
                Toast.makeText(getActivity(), "You have no friends",
                    Toast.LENGTH_SHORT).show();
            }
        }
        firstVisit = false;

        names = new ArrayList<>();
        for(Friend e: MainActivity.user1.getFriendMap().values()){
            names.add(e.getName());
        }


        adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, names);
        flv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent();
                intent.setClass(getActivity(), FriendInfo.class);
                getActivity().startActivity(intent);
            }
        });

        flv.setAdapter(adapter);
        flv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {

                Log.d(LOG_TAG, "OnItemLongClick");

                parent.getChildAt(position).setBackgroundColor(Color.RED);

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Delete");
                alert.setMessage("Are you sure you want to delete contact?");

                alert.setPositiveButton(android.R.string.yes,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            flv.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    String listItem = (String) parent.getItemAtPosition(position);

                                    Friend i = null;
                                    for(Friend e: MainActivity.user1.getFriendMap().values()){
                                        if(listItem.equals(e.getName())){
                                            i = e;
                                        }
                                    }

                                    MainActivity.user1.getFriendMap().values().remove(i);

                                    names.remove(listItem);
                                    adapter.notifyDataSetChanged();
                                    parent.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                                }
                            }, 400);
                        }
                    });

                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        parent.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
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
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(contactPickerIntent, PICK_CONTACTS);
            }
        });

        Log.d(LOG_TAG, "onCreateView");

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String id = "0";
        String name = "";
        String email = "";

        if (requestCode == PICK_CONTACTS) {
            if (resultCode == RESULT_OK) {
                ContactDataManager contactsManager = new
                    ContactDataManager(this.getContext(), data);
                try {
                    idNo = idNo + 1;
                    id = String.valueOf(idNo);
                    name = contactsManager.getContactName();
                    email = contactsManager.getContactEmail();
                    Friend friend = new Friend(id, name, email);
                    MainActivity.user1.getFriendMap().put(id, friend);

                    names.add(friend.getName());
                    adapter.notifyDataSetChanged();
                } catch (ContactDataManager.ContactQueryException e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }
        }
        Log.d(LOG_TAG, "onActivityResult");
    }
}