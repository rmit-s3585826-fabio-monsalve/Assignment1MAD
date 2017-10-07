package s3585826.assignment1.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import s3585826.assignment1.Database.DatabaseHandler;
import s3585826.assignment1.R;

public class DatabaseTester extends AppCompatActivity {

    TextView textView;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_tester);
        databaseHandler = new DatabaseHandler(this, null, null, 1);
        textView = (TextView) findViewById(R.id.textView);

        Button namesButton = (Button) findViewById(R.id.namesButton);
        namesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printDatabase();
            }
        });

        Button deleteButton = (Button) findViewById(R.id.namesButton);
        namesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printDatabase();
            }
        });
    }

    public void printDatabase(){
        String dbString = databaseHandler.databaseToStringMeetings();
        textView.setText(dbString);
    }

}
