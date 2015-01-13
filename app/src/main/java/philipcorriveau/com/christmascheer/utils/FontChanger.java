package philipcorriveau.com.christmascheer.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by philipcorriveau on 12/17/14.
 */
public class FontChanger {

    private static FontChanger instance;
    private static Typeface typeface;

    public static FontChanger getInstance(Context context) {
        synchronized (FontChanger.class) {
            if (instance == null) {
                instance = new FontChanger();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "christmasCrackFont.otf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}
