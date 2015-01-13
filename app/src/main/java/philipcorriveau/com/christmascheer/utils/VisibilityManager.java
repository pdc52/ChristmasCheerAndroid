package philipcorriveau.com.christmascheer.utils;

/**
 * Created by philipcorriveau on 12/14/14.
 */
public class VisibilityManager {

    private static boolean isVisible = false;

    public static void setIsVisible(boolean visible) {
        isVisible = visible;
    }

    public static boolean getIsVisible() {
        return isVisible;
    }
}
