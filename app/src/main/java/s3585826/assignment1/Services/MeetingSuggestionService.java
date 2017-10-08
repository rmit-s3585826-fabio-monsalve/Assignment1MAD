package s3585826.assignment1.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;
import s3585826.assignment1.Model.Model;

/**
 * Created by Fabio Monsalve s3585826 on 5/10/17.
 */

public class MeetingSuggestionService extends Service {
    private static final String LOG_TAG = "MeetingSuggestions";
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                while(true) {
                    Log.d(LOG_TAG, "Thread asleep now..");
                    Thread.sleep(TimeUnit.SECONDS.toMillis(5));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public Thread meetingSuggestionsThread = new Thread(runnable);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy (SERVICE)");
        meetingSuggestionsThread.interrupt();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand (SERVICE)");
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        meetingSuggestionsThread.start();
        return super.onStartCommand(intent, flags, startId);
    }
}
