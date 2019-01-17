package technology.infobite.com.sportsforsports;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.ui.activity.LoginActivity;
import technology.infobite.com.sportsforsports.ui.activity.NewPostsActivity;
import technology.infobite.com.sportsforsports.utils.AppPreference;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppPreference.getBooleanPreference(getApplicationContext(), Constant.LOGIN_API)) {
                    Intent intent = new Intent(SplashScreen.this, NewPostsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }
}
