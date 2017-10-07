package s3585826.assignment1.Support_Code;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import s3585826.assignment1.Model.Location;

/**
 * Created by Callum on 3/10/2017.
 */

public class WalkTimeCalculator extends AsyncTask<Void,Void,JSONObject> {

    private static final String LOG_TAG = WalkTimeCalculator.class.getName();
    private static final String APIkey = "AIzaSyC3_tT8vVyQ-yKGbR7RGPobs2IqlNj4lgs";
    private URL url;
    private HttpURLConnection connection;
    private Location userLocation;
    private Location friendLocation;
    private Location destination;
    private JSONObject json;

    public WalkTimeCalculator(Location userLocation, Location friendLocation){
        Log.i(LOG_TAG, "in constructor");
        this.userLocation=userLocation;
        this.friendLocation=friendLocation;
        this.destination = new Location((userLocation.getLatitude()+friendLocation.getLatitude())/2,
                (userLocation.getLongitude()+friendLocation.getLongitude())/2);
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        Log.i(LOG_TAG, "doInBackground()");

        try {
            url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&mode=walking"
                    +"&origins="+userLocation.getLatitude()+","+userLocation.getLongitude()+"%7C"+friendLocation.getLatitude()+","+friendLocation.getLongitude()
                    +"&destinations="+destination.getLatitude()+","+destination.getLongitude()
                    +"&key="+APIkey);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // retrieve content from URLconnection
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder contentSB = new StringBuilder();
            while ((line = br.readLine()) != null) {
                contentSB.append(line + "\n");
            }
            json = new JSONObject(contentSB.toString());

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
