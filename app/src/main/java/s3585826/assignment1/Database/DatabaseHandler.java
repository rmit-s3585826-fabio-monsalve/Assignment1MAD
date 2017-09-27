package s3585826.assignment1.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import s3585826.assignment1.Model.Friend;
import s3585826.assignment1.Model.Meeting;

/**
 * Created by Fabio Monsalve s3585826 on 27/09/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "friendTrackerDatabase.db";
    public static final String FRIENDS_TABLE = "FRIENDS";
    public static final String FRIENDS_COLUMN_ID = "FRIEND_ID";
    public static final String FRIENDS_COLUMN_NAME = "NAME";
    public static final String FRIENDS_COLUMN_EMAIL = "EMAIL";
    public static final String FRIENDS_COLUMN_BIRTHDAY = "BIRTHDAY";

    public static final String MEETINGS_TABLE = "MEETINGS";
    public static final String MEETINGS_COLUMN_ID = "MEETING_ID";
    public static final String MEETINGS_COLUMN_TITLE = "TITLE";
    public static final String MEETINGS_COLUMN_STARTTIME = "STARTTIME";
    public static final String MEETINGS_COLUMN_ENDTIME = "ENDTIME";
    public static final String MEETINGS_COLUMN_LOCATION = "LOCATION";
    public static final String MEETINGS_COLUMN_DATE = "DATE";


    public DatabaseHandler (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + FRIENDS_TABLE + "(" +
            FRIENDS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FRIENDS_COLUMN_NAME + " TEXT " +
            FRIENDS_COLUMN_EMAIL + " TEXT " + FRIENDS_COLUMN_BIRTHDAY + " TEXT " + ");";
        db.execSQL(query1);

        String query2 = "CREATE TABLE " + MEETINGS_TABLE + "(" +
            MEETINGS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MEETINGS_COLUMN_TITLE + " TEXT " +
            MEETINGS_COLUMN_STARTTIME + " TEXT " + MEETINGS_COLUMN_ENDTIME + " TEXT " + MEETINGS_COLUMN_LOCATION + " TEXT " +
            MEETINGS_COLUMN_DATE + " TEXT " + ");";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FRIENDS_TABLE);
        onCreate(db);
    }

    //Add a new row to the database
    public void addFriend(Friend friend){
        ContentValues values = new ContentValues();
        values.put(FRIENDS_COLUMN_NAME, friend.getName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(FRIENDS_TABLE, null, values);
        db.close();
    }

    public void addMeeting(Meeting meeting){
        ContentValues values = new ContentValues();
        values.put(MEETINGS_COLUMN_TITLE, meeting.getTitle());
        values.put(MEETINGS_COLUMN_ID, meeting.getId());
        values.put(MEETINGS_COLUMN_STARTTIME, meeting.getStartTime());
        values.put(MEETINGS_COLUMN_ENDTIME, meeting.getEndTime());
        values.put(MEETINGS_COLUMN_LOCATION, meeting.getLocationString());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(MEETINGS_TABLE, null, values);
        db.close();
    }

    //Delete a product from the database
    public void deleteFriend(String friendId){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + FRIENDS_TABLE + " WHERE " + FRIENDS_COLUMN_ID + "=\"" + friendId + "\";");
    }

    public void deleteMeeting(String meetingId){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + MEETINGS_TABLE + " WHERE " + MEETINGS_COLUMN_ID + "=\"" + meetingId + "\";");
    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + FRIENDS_TABLE + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("NAME")) != null) {
                dbString += c.getString(c.getColumnIndex("NAME"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

}

/*
public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        dbHandler = new MyDBHandler(this, null, null, 1);
        printDatabase();

    }

    public void addButtonClicked(View view){
        Product product = new Product(editText.getText().toString());
        dbHandler.addProduct(product);
        printDatabase();

    }

    public void deleteButtonClicked(View view){
        String inputText = editText.getText().toString();
        dbHandler.deleteProduct(inputText);
        printDatabase();

    }

    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        textView.setText(dbString);
        editText.setText("");
    }


}
                                                                              */
