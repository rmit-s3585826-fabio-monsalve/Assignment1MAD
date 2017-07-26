package s3585826.assignment1.Model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import s3585826.assignment1.R;

public class Friends extends Fragment{

    private ContactDataManager cdm;
    private View view;
    private int friendCount = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friends,container,false);

        if(friendCount == 0) {
            TextView ft1 = (TextView) view.findViewById(R.id.ft1);
            ft1.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "You have no friends", Toast.LENGTH_LONG).show();
        }else{
            //TODO
        }
        return view;
    }

}