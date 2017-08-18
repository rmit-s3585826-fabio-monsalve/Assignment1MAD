package s3585826.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import s3585826.assignment1.Model.Data;

public class EditFriendDetails extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friend_details);


        final EditText editTextId = (EditText) findViewById(R.id.editTextId);
        final EditText editTextName = (EditText) findViewById(R.id.editTextName);
        final EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        final EditText editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);

        editTextId.setText(Data.focusFriend.getId());
        editTextName.setText(Data.focusFriend.getName());
        editTextEmail.setText(Data.focusFriend.getEmail());
        editTextBirthday.setText(Data.focusFriend.getBirthday());

        Button editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.focusFriend.setId(editTextId.getText().toString());
                Data.focusFriend.setName(editTextName.getText().toString());
                Data.focusFriend.setEmail(editTextEmail.getText().toString());
                Data.focusFriend.setBirthday(editTextBirthday.getText().toString());

                Intent intent = new Intent(EditFriendDetails.this, FriendInfo.class);
                startActivity(intent);
            }
        });
    }
}
