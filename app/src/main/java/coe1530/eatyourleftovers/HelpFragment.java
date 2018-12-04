package coe1530.eatyourleftovers;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.WindowManager;
import android.view.KeyEvent;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple help page fragment subclass
 */
public class HelpFragment extends Fragment implements View.OnClickListener {

    private TextInputEditText sendTxt;
    private String sendString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        View view = inflater.inflate(R.layout.fragment_help, container, false);
        sendTxt = (TextInputEditText) view.findViewById(R.id.sendEmailTxt);
        //sendString = sendTxt.getText().toString();
        Button sendBtn = (Button) view.findViewById(R.id.sendEmailBtn);
        sendBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // If you click the submit button
            case R.id.sendEmailBtn:
                sendString = sendTxt.getText().toString();
                sendEmail();
                sendTxt.setText("");
        }
    }

    protected void sendEmail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"Eat@Your.Leftovers"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Hi!");
        i.putExtra(Intent.EXTRA_TEXT, sendString);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this.getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }
}
