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

        Button button1 = (Button)findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           Model.getInstance().getUser().setReminderPeriodAfterNotification(remindIn);
                                           startActivity(new Intent(MeetingReminderActivity.this,MainActivity.class));
                                       }
                                   }
        );


        NumberPicker np = (NumberPicker)findViewById(R.id.numberPicker);
        np.setMinValue(1);
        np.setMaxValue(30);
        np.setWrapSelectorWheel(true);

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                remindIn = newVal;

            }
        });

    }
}
