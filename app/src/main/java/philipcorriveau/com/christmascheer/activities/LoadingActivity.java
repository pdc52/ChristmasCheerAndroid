package philipcorriveau.com.christmascheer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import philipcorriveau.com.christmascheer.R;
import philipcorriveau.com.christmascheer.utils.FontChanger;

/**
 * Created by philipcorriveau on 12/22/14.
 */
public class LoadingActivity extends Activity {

    public static final int SPINNING_ANIMATION_LENGTH = 1000;
    
    ImageView rotateImage;
    TextView loadingMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        rotateImage = (ImageView) findViewById(R.id.rotate_image_view);

        loadingMessage = (TextView) findViewById(R.id.loading_message);
        loadingMessage.setTypeface(FontChanger.getInstance(this).getTypeFace());

        setupAnimation();
    }

    public void setupAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(SPINNING_ANIMATION_LENGTH);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateImage.startAnimation(rotateAnimation);
    }
}
