package s3585826.assignment1.Fragments;

import android.app.AlertDialog;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

import s3585826.assignment1.Support_Code.ContactDataManager;
import s3585826.assignment1.Activities.FriendInfoActivity;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.Model.Friend;
import s3585826.assignment1.R;

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

        // Navigate to friend info page
        friendsListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(), FriendInfoActivity.class);

            String listItem = (String) adapterView.getItemAtPosition(i);
            Friend f = null;

            // Populate array list of friends for ListView with names from the users friends hashmap
            for(Friend e: Model.getInstance().getUser().getFriends().values()){
                if(listItem.equals(e.getName())){
                    f = e;
                }
            }
            Model.getInstance().setFocusFriend(f);
            getActivity().startActivity(intent);
        });

        // Delete friend on long click
        friendsListView.setOnItemLongClickListener((adapterView, view, i, l) -> {

            adapterView.getChildAt(i).setBackgroundColor(Color.RED);

            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("Delete");
            alert.setMessage("Are you sure you want to delete friend?");

            alert.setPositiveButton(android.R.string.yes,
                (dialog, which) -> friendsListView.postDelayed(() -> {
                    String listItem = (String) adapterView.getItemAtPosition(i);

                    // Find friend in user's friend hashmap with name of friend
                    Friend f = null;
                    for(Friend e: Model.getInstance().getUser().getFriends().values()){
                        if(listItem.equals(e.getName())){
                            f = e;
                        }
                    }

                    Model.getInstance().getUser().getFriends().values().remove(f);

                    names.remove(listItem);
                    Model.getInstance().setFocusFriend(f);

                    // Update list
                    adapter.notifyDataSetChanged();
                    adapterView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }, 400));

            alert.setNegativeButton(android.R.string.no, (dialog, which) -> {
                adapterView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                dialog.cancel();
            });

            alert.show();
            return true;
        });

        // Assign add new friend from contacts function to floating action button
        FloatingActionButton flb = friendsView.findViewById(R.id.ffab);
        flb.setOnClickListener(view -> {
            Model.getInstance().setMeetingFocus(false);
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(contactPickerIntent, PICK_CONTACTS);
        });

        Log.d(LOG_TAG, "onCreateView" + Model.getInstance().getUser().getFriends().size());

        return friendsView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.friends_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String name;
        String email;

        if (requestCode == PICK_CONTACTS) {

            if (resultCode == RESULT_OK) {
                ContactDataManager contactsManager = new
                    ContactDataManager(this.getContext(), data);

                try {

                    // Create friend from contacts
                    Model.getInstance().incrementFriendId();
                    name = contactsManager.getContactName();
                    email = contactsManager.getContactEmail();
                    Friend friend = new Friend(Integer.toString(Model.getInstance().getFriendId()), name, email, null);
                    Model.getInstance().getUser().getFriends().put(Integer.toString(Model.getInstance().getFriendId()), friend);

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