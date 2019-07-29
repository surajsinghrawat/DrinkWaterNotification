package com.example.drinkwaternotification;

import android.content.Context;

public class ReminderTasks {

    public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";
    public static final String ACTION_DISMISS ="clear-notification";

    public static void executeTask(Context context, String action) {

        if (ACTION_INCREMENT_WATER_COUNT.equals(action)) {
            incrementWaterCount(context);
        }else if (ACTION_DISMISS.equals(action)) {
           NotificationUtilities.CancleAllNotification(context);
        }
    }

    private static void incrementWaterCount(Context context) {
        PreferenceUtilities.incrementWaterCount(context);
        NotificationUtilities.CancleAllNotification(context);
    }
}
