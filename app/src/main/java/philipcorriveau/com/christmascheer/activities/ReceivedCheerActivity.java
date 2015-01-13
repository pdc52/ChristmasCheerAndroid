package philipcorriveau.com.christmascheer.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;

import java.util.HashMap;

import philipcorriveau.com.christmascheer.Constants;
import philipcorriveau.com.christmascheer.utils.FontChanger;
import philipcorriveau.com.christmascheer.R;
import philipcorriveau.com.christmascheer.models.Cheer;

/**
 * Created by philipcorriveau on 12/12/14.
 */
public class ReceivedCheerActivity extends VisibleActivity {

    Cheer cheer;

    Button returnCheerButton;
    Button declineReturnCheerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_cheer);

        String jsonData = getIntent().getStringExtra(Constants.RECEIVED_CHEER_DATA_KEY);
        Log.d("com.parse.push", jsonData);

        Gson gson = new Gson();
        cheer = gson.fromJson(jsonData, Cheer.class);

        TextView cheerTitle = (TextView) findViewById(R.id.received_cheer_title);
        cheerTitle.setTypeface(FontChanger.getInstance(this).getTypeFace());

        TextView cheerMessage = (TextView) findViewById(R.id.cheer_message);
        cheerMessage.setText(cheer.getAlert());
        cheerMessage.setTypeface(FontChanger.getInstance(this).getTypeFace());

        returnCheerButton = (Button) findViewById(R.id.return_cheer_button);
        returnCheerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnCheer();
                finish();
            }
        });
        returnCheerButton.setTypeface(FontChanger.getInstance(this).getTypeFace());


        declineReturnCheerButton = (Button) findViewById(R.id.decline_return_cheer_button);
        declineReturnCheerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        declineReturnCheerButton.setTypeface(FontChanger.getInstance(this).getTypeFace());
    }

    public void returnCheer() {
        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String name = preferences.getString(Constants.PREF_NAME, getString(R.string.default_name));

        HashMap<String, String> parseData = new HashMap<String, String>();
        parseData.put("originalNoteId", cheer.getOriginalNoteId());
        parseData.put("fromUserId", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        parseData.put("fromInstallationId", ParseInstallation.getCurrentInstallation().getObjectId());
        parseData.put("fromName", name);
        parseData.put("fromLocation", "The North Pole");

        ParseCloud.callFunctionInBackground(Constants.PARSE_RETURN_CHEER_DEV, parseData, new FunctionCallback<String>() {
            @Override
            public void done(String string, ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "Return successful!");
                } else {
                    Log.e("com.parse.push", "Return failed");
                    e.printStackTrace();
                }
            }
        });
    }

}
