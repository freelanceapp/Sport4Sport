package technology.infobite.com.sportsforsports.fontchange;

import android.app.Application;
import android.graphics.Typeface;

public class MyApplication extends Application {

    private Typeface raleway, openSans;

    public void onCreate() {
        super.onCreate();

        raleway = Typeface.createFromAsset(getAssets(), "font/Raleway-Medium.ttf");
        openSans = Typeface.createFromAsset(getAssets(), "font/Raleway-Medium.ttf");
    }

    public Typeface getArialFont() {
        return raleway;
    }

    public Typeface getOpenSans() {
        return openSans;
    }
}
