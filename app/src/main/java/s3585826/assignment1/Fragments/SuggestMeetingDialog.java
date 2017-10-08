package s3585826.assignment1.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import s3585826.assignment1.Database.DatabaseHandler;
import s3585826.assignment1.Model.Meeting;
import s3585826.assignment1.Model.Model;

/**
 * Fragment for displaying meeting suggestions.
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class SuggestMeetingDialog extends DialogFragment {

    private static final String LOG_TAG = "SuggestMeetingDialog";
    int index;
    Meeting meeting;
    MeetingsFragment parentFrag;

    @Override
    public AlertDialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get reference to parent fragment
        parentFrag = ((MeetingsFragment)SuggestMeetingDialog.this.getTargetFragment());

        // get index of correct suggested meeting
        index = getArguments().getInt("index");

        // get meeting
        meeting = Model.getInstance().getUser().getSuggestedMeetings().get(index);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Schedule Meeting")
                .setMessage("Schedule a meeting with " + meeting.getInvitedFriends()[0] + " at " + meeting.getStartTime())
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Add meeting and increment meetingID
                        Model.getInstance().getUser().addMeeting(meeting);
                        DatabaseHandler db = new DatabaseHandler(getContext(), null, null, 1);
                        db.addMeeting(meeting);
                        Model.getInstance().incrementMeetingId();
                        if (parentFrag!=null)
                            parentFrag.updateView();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Suggest next meeting. Change text and keep dialog open?
                        index++;
                        if (index<(Model.getInstance().getUser().generateSuggestedMeetings().size())) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("index", index);

                            SuggestMeetingDialog dialog = new SuggestMeetingDialog();
                            dialog.setArguments(bundle);
                            dialog.setTargetFragment(parentFrag,1);
                            dialog.show(getFragmentManager(), "SuggestMeeting");
                        }
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Close dialog and stop suggesting
                    }
                })
                .create();
    }
}
