package s3585826.assignment1.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import s3585826.assignment1.Model.Meeting;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;

/**
 * Created by Callum on 29/09/2017.
 */

public class MapsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, mapFragment).commit();
        }

        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    if (googleMap != null) {
                        LatLng location;
                        //display friends location with pin
                        if (Model.getInstance().getUser().getMeetings()!=null) {
                            for(Meeting meeting:Model.getInstance().getUser().getMeetings().values()){
                                location = new LatLng(meeting.getLocation().getLatitude(), meeting.getLocation().getLongitude());
                                googleMap.addMarker(new MarkerOptions().position(location)).setTitle(meeting.getTitle());
                            }
                        }
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-37.81,144.96),12.0f));
                    }
                }
            });
        }
        return view;
    }
}
