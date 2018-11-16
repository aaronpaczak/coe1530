package coe1530.eatyourleftovers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;

import coe1530.eatyourleftovers.dummy.DummyContent;
import coe1530.eatyourleftovers.dummy.ToDoList;

public class MainView extends AppCompatActivity implements TodoItemFragment.OnListFragmentInteractionListener {

    private BottomNavigationView navigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    CaldroidFragment caldroidFragment = new CaldroidFragment();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    fragment = caldroidFragment;
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
}
