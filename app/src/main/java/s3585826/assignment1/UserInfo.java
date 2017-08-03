package s3585826.assignment1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserInfo extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_user_info, container, false);

        TextView userInfoId = view.findViewById(R.id.userInfoId);
        TextView userInfoName = view.findViewById(R.id.userInfoName);
        TextView userInfoEmail = view.findViewById(R.id.userInfoEmail);

        userInfoId.setText(MainActivity.user1.getId());
        userInfoName.setText(MainActivity.user1.getName());
        userInfoEmail.setText(MainActivity.user1.getEmail());

        return view;
    }

}
