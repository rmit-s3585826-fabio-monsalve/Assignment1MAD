package s3585826.assignment1.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String id;
    private String name;
    private String email;
    private HashMap<String, Friend> friendMap;
    private ArrayList<Meeting> meetingsList;

    public User (String id, String name, String email,
                 HashMap<String, Friend> friendMap, ArrayList<Meeting> meetingList){
        this.id = id;
        this.name = name;
        this.email = email;
        this.friendMap = friendMap;
        this.meetingsList = meetingList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Meeting> getMeetingsList() {
        return meetingsList;
    }

    public void setMeetingsList(ArrayList<Meeting> meetingsList) {
        this.meetingsList = meetingsList;
    }

    public HashMap<String, Friend> getFriendMap() {
        return friendMap;
    }

    public void setFriendMap(HashMap<String, Friend> friendMap) {
        this.friendMap = friendMap;
    }
}
