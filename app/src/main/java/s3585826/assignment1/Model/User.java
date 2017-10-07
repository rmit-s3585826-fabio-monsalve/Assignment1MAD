package s3585826.assignment1.Model;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import s3585826.assignment1.Support_Code.WalkTimeCalculator;

/**
 * User class
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class User extends Person{

    private HashMap<String, Friend> friends;
    private HashMap<String, Meeting> meetings;
    private ArrayList<Meeting> suggestedMeetings;
    private int suggestionInterval;
    private int reminderPeriod;
    private static final String LOG_TAG = "User class";

    // User constructor
    public User(String id, String name, String email, String birthday) {
        super(id, name, email, birthday);
        this.friends = new HashMap<>();
        this.meetings = new HashMap<>();
        this.suggestedMeetings = new ArrayList<>();
        this.suggestionInterval=5;
        this.reminderPeriod=5;
    }

    public HashMap<String, Friend> getFriends() {
        return friends;
    }

    public HashMap<String, Meeting> getMeetings() {
        return meetings;
    }


    public String [] getfriendsStringArray(){
        String [] friendsArray = new String [Model.getInstance().getUser().getFriends().size()];
        int i = 0;
        for(Friend e : Model.getInstance().getUser().getFriends().values()){
            friendsArray[i] = e.getName();
        }
        return friendsArray;
    }


    public Meeting getMeeting(String id){
        return meetings.get(id);
    }

    // Method to sort Meetings by 24hr time
    public ArrayList<Meeting> sortMeetingsByTimeAscending(){
        ArrayList<Meeting> sortMeetings = new ArrayList<>(meetings.values());
        Collections.sort(sortMeetings, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting m1, Meeting m2) {
                return Integer.compare(Integer.parseInt(m1.getStartTime()),Integer.parseInt(m2.getStartTime()));
            }
        });
        return sortMeetings;
    }

    // Create a list of suggested meetings sorted on walking time ascending
    public ArrayList<Meeting> generateSuggestedMeetings(){

        suggestedMeetings.clear();
        for (Friend friend : friends.values()) {
            if (friend.getLocation()!=null){

                //get JSONObject
                Location userLocation =Model.getInstance().getUser().getLocation();
                Location friendLocation=friend.getLocation();
                WalkTimeCalculator walkTimeCalculator = new WalkTimeCalculator(userLocation,friendLocation);
                JSONObject json;
                try {
                    json = walkTimeCalculator.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return suggestedMeetings;
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    return suggestedMeetings;
                }

                //setup required values
                String[] attendees = {friend.getName()};

                //setup suggested meeting
                Meeting meeting = new Meeting();

                meeting.setId(Integer.toString(Model.getMeetingId()));
                meeting.setTitle("Meeting with "+friend.getName());
                meeting.setInvitedFriends(attendees);

                // defualt startTime is currentTime plus larger of attendee walktimes
                int seconds;
                int userSeconds;
                int friendSeconds;
                try {
                    userSeconds = json.getJSONArray("rows")
                            .getJSONObject(0)
                            .getJSONArray ("elements")
                            .getJSONObject(0)
                            .getJSONObject("duration")
                            .getInt("value");
                    friendSeconds = json.getJSONArray("rows")
                            .getJSONObject(1)
                            .getJSONArray ("elements")
                            .getJSONObject(0)
                            .getJSONObject("duration")
                            .getInt("value");
                    if (userSeconds>friendSeconds)
                        seconds=userSeconds;
                    else
                        seconds=friendSeconds;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return suggestedMeetings;
                }
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND,seconds);
                meeting.setStartTime(cal.getTime().toString());

                // default endtime is startTime +1hr
                cal.add(Calendar.HOUR_OF_DAY, 1);
                meeting.setEndTime(cal.getTime().toString());


                meeting.setLocation(new Location((userLocation.getLatitude()+friendLocation.getLatitude())/2,
                        (userLocation.getLongitude()+friendLocation.getLongitude())/2));

                suggestedMeetings.add(meeting);
            }
            //sort meetings on combined distance here
        }
        return suggestedMeetings;
    }

    public void addMeeting(Meeting meeting) {
        meetings.put(meeting.getId(), meeting);
    }

    public void removeMeeting(String id){
        meetings.remove(id);
    }

    public Friend getFriend(String id){
        return friends.get(id);
    }

    public void addFriend(Friend friend) {
        friends.put(friend.getId(), friend);
    }

    public void removeFriend(String id){
        friends.remove(id);
    }

    public int getSuggestionInterval() {
        return suggestionInterval;
    }

    public void setSuggestionInterval(int suggestionInterval) {
        this.suggestionInterval = suggestionInterval;
    }

    public int getReminderPeriod() {
        return reminderPeriod;
    }

    public void setReminderPeriod(int reminderPeriod) {
        this.reminderPeriod = reminderPeriod;
    }

    public ArrayList<Meeting> getSuggestedMeetings() {
        return suggestedMeetings;
    }

    public void setSuggestedMeetings(ArrayList<Meeting> suggestedMeetings) {
        this.suggestedMeetings = suggestedMeetings;
    }
}

