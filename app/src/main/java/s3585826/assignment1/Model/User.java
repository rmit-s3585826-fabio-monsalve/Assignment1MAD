package s3585826.assignment1.Model;

import java.util.ArrayList;
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
        ArrayList<Friend> friends = new ArrayList<>();
        Location location = new Location(321312, 312312);

        Meeting meeting = new Meeting("4124j1l2i","Meeting 1", "9:00", "13:00", "dasda", friends, location);
        meetings.put("dfadfs", meeting);
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

