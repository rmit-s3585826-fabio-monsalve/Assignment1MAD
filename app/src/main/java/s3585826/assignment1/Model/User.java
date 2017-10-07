package s3585826.assignment1.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
        this.suggestionInterval = 1;
        this.reminderPeriod = 1;
    }

    public HashMap<String, Friend> getFriends() {
        return friends;
    }

    public HashMap<String, Meeting> getMeetings() {
        return meetings;
    }

    public long getReminderPeriodAsMilliseconds(){
        return TimeUnit.MINUTES.toMillis(reminderPeriod);
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
                Log.d(LOG_TAG, m1.getStartTime() + " " + m2.getEndTime());
                String[] m1Tokens;
                m1Tokens = m1.getStartTime().split(":");
                String startTime1Formatted = m1Tokens[0] + m1Tokens[1];
                String[] m2Tokens;
                m2Tokens = m2.getStartTime().split(":");
                String startTime2Formatted = m2Tokens[0] + m2Tokens[1];

                return Integer.compare(Integer.parseInt(startTime1Formatted),Integer.parseInt(startTime2Formatted));
            }
        });
        return sortMeetings;
    }

    // Create a list of suggested meetings sorted on walking time ascending
    public ArrayList<Meeting> generateSuggestedMeetings(){

        suggestedMeetings.clear();
        for (Friend friend : friends.values()) {
            if (friend.getLocation()!=null){

                Meeting meeting = new Meeting();
                String[] attendees = {friend.getName()};

                //setup suggested meeting
                meeting.setId(Integer.toString(Model.getMeetingId()));
                meeting.setTitle("Meeting with "+friend.getName());
                meeting.setDate("2017-3-27");
                meeting.setInvitedFriends(attendees);
                meeting.setStartTime("13:00");
                meeting.setEndTime("14:00");
                meeting.setLocation(friend.getLocation());

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

