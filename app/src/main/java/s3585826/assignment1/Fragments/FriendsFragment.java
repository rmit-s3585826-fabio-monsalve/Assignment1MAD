package s3585826.assignment1.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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

import s3585826.assignment1.Activities.DatabaseTester;
import s3585826.assignment1.Activities.FriendInfoActivity;
import s3585826.assignment1.Database.DatabaseHandler;
import s3585826.assignment1.Model.Friend;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;
import s3585826.assignment1.Support_Code.ContactDataManager;

import static android.app.Activity.RESULT_OK;

/**
 * Fragment class for friends tab
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class FriendsFragment extends Fragment {

    private static final String LOG_TAG = "FriendsFragment";
    protected static final int PICK_CONTACTS = 100;
    private BaseAdapter adapter;
    private ArrayList<String> names;
    private DatabaseHandler databaseHandler;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
        savedInstanceState) {

        setHasOptionsMenu(true);

        // If user has no friends a toast will appear
        if(Model.getInstance().getUser().getFriends().size() == 0) {
            Toast.makeText(getActivity(), "You have no friends", Toast.LENGTH_SHORT).show();
        }

        Log.d(LOG_TAG, Integer.toString(Model.getInstance().getUser().getFriends().size()) + Model.getInstance().getUser().getFriend("2").getName());

        names = new ArrayList<>();
        for(Friend e: Model.getInstance().getUser().getFriends().values()) {
            names.add(e.getName());
        }

        adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, names);

        final View friendsView = inflater.inflate(R.layout.fragment_friends, container, false);
        final ListView friendsListView = friendsView.findViewById(R.id.flw1);
        friendsListView.setAdapter(adapter);

        Button databaseButton = friendsView.findViewById(R.id.databaseButton);
        databaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FriendsFragment.this.getActivity(), DatabaseTester.class);
                FriendsFragment.this.getActivity().startActivity(intent);
            }
        });

        // Navigate to friend info page
        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(FriendsFragment.this.getActivity(), FriendInfoActivity.class);

                String listItem = (String) adapterView.getItemAtPosition(i);
                Friend f = null;

                // Populate array list of friends for ListView with names from the users friends hashmap
                for (Friend e : Model.getInstance().getUser().getFriends().values()) {
                    if (listItem.equals(e.getName())) {
                        f = e;
                    }
                }
                Model.getInstance().setFocusFriend(f);
                FriendsFragment.this.getActivity().startActivity(intent);
            }
        });

        // Delete friend on long click
        friendsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int i, long l) {

                adapterView.getChildAt(i).setBackgroundColor(Color.RED);

                AlertDialog.Builder alert = new AlertDialog.Builder(FriendsFragment.this.getContext());
                alert.setTitle("Delete");
                alert.setMessage("Are you sure you want to delete friend?");

                alert.setPositiveButton(android.R.string.yes,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            friendsListView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String listItem = (String) adapterView.getItemAtPosition(i);

                                    // Find friend in user's friend hashmap with name of friend
                                    Friend f = null;
                                    for (Friend e : Model.getInstance().getUser().getFriends().values()) {
                                        if (listItem.equals(e.getName())) {
                                            f = e;
                                        }
                                    }

                                    Model.getInstance().getUser().getFriends().values().remove(f);

                                    names.remove(listItem);
                                    Model.getInstance().setFocusFriend(f);

                                    // Update list
                                    adapter.notifyDataSetChanged();
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

        // Assign add new friend from contacts function to floating action button
        FloatingActionButton flb = friendsView.findViewById(R.id.ffab);
        flb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.getInstance().setMeetingFocus(false);
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                FriendsFragment.this.startActivityForResult(contactPickerIntent, PICK_CONTACTS);
            }
        });

        Log.d(LOG_TAG, "onCreateView" + Model.getInstance().getUser().getFriends().size());

        return friendsView;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String name;
        String email;
        databaseHandler = new DatabaseHandler(this.getContext(), null, null, 1);

        if (requestCode == PICK_CONTACTS) {

            if (resultCode == RESULT_OK) {
                ContactDataManager contactsManager = new
                    ContactDataManager(this.getContext(), data);

                try {

                    // Create friend from contacts
                    Model.getInstance().incrementFriendId();
                    name = contactsManager.getContactName();
                    email = contactsManager.getContactEmail();
                    Friend friend = new Friend(Integer.toString(Model.getInstance().getFriendId()), name, email, "birthday");
                    Model.getInstance().getUser().getFriends().put(Integer.toString(Model.getInstance().getFriendId()), friend);
                    databaseHandler.addFriend(friend);
                    names.add(friend.getName());
                    Model.getInstance().setFocusFriend(friend);

                    // Navigate to DateDialog fragment
                    new ChooseDateDialog().show(getFragmentManager(), "");

                    adapter.notifyDataSetChanged();

                } catch (ContactDataManager.ContactQueryException e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }
        }
        Log.d(LOG_TAG, "onActivityResult");
    }


}