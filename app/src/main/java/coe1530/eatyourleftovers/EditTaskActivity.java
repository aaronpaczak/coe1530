package coe1530.eatyourleftovers;
//
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//
//public class AddTaskActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_task);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }
//
//}


import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import coe1530.eatyourleftovers.data.TaskContract;
import coe1530.eatyourleftovers.dummy.ToDoList;

public class EditTaskActivity extends AppCompatActivity {

    // Declare a member variable to keep track of a task's selected mPriority, mProgress
    private int mPriority = 0;
    private int mDuration = 0;

    // Initialize the seekbar
    private SeekBar mSeekBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // Initialize to highest mPriority by default (mPriority = 1)
        ((RadioButton) findViewById(R.id.radButton1)).setChecked(true);
        mPriority = 1;

        mSeekBar = (SeekBar) findViewById(R.id.durationBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            // Change the text view next to the seek bar to update how many hours the user changed it to
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView textView = (TextView) findViewById(R.id.currentSelectedDuration);
                if (progress == 1) {
                    textView.setText(progress + " hour");
                } else {
                    textView.setText(progress + " hours");
                }
                mDuration = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        Intent theIntent=getIntent();
        theIntent.getExtras();
        Bundle extras = getIntent().getExtras();
        String theTitle=extras.getString("Task Name");
        ToDoList.ToDoItem theToDo=ToDoList.getToDoItem(theTitle);
        EditText theEditView=(EditText) findViewById(R.id.editTextTaskTitle);
        theEditView.setText(theToDo.name,TextView.BufferType.EDITABLE);
        theEditView=(EditText) findViewById(R.id.editTextTaskDescription);
        theEditView.setText(theToDo.details,TextView.BufferType.EDITABLE);
        SeekBar theSeekBar=(SeekBar) findViewById(R.id.durationBar);
        theSeekBar.setProgress(theToDo.duration);

        Log.w("Checking", Integer.toString(theToDo.priority));
        RadioButton theRadioButton=(RadioButton) findViewById(R.id.radButton1);
        theRadioButton.setChecked(true);
        if(theToDo.priority==1)
        {
            theRadioButton.setChecked(true);
        }
        else
        {
            theRadioButton.setChecked(false);
        }

        theRadioButton=(RadioButton) findViewById(R.id.radButton2);
        if(theToDo.priority==2)
        {
            theRadioButton.setChecked(true);
        }
        else
        {
            theRadioButton.setChecked(false);
        }

        theRadioButton=(RadioButton) findViewById(R.id.radButton3);
        if(theToDo.priority==3)
        {
            theRadioButton.setChecked(true);
        }
        else
        {
            theRadioButton.setChecked(false);
        }
    }


    /**
     * onClickEditTask is called when the "EDIT" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onClickEditTask(View view) {
        // Check if the To Do title input is empty - (don't create an entry if there is no input)
        String title = ((EditText) findViewById(R.id.editTextTaskTitle)).getText().toString();
        if (title.length() == 0 || title.equalsIgnoreCase("ToDo Title")) { return; }

        // Check if the To Do description input is empty - (don't create an entry if there is no input)
        String desc = ((EditText) findViewById(R.id.editTextTaskDescription)).getText().toString();
        if (desc.length() == 0 || title.equalsIgnoreCase("ToDo Description")) { return; }

        // Check if no priority has been selected.
        if (mPriority == 0) { return; }

        // Check if no duration has been selected.
        if (mDuration == 0) { return; }

        // Remove old To Do Item
        Intent theIntent=getIntent();
        theIntent.getExtras();
        Bundle extras = getIntent().getExtras();
        String theTitle=extras.getString("Task Name");
        ToDoList.removeItem(ToDoList.getToDoItem(theTitle));
        // Create Edited To Do Item
        new ToDoList.ToDoItem(title, mDuration, mPriority, desc);

//        For Database entry, use the infrastructure provided by Google in the data folder
//        // Retrieve user input and store it in a ContentValues object
//        // Create new empty ContentValues object
//        ContentValues contentValues = new ContentValues();
//        // Put the task description and selected mPriority into the ContentValues
//        contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, input);
//        contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY, mPriority);
//        // Insert new task data via a ContentResolver
//        Uri uri = getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);
//        // Display the URI that's returned with a Toast
//        if(uri != null) {
//            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
//        }

        // Finish activity (this returns back to MainView's current fragment AKA ToDoFragment)
        finish();
    }

    public void onClickRemoveTask(View view) {
        // Remove old To Do Item
        Intent theIntent=getIntent();
        theIntent.getExtras();
        Bundle extras = getIntent().getExtras();
        String theTitle=extras.getString("Task Name");
        ToDoList.removeItem(ToDoList.getToDoItem(theTitle));
        finish();
    }

    // Cancel editing a task
    public void onClickCancelTask(View view) { finish(); }

    /**
     * onPrioritySelected is called whenever a priority button is clicked.
     * It changes the value of mPriority based on the selected button.
     */
    public void onPrioritySelected(View view) {
        if (((RadioButton) findViewById(R.id.radButton1)).isChecked()) {
            mPriority = 1;
        } else if (((RadioButton) findViewById(R.id.radButton2)).isChecked()) {
            mPriority = 2;
        } else if (((RadioButton) findViewById(R.id.radButton3)).isChecked()) {
            mPriority = 3;
        }
    }


}
