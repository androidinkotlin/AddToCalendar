package com.plutuscommerce.base.section_login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;

import java.util.Calendar;

/**
 * Created by Windows10 on 8/15/2017.
 */

public class AddToCalendar {
    /*
    <uses-permission android:name="android.permission.READ_CALENDAR" />
    <uses-permission android:name="android.permission.WRITE_CALENDAR" />*/
    private void checkPermissionsCal(Activity activity){
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            if ((ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CALENDAR)!= PackageManager.PERMISSION_GRANTED)
                    || (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CALENDAR)!= PackageManager.PERMISSION_GRANTED)){
                // Check Permissions Now
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_CALENDAR,Manifest.permission.READ_CALENDAR},45);
            } else {
                // permission has been granted, continue as usual
                addToCal(activity);
            }

        } else {
            // Pre-Marshmallow
            addToCal(activity);
        }
    }
    private void addToCal(Activity activity){
        Calendar cal = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= 14) {
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis()+3600000)
                    .putExtra(CalendarContract.Events.TITLE, "Yoga")
                    .putExtra(CalendarContract.Events.DESCRIPTION, "Group class")
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                    .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
            activity.startActivity(intent);
        }

        else {
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra("beginTime", cal.getTimeInMillis());
            intent.putExtra("allDay", true);
            intent.putExtra("rrule", "FREQ=YEARLY");
            intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
            intent.putExtra("title", "A Test Event from android app");
            activity.startActivity(intent);
        }
    }
}
