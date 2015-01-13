package philipcorriveau.com.christmascheer.receivers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import philipcorriveau.com.christmascheer.Constants;
import philipcorriveau.com.christmascheer.utils.VisibilityManager;
import philipcorriveau.com.christmascheer.activities.MainActivity;

/**
 * Created by philipcorriveau on 12/12/14.
 */
public class CheerPushBroadcastReceiver extends ParsePushBroadcastReceiver {

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        Log.d("com.parse.push", "Push received");
        if (VisibilityManager.getIsVisible()) {
            showPush(context, intent);
        } else {
            super.onPushReceive(context, intent);
        }
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        Log.d("com.parse.push", "Push opened");
        showPush(context, intent);
    }

    public void showPush(Context context, Intent intent) {
        Intent pushIntent = new Intent(context, MainActivity.class);
        pushIntent.putExtra(Constants.VIEW_PUSH, Constants.VIEW_PUSH);
        pushIntent.putExtras(intent.getExtras());
        pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(pushIntent);
    }
}
