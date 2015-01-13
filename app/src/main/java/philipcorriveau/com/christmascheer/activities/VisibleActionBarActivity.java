package philipcorriveau.com.christmascheer.activities;

import android.support.v7.app.ActionBarActivity;

import philipcorriveau.com.christmascheer.utils.VisibilityManager;

/**
 * Created by philipcorriveau on 12/15/14.
 */
public class VisibleActionBarActivity extends ActionBarActivity {

    @Override
    protected void onResume() {
        super.onResume();
        VisibilityManager.setIsVisible(true);
    }

    @Override
    protected void onPause() {
        VisibilityManager.setIsVisible(false);
        super.onPause();
    }

}
