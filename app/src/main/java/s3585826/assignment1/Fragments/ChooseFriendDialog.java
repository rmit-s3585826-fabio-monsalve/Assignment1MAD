package s3585826.assignment1.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import s3585826.assignment1.Model.Friend;
import s3585826.assignment1.Model.Model;

/**
 * Fragment for Picker dialog, this class was used for setting the which friends are attending the meeting
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class ChooseFriendDialog extends DialogFragment {

    private static final String LOG_TAG = "ChooseFriendDialog";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final boolean[] checkedFriends = new boolean[]{false, true, false, true, false};
        final String [] attendees = new String [Model.getInstance().getUser().getFriends().size()];

        // Get list of names to populate dialog
        final CharSequence[] names = new CharSequence[Model.getInstance().getUser().getFriends().size()];
        int i=0;
        for (Friend friend :Model.getInstance().getUser().getFriends().values()) {
            names[i]=friend.getName();
            i++;
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle("Choose Friends")
                .setMultiChoiceItems(names, null , new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked)
                            for (int i1 = 0; i1 < names.length; i1++) {
                                attendees[i1] = names[i1].toString();
                            }
                    }
                })
                .setPositiveButton("Submit",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            NewMeetingFragment.meeting.setInvitedFriends(attendees);
                        }
                    })
                .setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {

                /* User clicked No so do some stuff */
                        }
                    }).create();
    }
}
