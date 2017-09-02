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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

        if(Model.getInstance().getUser().getFriends().size() == 0) {
            Toast.makeText(getActivity(), "You have no friends", Toast.LENGTH_SHORT).show();
            }

        //add names to list view adapter
        names = new ArrayList<>();
        for(Friend e: Model.getInstance().getUser().getFriends().values())
            names.add(e.getName());
        adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, names);

        final View friendsView = inflater.inflate(R.layout.fragment_friends, container, false);
        final ListView friendsListView = friendsView.findViewById(R.id.flw1);
        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FriendInfoActivity.class);
                String listItem = (String) adapterView.getItemAtPosition(i);
                Friend f = null;
                for(Friend e: Model.getInstance().getUser().getFriends().values()){
                    if(listItem.equals(e.getName())){
                        f = e;
                    }
                }
                Model.getInstance().setFocusFriend(f);
                getActivity().startActivity(intent);
                Log.d(LOG_TAG, "OnItemClick" + listItem);
            }
        });

        friendsListView.setAdapter(adapter);
        friendsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int i, long l) {

                Log.d(LOG_TAG, "OnItemLongClick");

                adapterView.getChildAt(i).setBackgroundColor(Color.RED);

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Delete");
                alert.setMessage("Are you sure you want to delete friend?");

                alert.setPositiveButton(android.R.string.yes,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            friendsListView.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    String listItem = (String) adapterView.getItemAtPosition(i);

                                    Friend f = null;
                                    for(Friend e: Model.getInstance().getUser().getFriends().values()){
                                        if(listItem.equals(e.getName())){
                                            f = e;
                                        }
                                    }

                                    Model.getInstance().getUser().getFriends().values().remove(f);

                                    names.remove(listItem);
                                    Model.getInstance().setFocusFriend(f);
                                    adapter.notifyDataSetChanged();
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

        FloatingActionButton flb = friendsView.findViewById(R.id.ffab);
        flb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(contactPickerIntent, PICK_CONTACTS);
            }
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
                    Model.getInstance().incrementFriendId();
                    name = contactsManager.getContactName();
                    email = contactsManager.getContactEmail();
                    Friend friend = new Friend(Integer.toString(Model.getInstance().getFriendId()), name, email, null);
                    Model.getInstance().getUser().getFriends().put(Integer.toString(Model.getInstance().getFriendId()), friend);

                    names.add(friend.getName());
                    Model.getInstance().setFocusFriend(friend);

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