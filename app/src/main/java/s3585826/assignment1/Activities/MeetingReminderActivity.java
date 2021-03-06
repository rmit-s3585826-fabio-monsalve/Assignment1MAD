package s3585826.assignment1.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;

public class MeetingReminderActivity extends AppCompatActivity {
    int remindIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_reminder);

        // Set user reminder period after notification
        Button button1 = (Button)findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           Model.getInstance().getUser().setReminderPeriodAfterNotification(remindIn);
                                           startActivity(new Intent(MeetingReminderActivity.this,MainActivity.class));
                                       }
                                   }
        );

        // Set up number picker
        NumberPicker numberPicker = (NumberPicker)findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(30);
        numberPicker.setWrapSelectorWheel(true);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {

            @Override
            public void onValueChange(NumberPicker picker, int oldValue, int newValue)
            {
                remindIn = newValue;
            }
        });

    }
}
