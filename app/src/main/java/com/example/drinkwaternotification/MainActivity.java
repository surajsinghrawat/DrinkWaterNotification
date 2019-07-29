package com.example.drinkwaternotification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView incrementCount;

    RadioGroup radioGroup;
    RadioButton radioButton;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        incrementCount=findViewById(R.id.show_text);


        radioGroup=findViewById(R.id.rediogroup);


        updateWaterCount();



        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);



    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void testNotification(View view) {
//        NotificationUtilities.remindUserNotification(this);
//
//
//    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (PreferenceUtilities.KEY_WATER_COUNT.equals(key)) {
            updateWaterCount();
        }
    }

    private void updateWaterCount() {
        int waterCount = PreferenceUtilities.getWaterCount(this);
        incrementCount.setText(waterCount+"");
    }

    public void incrementWater(View view) {


        Intent incrementWaterCountIntent = new Intent(this, WaterReminderIntentService.class);

        incrementWaterCountIntent.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);

        startService(incrementWaterCountIntent);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }





    @RequiresApi(api = Build.VERSION_CODES.N)
    public void click(View view) {

        int radioId =radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);
        Toast.makeText(MainActivity.this,"Click",Toast.LENGTH_LONG).show();
        Calendar calendar = Calendar.getInstance();


        Intent intent=new Intent(getApplicationContext(),NotificationUtilities.class);

        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),100,
                intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_FIFTEEN_MINUTES,pendingIntent);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void clicked(View view) {

        int radioId =radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);
        Toast.makeText(MainActivity.this,"Remainder Set",Toast.LENGTH_LONG).show();
        Calendar calendar = Calendar.getInstance();


        Intent intent=new Intent(getApplicationContext(),NotificationUtilities.class);

        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),100,
                intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_HALF_HOUR,pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void clickable(View view) {

        int radioId =radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);
        Toast.makeText(MainActivity.this,"Click",Toast.LENGTH_LONG).show();
        Calendar calendar = Calendar.getInstance();


        Intent intent=new Intent(getApplicationContext(),NotificationUtilities.class);

        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),100,
                intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_HOUR,pendingIntent);
    }
}
