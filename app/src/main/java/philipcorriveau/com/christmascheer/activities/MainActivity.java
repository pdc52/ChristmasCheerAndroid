package philipcorriveau.com.christmascheer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import philipcorriveau.com.christmascheer.Constants;
import philipcorriveau.com.christmascheer.R;
import philipcorriveau.com.christmascheer.adapters.CheerAdapter;
import philipcorriveau.com.christmascheer.location.MyLocation;
import philipcorriveau.com.christmascheer.models.Cheer;
import philipcorriveau.com.christmascheer.utils.FontChanger;


public class MainActivity extends VisibleActionBarActivity {

    CircleImageView sendChristmasCheerButton;
    TextView nameTextView;
    TextView locationTextView;

    SlidingMenu slidingMenu;
    ListView cheerHistory;
    CheerAdapter cheerAdapter;
    TextView emptyHistory;

    String name;
    MyLocation myLocation;
    String displayAddress;
    HashMap<String, String> states;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        name = preferences.getString(Constants.PREF_NAME, "");
        if (TextUtils.isEmpty(name)) {
            showNameDialog();
        }

        sendChristmasCheerButton = (CircleImageView) findViewById(R.id.send_christmas_cheer_button);
        setRandomButtonImage();

        nameTextView = (TextView) findViewById(R.id.name_text_view);
        nameTextView.setText(name);
        nameTextView.setTypeface(FontChanger.getInstance(this).getTypeFace());

        locationTextView = (TextView) findViewById(R.id.location_text_view);
        locationTextView.setTypeface(FontChanger.getInstance(this).getTypeFace());

        setupStatesMap();
        setupSlidingMenu();
        getLocation();
        checkForReceivedPush();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onMenuClick(MenuItem menuItem) {
        slidingMenu.toggle();
    }

    public void setRandomButtonImage() {
        Random random = new Random();
        int randomInt = random.nextInt(9);
        String fileName = "main_button_";
        fileName += randomInt;
        int id = getResources().getIdentifier(fileName, "drawable", getPackageName());
        sendChristmasCheerButton.setImageResource(id);
    }

    public void setupSlidingMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        slidingMenu.setBehindOffset(200);
        slidingMenu.setFadeDegree(0.35f);

        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.sliding_menu);

        cheerHistory = (ListView) findViewById(R.id.cheer_history);
        cheerAdapter = new CheerAdapter(this);
        cheerAdapter.setPaginationEnabled(false);
        cheerHistory.setAdapter(cheerAdapter);

        emptyHistory = (TextView) findViewById(R.id.empty_history);
        cheerHistory.setEmptyView(emptyHistory);

        slidingMenu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
            @Override
            public void onOpened() {
                cheerAdapter.loadObjects();
            }
        });
    }

    public void getLocation() {
        MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
            @Override
            public void gotLocation(Location location){
                findAddress(location.getLatitude(), location.getLongitude());
            }
        };
        myLocation = new MyLocation();
        myLocation.getLocation(this, locationResult);
    }

    public void findAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses = null;

        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null) {
            Address address = addresses.get(0);
            displayAddress = address.getLocality() + ", ";
            if (address.getCountryCode().equalsIgnoreCase("US")) {
                String adminArea = address.getAdminArea();
                if (states.containsKey(adminArea)) {
                    displayAddress += states.get(adminArea);
                } else {
                    displayAddress += adminArea;
                }
            } else {
                displayAddress += address.getCountryCode();
            }

        } else {
            displayAddress = getString(R.string.default_location);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                locationTextView.setText(displayAddress);
            }
        });
    }

    public void showNameDialog() {
        Intent intent = new Intent(this, NameActivity.class);
        startActivity(intent);
        finish();
    }

    public void checkForReceivedPush() {
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.VIEW_PUSH)) {
            //Launched from push notification
            Bundle extras = intent.getExtras();
            String jsonData = extras.getString( "com.parse.Data" );

            Gson gson = new Gson();
            Cheer cheer = gson.fromJson(jsonData, Cheer.class);
            if (cheer.getIsResponse()) {
                Intent cheerReturnedIntent = new Intent(this, CheerReturnedActivity.class);
                cheerReturnedIntent.putExtra(Constants.CHEER_RETURNED_DATA_KEY, jsonData);
                startActivity(cheerReturnedIntent);
            } else {
                Intent receiveCheerIntent = new Intent(this, ReceivedCheerActivity.class);
                receiveCheerIntent.putExtra(Constants.RECEIVED_CHEER_DATA_KEY, jsonData);
                startActivity(receiveCheerIntent);
            }
        }
    }

    public void sendChristmasCheerPush(View view) {
        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        String name = preferences.getString(Constants.PREF_NAME, getString(R.string.default_name));

        HashMap<String, String> parseData = new HashMap<String, String>();
        parseData.put("fromUserId", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        parseData.put("fromInstallationId", ParseInstallation.getCurrentInstallation().getObjectId());
        parseData.put("fromName", name);
        parseData.put("fromLocation", displayAddress);
        ParseCloud.callFunctionInBackground(Constants.PARSE_SEND_CHEER_DEV, parseData, new FunctionCallback<String>() {
            @Override
            public void done(String string, ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "Push successful!");
                } else {
                    Log.e("com.parse.push", "Push failed");
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        myLocation.cancelTimer();
        super.onPause();
    }

    public void setupStatesMap() {
        states = new HashMap<String, String>();
        states.put("Alabama","AL");
        states.put("Alaska","AK");
        states.put("Alberta","AB");
        states.put("American Samoa","AS");
        states.put("Arizona","AZ");
        states.put("Arkansas","AR");
        states.put("Armed Forces (AE)","AE");
        states.put("Armed Forces Americas","AA");
        states.put("Armed Forces Pacific","AP");
        states.put("British Columbia","BC");
        states.put("California","CA");
        states.put("Colorado","CO");
        states.put("Connecticut","CT");
        states.put("Delaware","DE");
        states.put("District Of Columbia","DC");
        states.put("Florida","FL");
        states.put("Georgia","GA");
        states.put("Guam","GU");
        states.put("Hawaii","HI");
        states.put("Idaho","ID");
        states.put("Illinois","IL");
        states.put("Indiana","IN");
        states.put("Iowa","IA");
        states.put("Kansas","KS");
        states.put("Kentucky","KY");
        states.put("Louisiana","LA");
        states.put("Maine","ME");
        states.put("Manitoba","MB");
        states.put("Maryland","MD");
        states.put("Massachusetts","MA");
        states.put("Michigan","MI");
        states.put("Minnesota","MN");
        states.put("Mississippi","MS");
        states.put("Missouri","MO");
        states.put("Montana","MT");
        states.put("Nebraska","NE");
        states.put("Nevada","NV");
        states.put("New Brunswick","NB");
        states.put("New Hampshire","NH");
        states.put("New Jersey","NJ");
        states.put("New Mexico","NM");
        states.put("New York","NY");
        states.put("Newfoundland","NF");
        states.put("North Carolina","NC");
        states.put("North Dakota","ND");
        states.put("Northwest Territories","NT");
        states.put("Nova Scotia","NS");
        states.put("Nunavut","NU");
        states.put("Ohio","OH");
        states.put("Oklahoma","OK");
        states.put("Ontario","ON");
        states.put("Oregon","OR");
        states.put("Pennsylvania","PA");
        states.put("Prince Edward Island","PE");
        states.put("Puerto Rico","PR");
        states.put("Quebec","PQ");
        states.put("Rhode Island","RI");
        states.put("Saskatchewan","SK");
        states.put("South Carolina","SC");
        states.put("South Dakota","SD");
        states.put("Tennessee","TN");
        states.put("Texas","TX");
        states.put("Utah","UT");
        states.put("Vermont","VT");
        states.put("Virgin Islands","VI");
        states.put("Virginia","VA");
        states.put("Washington","WA");
        states.put("West Virginia","WV");
        states.put("Wisconsin","WI");
        states.put("Wyoming","WY");
        states.put("Yukon Territory","YT");
    }
}
