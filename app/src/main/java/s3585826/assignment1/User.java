package s3585826.assignment1;

import java.util.ArrayList;

public class User {
    private String id;
    private String name;
    private String email;

    private ArrayList<Friend> friendList;
    private ArrayList<Meeting> meetingsList;

    public User (String id, String name, String email, ArrayList<Friend>
        friendList, ArrayList<Meeting> meetingList){
        this.id = id;
        this.name = name;
        this.email = email;
        this.friendList = friendList;
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

    public ArrayList<Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<Friend> friendList) {
        this.friendList = friendList;
    }

    public ArrayList<Meeting> getMeetingsList() {
        return meetingsList;
    }

    public void setMeetingsList(ArrayList<Meeting> meetingsList) {
        this.meetingsList = meetingsList;
    }
}
