package coe1530.eatyourleftovers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import android.view.View;

import static android.app.Notification.VISIBILITY_PUBLIC;
import static android.app.PendingIntent.getActivity;

/*
This is the main view for the application. THis view will switch between three fragments, one for a
calendar view that displays items from the calendar app for a certain day, the to do list which holds
items of interest to the user, and the help screen that the user can use to convey bugs to the developers.
 */
public class MainView extends AppCompatActivity
        implements TodoItemFragment.OnListFragmentInteractionListener, CalendarFragment.OnListFragmentInteractionListener{

    private BottomNavigationView navigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    
	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    fragment = new CalendarFragment();
                    break;
                case R.id.navigation_todo:
                    fragment = new TodoItemFragment();
                    break;
                case R.id.navigation_help:
                    fragment = new HelpFragment();
                    break;
            }
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment).commit();
            return true;
        }
    };

    @Override
    // Code that is run when this activity has begun
    // for this one, since it is the main activity, the is called when the launcher launches this app
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        // Blows up what belongs in the xml file whose xml file is "layout/activity_main_view"
        setContentView(R.layout.activity_main_view);
        //setContentView(R.layout.activity_login);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        fragmentManager = getSupportFragmentManager();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onListFragmentInteraction(ToDoList.ToDoItem item) {

    }

    @Override
    public void onListFragmentInteraction(CalendarList.CalendarItem item) {
	}
    
//	public void sendNotification(View view) {
//        CalendarList.CalendarItem item = new CalendarList.CalendarItem("0","1","Study", true);
//        Intent intent = new Intent();
//        PendingIntent pendingIntent = getActivity(getApplicationContext(), 0, intent,0);
//
////        NotificationCompat.Style style = new NotificationCompat.BigPictureStyle();
//        NotificationCompat.Style style = new NotificationCompat.DecoratedCustomViewStyle();
//        //Get an instance of NotificationManager
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.notification)
//                        .setContentTitle("You have free time!")
//                        .setVisibility(VISIBILITY_PUBLIC)
//                        .setFullScreenIntent(pendingIntent, true)
//                        .setStyle(style)
//                        .setContentText("Why don't you work on '" + item.toString() + "'?");
//
//
//        // Gets an instance of the NotificationManager service
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(001, mBuilder.build());
//    }

    public void sendNotification(View view) {
        CalendarList.CalendarItem item;
//        if (!CalendarList.TODAYS_ITEMS.isEmpty()) {
//            Log.i("Not empty","TODAYS_ITEMS is not empty");
//            item = CalendarList.TODAYS_ITEMS.get(0);
//            Log.i("TODAYS_ITEMS.get(0)",item.toString());
//        }
//        else {
        item = new CalendarList.CalendarItem("0","1", "study", true);
        Log.i("empty","TODAYS_ITEMS is empty :(");
//        }

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // The id of the channel.
        String id = "my_channel_01";

        // The user-visible name of the channel.
        CharSequence name = "some channel";

        // The user-visible description of the channel.
        String description = "some description";

        int importance = NotificationManager.IMPORTANCE_LOW;

        NotificationChannel mChannel = new NotificationChannel(id, name,importance);

        // Configure the notification channel.
        mChannel.setDescription(description);

        mChannel.enableLights(true);

        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        mNotificationManager.createNotificationChannel(mChannel);

        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        // Sets an ID for the notification, so it can be updated.
        int notifyID = 1;

        // The id of the channel.
        String CHANNEL_ID = "my_channel_01";

        Intent intent = new Intent();
        PendingIntent pendingIntent = getActivity(getApplicationContext(), 0, intent,0);
        NotificationCompat.Style style = new NotificationCompat.DecoratedCustomViewStyle();

        // Create a notification and set the notification channel.
        Notification notification = new Notification.Builder(this)
                .setContentTitle("It looks like you have free time!")
                .setContentText("Why don't you try to work on '" + item.toString() + "'?")
                .setFullScreenIntent(pendingIntent, true)
                .setVisibility(VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.notification)
                .setChannelId(CHANNEL_ID)
                .build();

// Issue the notification.
        mNotificationManager.notify(1, notification);

//        Intent intent = new Intent();
//        PendingIntent pendingIntent = getActivity(getApplicationContext(), 0, intent,0);
//
////        NotificationCompat.Style style = new NotificationCompat.BigPictureStyle();
//        NotificationCompat.Style style = new NotificationCompat.DecoratedCustomViewStyle();
//        //Get an instance of NotificationManager
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.notification)
//                        .setContentTitle("You have free time!")
//                        .setVisibility(VISIBILITY_PUBLIC)
//                        .setFullScreenIntent(pendingIntent, true)
//                        .setStyle(style)
//                        .setContentText("Why don't you work on '" + item.toString() + "'?");
//

        // Gets an instance of the NotificationManager service
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(001, mBuilder.build());
    }

    public void sendNotification(CalendarList.CalendarItem item) {
        Intent intent = new Intent();
        PendingIntent pendingIntent = getActivity(getApplicationContext(), 0, intent,0);

        NotificationCompat.Style style = new NotificationCompat.DecoratedCustomViewStyle();
        //Get an instance of NotificationManager
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle("You have free time!")
                        .setVisibility(VISIBILITY_PUBLIC)
                        .setFullScreenIntent(pendingIntent, true)
                        .setStyle(style)
                        .setContentText("Why don't you work on '" + item.toString() + "'?");


        // Gets an instance of the NotificationManager service
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(001, mBuilder.build());
    }
}
