package coe1530.eatyourleftovers;

import android.Manifest;
import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.google.api.services.calendar.model.Event;

import java.net.ConnectException;
import java.util.List;

public class CalendarFragment extends Fragment implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    // Calendar View
    CalendarView mCalendarView;

    // List of Events from the Google Calendar API
    List<Event> mListOfEvents;

    // Permissions Variables
    private static final int REQ_READ_CALENDAR = 1;
    private static final int REQ_WRITE_CALENDAR = 2;
    private static final String TAG = "CalendarPermissions";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // Get the Calendar View and add the on click listener to it
        mCalendarView = (CalendarView) view.findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Log.w("Date info: ", month + "/" + dayOfMonth + "/" + year);
                CalendarHandler.printEvents(getContext(), month+1, dayOfMonth, year);
            }
        });

        // Gets calendar permissions.... maybe better to put this in the bottom navigation listener
        Button updateCalendarButton = view.findViewById(R.id.updateCalendarButton);
        updateCalendarButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            // If you click the update calendar button
            case R.id.updateCalendarButton:
                Log.w("ClickipooMagoo", "This button was clicked.");
                try {
                    // Request permission if not already granted
                    getCalendarData();
                } catch (Exception e) {
                    Log.w("ListException", e);
                }
                break;
        }
    }

    private void getCalendarData() {
        Context context = getContext();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR}, REQ_READ_CALENDAR);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQ_READ_CALENDAR
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getDataFromCalendarTable();
        }
    }

    // Test query for after calendar data has been granted permission with the update calendar button.
    public void getDataFromCalendarTable() {
        Cursor cur = null;
        Context context = getContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String[] selectionArgs = new String[]{"apaczak2@gmail.com", "apaczak2.gmail.com"};
        cur = cr.query(uri, null, null, selectionArgs, null);
        while (cur.moveToNext()) {
            String displayName = cur.getString(cur.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME));
            Log.w(TAG, displayName);
        }
    }

}
