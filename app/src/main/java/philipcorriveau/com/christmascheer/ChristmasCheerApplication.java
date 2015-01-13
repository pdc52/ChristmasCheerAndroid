package philipcorriveau.com.christmascheer;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import philipcorriveau.com.christmascheer.utils.VisibilityManager;

/**
 * Created by philipcorriveau on 11/21/14.
 */
public class ChristmasCheerApplication extends Application{

    VisibilityManager visibilityManager;

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, Constants.PARSE_APPLICATION_ID, Constants.PARSE_CLIENT_KEY);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (!preferences.getBoolean(Constants.PREF_IS_PARSE_SUBSCRIBED, false)) {
            ParsePush.subscribeInBackground("", new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                        sharedPreferences.edit().putBoolean(Constants.PREF_IS_PARSE_SUBSCRIBED, true).apply();

                        ParseInstallation parseInstallation = ParseInstallation.getCurrentInstallation();
                        parseInstallation.put("dev", true);
                        try {
                            parseInstallation.save();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                    } else {
                        Log.e("com.parse.push", "failed to subscribe for push", e);
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public VisibilityManager getVisibilityManager() {
        return visibilityManager;
    }
}
