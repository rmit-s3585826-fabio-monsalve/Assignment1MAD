package s3585826.assignment1.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;


public class MyDetailsFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_user_info, container, false);

        TextView userInfoId = view.findViewById(R.id.userInfoId);
        TextView userInfoName = view.findViewById(R.id.userInfoName);
        TextView userInfoEmail = view.findViewById(R.id.userInfoEmail);
        TextView userInfoFriendCount = view.findViewById(R.id.userInfoFriendCount);

        userInfoId.setText(Model.getInstance().getUser().getId());
        userInfoName.setText(Model.getInstance().getUser().getName());
        userInfoEmail.setText(Model.getInstance().getUser().getEmail());
        userInfoFriendCount.setText(Integer.toString(Model.getInstance().getUser().getFriends().size()));

        return view;
    }
}
