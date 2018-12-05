package coe1530.eatyourleftovers;

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
import android.view.MenuItem;
import android.view.View;

import static android.app.Notification.VISIBILITY_PUBLIC;
import static android.app.PendingIntent.getActivity;

public class MainView extends AppCompatActivity implements TodoItemFragment.OnListFragmentInteractionListener {

    private BottomNavigationView navigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    // CaldroidFragment caldroidFragment = new CaldroidFragment();


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
                case R.id.navigation_map:
                    fragment = new MapFragment();
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
        super.onCreate(savedInstanceState);
        // Blows up what belongs in the xml file whose xml file is "layout/activity_main_view"
        setContentView(R.layout.activity_main_view);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.inflateMenu(R.menu.navigation);
        fragmentManager = getSupportFragmentManager();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onListFragmentInteraction(ToDoList.ToDoItem item) {

    }

    public void sendNotification(View view) {
        CalendarList.CalendarItem item = new CalendarList.CalendarItem("0","1","Study");
        Intent intent = new Intent();
        PendingIntent pendingIntent = getActivity(getApplicationContext(), 0, intent,0);

//        NotificationCompat.Style style = new NotificationCompat.BigPictureStyle();
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

    public void sendNotification(CalendarList.CalendarItem item) {
        Intent intent = new Intent();
        PendingIntent pendingIntent = getActivity(getApplicationContext(), 0, intent,0);

//        NotificationCompat.Style style = new NotificationCompat.BigPictureStyle();
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
