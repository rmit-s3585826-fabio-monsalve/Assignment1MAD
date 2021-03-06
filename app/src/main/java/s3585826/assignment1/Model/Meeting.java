package s3585826.assignment1.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class for meeting
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class Meeting {
    private String id;
    private String title;
    private String startTime;
    private String endTime;
    private String[] invitedFriends;
    private Location location;
    private String date;
    private int combinedWalktime;

    public Meeting(String id, String title, String startTime, String endTime, String date, String [] invitedFriends, Location location) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.invitedFriends = invitedFriends;
        this.location = location;
    }

    @Override
    public String toString() {
        return title;
    }

    public Meeting(){

    }

    // Method to get invited friends to a simple String Array List
    public ArrayList<String> getAttendeesAsStringArrayList(){

        ArrayList<String> attendeesArrayList = new ArrayList<>();
        for(int i = 0; i < invitedFriends.length; i++){
            attendeesArrayList.add(invitedFriends[i]);
        }
        return attendeesArrayList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public String [] getInvitedFriends() {
        return invitedFriends;
    }

    public void setInvitedFriends(String [] invitedFriends) {
        this.invitedFriends = invitedFriends;
    }

    public String getInvitedFriendsAsString(){
        String concatenatedFriendsString = "";
        for(int i = 0; i<invitedFriends.length; i++){
            concatenatedFriendsString = concatenatedFriendsString + "," +  invitedFriends[i];
        }
        return concatenatedFriendsString;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLocationString() {
        if (location==null)
            return "Unknown";
        else
            return location.toString();
    }

    public Date getFormattedDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        formatter.setLenient(false);
        Date meetingDate = new Date();
        try {
            meetingDate = formatter.parse(date + ", " + startTime + ":00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return meetingDate;
    }

    public int getCombinedWalktime() {
        return combinedWalktime;
    }

    public void setCombinedWalktime(int combinedWalktime) {
        this.combinedWalktime = combinedWalktime;
    }
}


