package s3585826.assignment1.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * User class
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class User extends Person{

    private HashMap<String, Friend> friends;
    private HashMap<String,Meeting> meetings;
    private static final String LOG_TAG = "User class";

    // User constructor
    public User(String id, String name, String email, String birthday) {
        super(id, name, email, birthday);
        this.friends = new HashMap<>();
        this.meetings = new HashMap<>();
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

    // Method to sort Meetings by time
    public ArrayList<String> sortMeetingsByTimeAscending(){
        ArrayList<Meeting> meetingsToSort = new ArrayList<Meeting>(meetings.values());

        ArrayList<String> sortedMeetings = new ArrayList<>();

        Collections.sort(meetingsToSort, new Comparator<Meeting>() {

            public int compare(Meeting o1, Meeting o2) {
                return Integer.parseInt(o1.getStartTime()) - Integer.parseInt(o2.getStartTime());
            }
        });

        for(Meeting m : meetings.values()){
            sortedMeetings.add(m.getTitle());
        }

        for (String s : sortedMeetings) {
            Log.d(LOG_TAG, s);
        }
        return sortedMeetings;
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

}

