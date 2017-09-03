package s3585826.assignment1.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class User extends Person{

    private HashMap<String, Friend> friends;
    private HashMap<String,Meeting> meetings;
    private ArrayList<Friend> freinfds1;

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

    public void sortMeetingsByTimeAscending(){
        //Collections.sort(meetings.values(),
           // (o1, o2) -> meetings.get(o1).getStartTime().compareTo(meetings.get(o2).getStartTime()));
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

