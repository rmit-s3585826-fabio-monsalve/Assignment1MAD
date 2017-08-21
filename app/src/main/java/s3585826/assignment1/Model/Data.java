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

/**
 * Created by Fabio Monsalve s3585826.
 */

public class Data {
    public static User user1;
    public static Friend focusFriend;
    private static final String LOG_TAG = "Friends Activity";

    public static void setUpApp(Context context) {

        Log.d(LOG_TAG, "setUpApp() START");
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
                    case "user": user1 = new User(tokens[1],  tokens[2], tokens[3], friendMap, meetingList);
                    case "friend": Friend friend = new Friend(tokens[1], tokens[2], tokens[3], null);
                        user1.getFriendMap().put(tokens[1], friend);
                }
            }
        }catch(IOException e){
            System.out.println("File not found");
        }

        Log.d(LOG_TAG, "setUpApp() END");
    }
}
