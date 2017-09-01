package s3585826.assignment1.Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

public class ChooseTimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private int timeType;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final java.util.Calendar c = java.util.Calendar.getInstance();
        int hour = c.get(java.util.Calendar.HOUR_OF_DAY);
        int minute = c.get(java.util.Calendar.MINUTE);

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
