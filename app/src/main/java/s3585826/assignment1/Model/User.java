package s3585826.assignment1.Model;

import java.util.HashMap;

public class User extends Person{

    private HashMap<String, Friend> friends;
    private HashMap<String,Meeting> meetings;

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

    public Meeting getMeeting(String id){
        return meetings.get(id);
    }

    public void addMeeting(Meeting meeting) {
        meetings.put(meeting.getId(), meeting);
    }

    public void removeMeeting(String id){
        meetings.remove(id);
    }

    public Friend getFriendById(String id){
        return friends.get(id);
    }

    public Friend getFriendByName(String name){

        Friend friend = null;
        for(Friend e : friends.values()) {
            if (e.getName().equals(name)) {
                friend = e;
                return friend;
            }
        }
        return friend;
    }

    public void addFriend(Friend friend) {
        friends.put(friend.getId(), friend);
    }

    public void removeFriend(String id){
        friends.remove(id);
    }

}

