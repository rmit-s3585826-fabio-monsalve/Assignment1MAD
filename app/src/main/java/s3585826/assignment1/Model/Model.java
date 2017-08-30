package s3585826.assignment1.Model;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Model {

    //Private static variable of the same class that is the only instance of the class.
    private static Model instance;
    private static User user1;
    private static Friend focusFriend;
    private static final String LOG_TAG = "Friends Activity";
    public static boolean firstTimeMain = true;

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
        focusFriend=f;
    }


    // Load dummy data from dummy_data.txt
    public void loadDummyData(Context context) {

        Log.d(LOG_TAG, "loadDummyData() START");
        String[] tokens;
        String line;
        ArrayList<Meeting> meetingList = new ArrayList<>();
        HashMap<String, Friend> friendMap = new HashMap<>();

        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open("dummy_data.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            while ((line = br.readLine()) != null) {
                tokens = line.split(",");

                Log.d(LOG_TAG, "switch" + tokens[0] + tokens[1] + tokens[2]);

                switch(tokens[0]){
                    case "user":
                        user1 = new User(tokens[1],  tokens[2], tokens[3], null);
                    case "friend":
                        Friend friend = new Friend(tokens[1], tokens[2], tokens[3], null);
                        user1.addFriend(friend);
                }
            }
        }catch(IOException e){
            System.out.println("File not found");
        }

        Log.d(LOG_TAG, "loadDummyData() END");
    }
}
