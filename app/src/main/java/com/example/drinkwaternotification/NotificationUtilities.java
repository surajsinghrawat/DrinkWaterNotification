package com.example.drinkwaternotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

public class NotificationUtilities extends BroadcastReceiver{
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent statement=new Intent(context,MainActivity.class);
        statement.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,
                                   statement,PendingIntent.FLAG_UPDATE_CURRENT);
NotificationChannel mNotify = new NotificationChannel(NOTIFICATION_CHANNEL_ID, context.getString(R.string.notification_string),
        NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(mNotify);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
               .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(context.getString(R.string.title))
                .setContentText(context.getString(R.string.text))
                .addAction(drinkAction(context))
                .addAction(ignoreNotification(context))
                .setAutoCancel(true);
                notificationManager.notify(100,builder.build());

//        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
//                .setContentIntent(pendingIntent)
//                .setSmallIcon(R.drawable.ic_stat_name)
//                .setContentTitle(context.getString(R.string.title))
//                .setContentText(context.getString(R.string.text))
//                .setAutoCancel(true);
//        notificationManager.notify(100,builder.build());


    }
    private static final int PENDING_INTENT_ID=101;
    private static final int WATER_REMINDER_NOTIFICATION_ID=333;
   private static final String NOTIFICATION_CHANNEL_ID="notification_id";
    private static final int ACTION_IGNORE_ID =777;
    private static final int ACTION_DONE_ID=1010;

    public static void CancleAllNotification(Context context){
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }


//
    private static NotificationCompat.Action ignoreNotification(Context context){
        Intent ignoreIntent=new Intent(context,WaterReminderIntentService.class);

        ignoreIntent.setAction(ReminderTasks.ACTION_DISMISS);
        PendingIntent ignorePendingIntent=PendingIntent.getService(context, ACTION_IGNORE_ID,ignoreIntent,
                                            PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action ignoreNotification=new NotificationCompat.Action(R.drawable.cancel_icon,
                                                      "No Thanks",ignorePendingIntent);
        return ignoreNotification;

    }


    private static NotificationCompat.Action drinkAction (Context context){
        Intent drinkWaterIntent=new Intent(context,WaterReminderIntentService.class);

        drinkWaterIntent.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);
        PendingIntent incrementPendingIntent=PendingIntent.getService(context, ACTION_DONE_ID,drinkWaterIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action drinkAction=new NotificationCompat.Action(R.drawable.done,
                "I did it",incrementPendingIntent);
        return drinkAction;

    }

}
