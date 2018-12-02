package coe1530.eatyourleftovers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

<<<<<<< Updated upstream
public class MainView extends AppCompatActivity {
=======
import com.roomorama.caldroid.CaldroidFragment;

import java.util.Date;

import coe1530.eatyourleftovers.dummy.DummyContent;
import coe1530.eatyourleftovers.dummy.ToDoList;

public class MainView extends AppCompatActivity implements TodoItemFragment.OnListFragmentInteractionListener {

    private BottomNavigationView navigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
//    CaldroidFragment caldroidFragment = new CaldroidFragment();
    CaldroidFragment caldroidFragment = new CaldroidCustomFragment();
//    CaldroidActivity caldroidActivity = new CaldroidActivity();
//    CaldroidFragment caldroidFragment = caldroidActivity.getCaldroidFragment();
>>>>>>> Stashed changes

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_calendar:
<<<<<<< Updated upstream
                    mTextMessage.setText(R.string.title_calendar);
                    return true;
=======
                    fragment = caldroidFragment;
                    caldroidFragment.setSelectedDate(new Date(2018,11,30));
                    break;
>>>>>>> Stashed changes
                case R.id.navigation_todo:
                    mTextMessage.setText(R.string.title_todo);
                    return true;
                case R.id.navigation_map:
                    mTextMessage.setText(R.string.title_map);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
