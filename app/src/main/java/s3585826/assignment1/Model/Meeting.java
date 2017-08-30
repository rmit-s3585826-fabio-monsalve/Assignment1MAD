package s3585826.assignment1.Model;

import java.util.ArrayList;

/**
 * Created by Fabio Monsalve s3585826.
 */

public class Meeting {
    private String id;
    private String title;
    private String startTime;
    private String endTime;
    private ArrayList<Friend> invitedFriends;
    private Location location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ArrayList<Friend> getInvitedFriends() {
        return invitedFriends;
    }

    public void setInvitedFriends(ArrayList<Friend> invitedFriends) {
        this.invitedFriends = invitedFriends;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}


