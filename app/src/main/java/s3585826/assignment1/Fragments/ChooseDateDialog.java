package s3585826.assignment1.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import java.util.Calendar;

import s3585826.assignment1.Model.Model;

public class ChooseDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        month = month + 1;

        String date = month + "/" + day + "/" + year;
        String friendid = Model.getInstance().getFocusFriend().getId();
        Model.getInstance().getUser().getFriends().get(friendid).setBirthday(date);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
}