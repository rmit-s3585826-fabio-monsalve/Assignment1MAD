package s3585826.assignment1.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import java.util.Calendar;

import s3585826.assignment1.Model.Model;

/**
 * Fragment for the Date Picker dialog, this class was used for setting the birthday of friends and meeting dates
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class ChooseDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    // Method for setting calendar from system to be used in dialog
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        // Set date from user input
        month = month + 1;
        String date = month + "/" + day + "/" + year;

        // If a meeting is being created then set date else if it's a friends birthday set date
        if(Model.getInstance().isMeetingFocus()){
            NewMeetingFragment.meeting.setDate(date);
        }else {
            String friendid = Model.getInstance().getFocusFriend().getId();
            Model.getInstance().getUser().getFriends().get(friendid).setBirthday(date);
        }
    }
}