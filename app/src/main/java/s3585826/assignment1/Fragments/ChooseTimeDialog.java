package s3585826.assignment1.Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

/**
 * Fragment for the TimePicker dialog, this class was used for setting the which friends are attending the meeting
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class ChooseTimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private int timeType;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        // Get system time
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int hour = c.get(java.util.Calendar.HOUR_OF_DAY);
        int minute = c.get(java.util.Calendar.MINUTE);

        // Chooses which time picker to use
        // Two time pickers are needed because both meetings and friends need one
        Bundle bundle = this.getArguments();
        if(bundle != null){
            timeType = bundle.getInt("DATE",1);
        }

        switch (timeType) {
            case 1:
                return new TimePickerDialog(getActivity(), this, hour, minute, true);
            case 2:
                return new TimePickerDialog(getActivity(), this, hour, minute, true);
        }
        return null;
    }

    // Set start time and end time for meeting
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

        if(timeType == 1){
            String date =  hour + ":" + minute;
            NewMeetingFragment.meeting.setStartTime(date);
        }
        else{
            String date =  hour + ":" + minute;
            NewMeetingFragment.meeting.setEndTime(date);
        }
    }
}
