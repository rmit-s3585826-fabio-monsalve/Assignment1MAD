package s3585826.assignment1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;

/**
 * Created by Callum on 29/09/2017.
 */

public class SettingsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings);

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //set editText fields and pre-populate from model
        final EditText suggestionInterval = (EditText) findViewById(R.id.suggestion_interval);
        suggestionInterval.setText(Integer.toString(Model.getInstance().getUser().getSuggestionInterval()));
        final EditText reminderPeriod = (EditText) findViewById(R.id.reminder_period);
        reminderPeriod.setText(Integer.toString(Model.getInstance().getUser().getReminderPeriod()));

        // handle save button
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update Model
                Model.getInstance().getUser().setSuggestionInterval(Integer.parseInt(suggestionInterval.getText().toString()));
                Model.getInstance().getUser().setReminderPeriod(Integer.parseInt(reminderPeriod.getText().toString()));

                // Back to Main Activity
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}