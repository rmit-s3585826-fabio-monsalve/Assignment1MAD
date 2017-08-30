package s3585826.assignment1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Callum on 8/08/2017.
 */

public class ChooseFriendFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Who is attending?")
                .setMultiChoiceItems(
                        R.array.Friends, null, new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton, boolean isChecked)
                            {
                        /* User clicked on a check box do some stuff */
                            }
                        })
                .setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {

                        /* User clicked Yes so do some stuff */
                            }
                        })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {

                        /* User clicked No so do some stuff */
                            }
                        }).create();
    }


}
