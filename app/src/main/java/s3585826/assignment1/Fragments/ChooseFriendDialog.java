package s3585826.assignment1.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import java.util.ArrayList;

import s3585826.assignment1.Model.Friend;
import s3585826.assignment1.Model.Model;

public class ChooseFriendDialog extends DialogFragment {

    private static final String LOG_TAG = "ChooseFriendDialog";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "array" + Model.getInstance().getUser().getfriendsStringArray().length);
        final boolean[] checkedFriends = new boolean[]{false, true, false, true, false};
        final ArrayList<String> attendees = new ArrayList<>();

        //get list of names to populate dialog
        final CharSequence[] names = new CharSequence[Model.getInstance().getUser().getFriends().size()];
        int i=0;
        for (Friend friend :Model.getInstance().getUser().getFriends().values()) {
            names[i]=friend.getName();
            i++;
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle("Choose Friends")
                .setMultiChoiceItems(names, null , new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if(isChecked)
                                    attendees.add(names[which].toString());
                            }
                        })
                .setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                NewMeetingFragment.meeting.setInvitedFriends(attendees);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {

                        /* User clicked No so do some stuff */
                            }
                        }).create();
    }
}
