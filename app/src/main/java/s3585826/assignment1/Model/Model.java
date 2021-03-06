package s3585826.assignment1.Model;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Model class
 * @authors Fabio Monsalve s3585826 and Callum Pearse s3586928
 */
public class Model {

    //Private static variable of the same class that is the only instance of the class.
    private static final String LOG_TAG = "Model";
    private static Model instance;
    private Boolean isConnected;
    private static boolean meetingFocus;
    private static User user1;
    private static Friend focusFriend;
    private static Meeting focusMeeting;
    public static boolean firstTimeMain = true;
    public static int friendId = 0;
    public static int meetingId = 0;

    public static boolean isSettingsChanged() {
        return settingsChanged;
    }

    public static void setSettingsChanged(boolean settingsChanged) {
        Model.settingsChanged = settingsChanged;
    }

    public static boolean settingsChanged = false;


    //Private constructor to restrict instantiation of the class from other classes.
    private Model(){

    }

    //Returns the singleton instance
    public static Model getInstance(){
        if(instance == null){
            instance = new Model();
        }
        return instance;
    }

    public static boolean isMeetingFocus() {
        return meetingFocus;
    }

    public static void setMeetingFocus(boolean meetingFocus) {
        Model.meetingFocus = meetingFocus;
    }

    //return user
    public User getUser(){
        return user1;
    }

    //return friend in focus
    public Friend getFocusFriend(){
        return focusFriend;
    }

    //set friend in focus
    public void setFocusFriend(Friend f){
        focusFriend = f;
    }

    public Meeting getFocusMeeting(){
        return focusMeeting;
    }

    //set friend in focus
    public void setFocusMeeting(Meeting m){
        focusMeeting = m;
    }

    public Meeting getNewMeeting(){
        return new Meeting();
    }

    // Load dummy data from dummy_data.txt
    public void loadDummyData(Context context) {

        Log.d(LOG_TAG, "loadDummyData() START");
        String[] tokens;
        String line;

        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open("dummy_data.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            while ((line = br.readLine()) != null) {
                tokens = line.split(",");

                switch(tokens[0]){
                    case "user":
                        user1 = new User(tokens[1],  tokens[2], tokens[3], null);
                        break;
                    case "notificationPeriod":
                        Model.getInstance().getUser().setReminderPeriod(Integer.parseInt(tokens[1]));
                        break;
                    case "suggestionFrequency":
                        Model.getInstance().getUser().setSuggestionInterval(Integer.parseInt(tokens[1]));
                }
            }
        }catch(IOException e){
            System.out.println("File not found");
        }

        Log.d(LOG_TAG, "loadDummyData() END");
    }

    public void incrementMeetingId(){

        meetingId++;
    }

    public void incrementFriendId(){

        friendId++;
    }

    public static int getFriendId() {
        int min = 0;
        int max = 1000;
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return friendId + randomNum;
    }

    public static int getMeetingId() {
        int min = 0;
        int max = 1000;
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return meetingId + randomNum;
    }

    public Boolean getConnected() {
        return isConnected;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }
}