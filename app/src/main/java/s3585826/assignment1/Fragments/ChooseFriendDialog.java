package s3585826.assignment1.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;

public class ChooseFriendDialog extends DialogFragment {
    private static final String LOG_TAG = "FriendDialog";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "array" + Model.getInstance().getUser().getfriendsStringArray().length);
        final boolean[] checkedFriends = new boolean[]{
            false,
            true,
            false,
            true,
            false
        };


        return new AlertDialog.Builder(getActivity())
                .setTitle("Choose Friends")
                .setMultiChoiceItems(
                        R.array.Friends,null , new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton, boolean isChecked)
                            {
                        /* User clicked on a check box do some stuff */
                            }
                        })
                .setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {

                        /* User clicked Yes so do some stuff */
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
