package s3585826.assignment1.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import s3585826.assignment1.Model.*;
import s3585826.assignment1.R;

/**
 * Activity for editing a friends details
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */

public class EditFriendActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friend_details);

        // Reference all relevant elements from the view
        final EditText editTextName = (EditText) findViewById(R.id.editTextName);
        final EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        final EditText editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);

        // Set the current friend details
        editTextName.setText(Model.getInstance().getFocusFriend().getName());
        editTextEmail.setText(Model.getInstance().getFocusFriend().getEmail());
        editTextBirthday.setText(Model.getInstance().getFocusFriend().getBirthday());

        // Set new details from user input
        Button editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Model.getInstance().getFocusFriend().setName(editTextName.getText().toString());
                Model.getInstance().getFocusFriend().setEmail(editTextEmail.getText().toString());
                Model.getInstance().getFocusFriend().setBirthday(editTextBirthday.getText().toString());

                // Back to FriendInfo
                Intent intent = new Intent(EditFriendActivity.this, FriendInfoActivity.class);
                EditFriendActivity.this.startActivity(intent);
            }
        });
    }
}
