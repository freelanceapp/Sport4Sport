package com.pinlinx.fontchange;

import android.app.Application;

public class CustomFontApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "DEFAULT", "font/20db.otf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "font/20db.otf");
        FontsOverride.setDefaultFont(this, "SERIF", "font/20db.otf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "font/20db.otf");
    }
}
