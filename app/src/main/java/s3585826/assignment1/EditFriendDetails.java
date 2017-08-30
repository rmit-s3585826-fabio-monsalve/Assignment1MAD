package s3585826.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import s3585826.assignment1.Model.*;

/**
 * Created by Fabio Monsalve s3585826.
 */

public class EditFriendDetails extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friend_details);


        final EditText editTextId = (EditText) findViewById(R.id.editTextId);
        final EditText editTextName = (EditText) findViewById(R.id.editTextName);
        final EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        final EditText editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);

        editTextId.setText(Model.getInstance().getFocusFriend().getId());
        editTextName.setText(Model.getInstance().getFocusFriend().getName());
        editTextEmail.setText(Model.getInstance().getFocusFriend().getEmail());
        editTextBirthday.setText(Model.getInstance().getFocusFriend().getBirthday());

        Button editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.getInstance().getFocusFriend().setId(editTextId.getText().toString());
                Model.getInstance().getFocusFriend().setName(editTextName.getText().toString());
                Model.getInstance().getFocusFriend().setEmail(editTextEmail.getText().toString());
                Model.getInstance().getFocusFriend().setBirthday(editTextBirthday.getText().toString());

                Intent intent = new Intent(EditFriendDetails.this, FriendInfo.class);
                startActivity(intent);
            }
        });
    }
}
