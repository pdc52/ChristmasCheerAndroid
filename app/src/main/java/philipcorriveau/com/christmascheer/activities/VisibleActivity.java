package philipcorriveau.com.christmascheer.activities;

import android.app.Activity;

import philipcorriveau.com.christmascheer.utils.VisibilityManager;

/**
 * Created by philipcorriveau on 12/14/14.
 */
public class VisibleActivity extends Activity {

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
