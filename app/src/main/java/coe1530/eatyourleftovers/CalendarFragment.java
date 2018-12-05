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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.google.api.services.calendar.model.Event;

import java.net.ConnectException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import coe1530.eatyourleftovers.dummy.CalendarList;
import coe1530.eatyourleftovers.dummy.ToDoList;

public class CalendarFragment extends Fragment implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback  {

    // Calendar Views
    CalendarView mCalendarView;
    RecyclerView mRecyclerView;
    private MyCalendarItemRecyclerViewAdapter mAdapter;
    private CalendarFragment.OnListFragmentInteractionListener mListener;

    // List of Events from the Google Calendar API
    List<Event> mListOfEvents;

    // Permissions Variables
    private static final int REQ_READ_CALENDAR = 1;
    private static final int REQ_WRITE_CALENDAR = 2;
    private static final String TAG = "CalendarPermissions";
    private static final int seconds_in_a_day = 86400000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // Set the Recycler View for the calendar items below
        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        // Initialize the adapter and attach it to the RecyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCalendarEvents);
        Context context = view.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new MyCalendarItemRecyclerViewAdapter(CalendarList.SHOWN_ITEMS, mListener);
        mRecyclerView.setAdapter(mAdapter);

        // Get the Calendar View and add the on click listener to it
        /*
            Every time a date is clicked on in the calendar view, we will display the events from
            that day in order for the user to see what they've got going on.
         */
        mCalendarView = (CalendarView) view.findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Log.w("Date info: ", month + "/" + dayOfMonth + "/" + year);
                String day = year+"-"+(month+1)+"-"+dayOfMonth+" 00:00:00";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                try {
                    Date date = df.parse(day);
                    long day_epoch = date.getTime();
                    long next_day = day_epoch + seconds_in_a_day;
                    printEventsFromDate(getContext(), day_epoch, next_day, false);

                } catch (Exception e){
                    Log.w("Date Parser", "The date selected was unable to be parsed.");
                }
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

            // If you click the update calendar button, today's events will get updated, and free
            // time will be recalculated.
            case R.id.updateCalendarButton:
                Log.w(TAG, "Updating today's events.");
                try {
                    // TODAYs events
                    Date today = java.util.Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedToday = df.format(today);
                    df.setTimeZone(TimeZone.getTimeZone("UTC"));

                    try {
                        Date date = df.parse(formattedToday);
                        long today_epoch = date.getTime();
                        long tomorrow = today_epoch + seconds_in_a_day;
                        printEventsFromDate(getContext(), today_epoch, tomorrow, true);

                    } catch (Exception e){
                        Log.w("Date Parser", "The date selected was unable to be parsed.");
                    }
                } catch (Exception e) {
                    Log.w("ListException", e);
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CalendarFragment.OnListFragmentInteractionListener) {
            mListener = (CalendarFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    // Returns a list of events to be displayed in the calendar recycler view on the calendar fragment
    public void printEventsFromDate(Context context, long day, long next, boolean today) {

        // check permissions
        getCalendarData();

        // Prepare the data for a query, some we need some we dont
        Cursor cur = null;
        ContentResolver cr = context.getContentResolver();
        Log.w("Day and next day", "day selected: " + day + " next day: " + next);
        Uri uri = CalendarContract.Events.CONTENT_URI;
        String[] selectionArgs = new String[]{"apaczak2@gmail.com", "apaczak2.gmail.com"};

        cur = cr.query(uri, null, null, null, null);
        Log.w("SUCCESS", selectionArgs[0] + " and " + cur);

        // Get the info
        cur.moveToFirst();
        while (cur.moveToNext()) {
            String eventName = cur.getString(cur.getColumnIndex(CalendarContract.Events.TITLE));
            String loc = cur.getString(cur.getColumnIndex(CalendarContract.Events.EVENT_LOCATION));
            String stime = cur.getString(cur.getColumnIndex(CalendarContract.Events.DTSTART));
            String etime = cur.getString(cur.getColumnIndex(CalendarContract.Events.DTEND));
            String duration = cur.getString(cur.getColumnIndex(CalendarContract.Events.DURATION));

            // format the data from epochs to time
            long stime_long = Long.parseLong(stime);
            long etime_long = Long.parseLong(etime);
            stime = formatDate(stime_long);
            etime = formatDate(etime_long);

            Log.w("EVENTS", eventName + "\t" + day + "\t" + stime_long + "\t" + next);
            // Add event to whatever list if its during that day
            if (stime_long < next && stime_long > day){
                Log.w("EVENTS", eventName + "\t" + loc + "\t" + stime + "\t" + etime + "\t" + duration);

                // Create new calendar item
                new CalendarList.CalendarItem(stime, etime, eventName, today);
            }
        }
    }

    public static String formatDate(long epoch) {
        Date date = new Date(epoch);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        String formatted = format.format(date);
        return formatted;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(CalendarList.CalendarItem item);
    }
}


