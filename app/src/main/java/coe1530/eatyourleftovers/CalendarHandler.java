package coe1530.eatyourleftovers;

// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

public class CalendarHandler {
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final int REQ_READ_CALENDAR = 1;
    private static final int REQ_WRITE_CALENDAR = 2;
    private static final String TAG = "CalendarPermissions";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";


    public static void getDataFromCalendarTable(Context context) {
        Cursor cur = null;
        ContentResolver cr = context.getContentResolver();

        //requestCalendarReadPermission(context, activity);

        String[] mProjection =
                {
                        CalendarContract.Calendars.ALLOWED_ATTENDEE_TYPES,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                        CalendarContract.Calendars.CALENDAR_LOCATION,
                        CalendarContract.Calendars.CALENDAR_TIME_ZONE
                };

        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("+ CalendarContract.Calendars.ACCOUNT_TYPE + " = ?))";
        String[] selectionArgs = new String[]{"apaczak2@gmail.com", "apaczak2.gmail.com"};

        cur = cr.query(uri, mProjection, selection, selectionArgs, null);
        Log.w("SUCCESS", selectionArgs[0] + " and " + cur);

        while (cur.moveToNext()) {
            String displayName = cur.getString(cur.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME));
            String accountName = cur.getString(cur.getColumnIndex(CalendarContract.Calendars.ACCOUNT_NAME));

            Log.w(TAG, displayName);
        }
        //addEvent(v);

    }

    public static void printEventsFromDate(Context context, long today, long tomorrow) {
        Cursor cur = null;
        ContentResolver cr = context.getContentResolver();

        Log.w("TOMORROW AND TODAY", "today: " + today + " tomorrow: " + tomorrow);

        //requestCalendarReadPermission(context, activity);

        /*
        String[] mProjection =
                {
                        CalendarContract.Events.TITLE,
                        CalendarContract.Events.EVENT_LOCATION,
                        CalendarContract.Events.DTSTART,
                        CalendarContract.Events.DTEND
                };
        */

        Uri uri = CalendarContract.Events.CONTENT_URI;
        /*
        String selection = "((" + CalendarContract.Events.TITLE + " = ?) " +
                "AND ("+ CalendarContract.Events.EVENT_LOCATION + " = ?) " +
                "AND ("+ CalendarContract.Events.DTSTART + " = ?) " +
                "AND ("+ CalendarContract.Events.DTEND + " = ?))";
        */
        String[] selectionArgs = new String[]{"apaczak2@gmail.com", "apaczak2.gmail.com"};

        cur = cr.query(uri, null, null, null, null);
        Log.w("SUCCESS", selectionArgs[0] + " and " + cur);

        cur.moveToFirst();
        while (cur.moveToNext()) {
            String eventName = cur.getString(cur.getColumnIndex(CalendarContract.Events.TITLE));
            String loc = cur.getString(cur.getColumnIndex(CalendarContract.Events.EVENT_LOCATION));
            String stime = cur.getString(cur.getColumnIndex(CalendarContract.Events.DTSTART));
            String etime = cur.getString(cur.getColumnIndex(CalendarContract.Events.DTEND));
            String duration = cur.getString(cur.getColumnIndex(CalendarContract.Events.DURATION));


            long stime_long = Long.parseLong(stime);
            stime = formatDate(stime_long);
            if (stime_long < tomorrow && stime_long > today){
                Log.w("EVENTS", eventName + "\t" + loc + "\t" + stime + "\t" + etime + "\t" + duration);
            }




        }

    }

    public static boolean todaysEvent(long epoch) {
        boolean happensToday = false;

        return happensToday;
    }

    public static String formatDate(long epoch) {
        Date date = new Date(epoch);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        String formatted = format.format(date);
        return formatted;
    }


    /**
     * Requests the Calendar READ Permission
     */
    private static void requestCalendarReadPermission(Context context, Activity activity) {
        //Log.i("CalendarRead Permission", "READ_CALENDAR permission has NOT been granted. Requesting permission.");

        if (ActivityCompat.checkSelfPermission(context, "android.permission.READ_CALENDAR") != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{"android.permission.READ_CALENDAR"}, REQ_READ_CALENDAR);
        } else {
            // Request it directly.
            ActivityCompat.requestPermissions(activity, new String[]{"android.permission.READ_CALENDAR"}, REQ_READ_CALENDAR);
        }
    }

    public static void addEvent(View view) {

        Context context = view.getContext();
        Activity activity = (Activity) context;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_CALENDAR}, REQ_WRITE_CALENDAR);
        }

        ContentResolver cr = context.getContentResolver();
        ContentValues contentValues = new ContentValues();

        java.util.Calendar beginTime = java.util.Calendar.getInstance();
        beginTime.set(2017, 02, 04, 9, 30);

        java.util.Calendar endTime = java.util.Calendar.getInstance();
        endTime.set(2017, 4, 4, 7, 35);

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
        values.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
        values.put(CalendarContract.Events.TITLE, "Tech Stores");
        values.put(CalendarContract.Events.DESCRIPTION, "Successful Startups");
        values.put(CalendarContract.Events.CALENDAR_ID, 2);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "Europe/London");
        values.put(CalendarContract.Events.EVENT_LOCATION, "London");
        values.put(CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS, "1");
        values.put(CalendarContract.Events.GUESTS_CAN_SEE_GUESTS, "1");

        cr.insert(CalendarContract.Events.CONTENT_URI, values);
        //Log.w("cr insert: ", values);
    }

    /**
     * Callback received when a permissions request has been completed.
     */

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQ_READ_CALENDAR) {

            Log.i(TAG, "Received response for Calendar Read permission request.");

            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Calendar read permission has been granted, preview can be displayed
                Log.i(TAG, "Calendar Read permission has now been granted.");
            } else {
                Log.i(TAG, "Calendar Read permission was NOT granted.");
            }
            // END_INCLUDE(permission_result)

        }
    }

}
// [END calendar_handler]