package s3585826.assignment1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import s3585826.assignment1.Model.Data;

/**
 * Created by Fabio Monsalve s3585826.
 */

public class UserInfo extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_user_info, container, false);

        TextView userInfoId = view.findViewById(R.id.userInfoId);
        TextView userInfoName = view.findViewById(R.id.userInfoName);
        TextView userInfoEmail = view.findViewById(R.id.userInfoEmail);
        TextView userInfoFriendCount = view.findViewById(R.id.userInfoFriendCount);

        userInfoId.setText(Data.user1.getId());
        userInfoName.setText(Data.user1.getName());
        userInfoEmail.setText(Data.user1.getEmail());
        userInfoFriendCount.setText(Integer.toString(Data.user1.getFriendMap().size()));

        return view;
    }
}
