package s3585826.assignment1.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import s3585826.assignment1.Activities.MainActivity;
import s3585826.assignment1.Activities.MeetingInfoActivity;
import s3585826.assignment1.Model.Model;
import s3585826.assignment1.R;

/**
 * Created by Fabio Monsalve s3585826 on 7/10/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, MeetingInfoActivity.class);
        Intent dismissIntent = new Intent(context, MainActivity.class);
        dismissIntent.setAction("DISMISS");
        Intent cancelIntent = new Intent(context, MainActivity.class);
        cancelIntent.setAction("CANCEL");
        Intent remindIntent = new Intent(context, MainActivity.class);
        remindIntent.setAction("REMIND");

        // Intent for clicking on notification
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MeetingInfoActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Intent for dismissing
        TaskStackBuilder stackBuilder2 = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(dismissIntent);

        PendingIntent dismissingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Intent for cancelling
        TaskStackBuilder stackBuilder3 = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(cancelIntent);

        PendingIntent cancellingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Intent for reminding
        TaskStackBuilder stackBuilder4 = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(remindIntent);

        PendingIntent remindingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.setContentTitle(Model.getInstance().getFocusMeeting().getTitle() + " is upcoming!")
            .setContentText("Start time: " + Model.getInstance().getFocusMeeting().getStartTime() + " End Time: "
            + Model.getInstance().getFocusMeeting().getEndTime() + " Location: " + Model.getInstance().getFocusMeeting().getLocationString())
            .setTicker("New Message Alert!")
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .addAction(1, "Dismiss", dismissingIntent)
            .addAction(2, "Cancel", cancellingIntent)
            .addAction(3, "Remind", remindingIntent)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}