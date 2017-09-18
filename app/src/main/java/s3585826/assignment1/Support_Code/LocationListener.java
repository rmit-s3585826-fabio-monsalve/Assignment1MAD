package s3585826.assignment1.Support_Code;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import s3585826.assignment1.Model.Friend;
import s3585826.assignment1.Model.Location;
import s3585826.assignment1.Model.Model;


public class LocationListener implements Runnable {

    private static final String LOG_TAG = DummyLocationService.class.getName();

    private DummyLocationService dummyLocationService;
    private List<DummyLocationService.FriendLocation> matched;
    private HashMap<String,DummyLocationService.FriendLocation> locationHashMap;
    private Location location;


    public LocationListener(Context context){
        dummyLocationService = DummyLocationService.getSingletonInstance(context);
        locationHashMap = new HashMap<>();
    }

    @Override
    public void run(){
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        while (true) {

            try {
                // returns locations within 10 mins either side of 9:46:30 AM
                matched = dummyLocationService.getFriendLocationsForTime(DateFormat.getTimeInstance(
                        DateFormat.MEDIUM).parse("09:46:30 AM"), 10, 0);

                //fill hashmap with matched locations
                for (DummyLocationService.FriendLocation friendLocation:matched){
                    locationHashMap.put(friendLocation.id,friendLocation);
                }

                //update all friends locations
                for (Friend friend: Model.getInstance().getUser().getFriends().values()){
                    if (locationHashMap.containsKey(friend.getId())) {
                        location = new Location(locationHashMap.get(friend.getId()).latitude,
                                locationHashMap.get(friend.getId()).longitude);
                        friend.setLocation(location);
                    } else
                        friend.setLocation(null);
                }

                Thread.currentThread().sleep(10000);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(LOG_TAG, "Matched Query:");
            dummyLocationService.log(matched);
        }
    }
}
