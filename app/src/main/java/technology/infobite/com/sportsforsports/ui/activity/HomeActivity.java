package technology.infobite.com.sportsforsports.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import technology.infobite.com.boommenu2.boom_menu.BoomButtons.HamButton;
import technology.infobite.com.boommenu2.boom_menu.BoomButtons.OnBMClickListener;
import technology.infobite.com.boommenu2.boom_menu.BoomMenuButton;
import technology.infobite.com.boommenu2.boom_menu.ButtonEnum;
import technology.infobite.com.boommenu2.boom_menu.Util;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.ui.fragment.NotificationFragment;
import technology.infobite.com.sportsforsports.ui.fragment.ProfileFragment;
import technology.infobite.com.sportsforsports.ui.fragment.SettingFragment;
import technology.infobite.com.sportsforsports.ui.fragment.TimelineFragment;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseActivity;

public class HomeActivity extends BaseActivity implements OnBMClickListener, View.OnClickListener {

    private static FragmentManager fragmentManager;
    private String strUserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getIntentData(savedInstanceState);
        init();
    }

    private void getIntentData(Bundle savedInstanceState) {
        if (getIntent() == null)
            return;
        strUserId = getIntent().getStringExtra("user_id");
        if (getIntent().getBooleanExtra("create_profile", false)) {
            fragmentManager = getSupportFragmentManager();
            if (savedInstanceState == null) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.fram_container, new ProfileFragment(),
                                Constant.ProfileFragment).commit();
            }
        } else {
            fragmentManager = getSupportFragmentManager();
            if (savedInstanceState == null) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.fram_container, new TimelineFragment(),
                                Constant.TimelineFragment).commit();
            }
        }
    }

    private void init() {
        findViewById(R.id.fabNewPost).setOnClickListener(this);
        findViewById(R.id.imgSearch).setOnClickListener(this);
        initBoomMenu();
    }

    private void initBoomMenu() {
        BoomMenuButton bmb = findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.Ham);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder();
            Rect imageRect = new Rect(Util.dp2px(10), Util.dp2px(10), Util.dp2px(44), Util.dp2px(44));
            if (i == 0) {
                builder.normalImageRes(R.drawable.ic_timeline);
                builder.normalTextRes(R.string.timeline);
                builder.pieceColor(Color.WHITE);
                builder.highlightedColorRes(R.color.white);
                builder.normalColorRes(R.color.pink);
            }
            if (i == 1) {
                builder.normalImageRes(R.drawable.ic_person);
                builder.normalTextRes(R.string.profile);
                builder.pieceColor(Color.WHITE);
                builder.highlightedColorRes(R.color.white);
                builder.normalColorRes(R.color.pink);
            }
            if (i == 2) {
                builder.normalImageRes(R.drawable.ic_notification);
                builder.normalTextRes(R.string.notification);
                builder.pieceColor(Color.WHITE);
                builder.highlightedColorRes(R.color.white);
                builder.normalColorRes(R.color.pink);
            }
            if (i == 3) {
                builder.normalImageRes(R.drawable.ic_setting);
                builder.normalTextRes(R.string.settings);
                builder.pieceColor(Color.WHITE);
                builder.highlightedColorRes(R.color.white);
                builder.normalColorRes(R.color.pink);
            }
            builder.imageRect(imageRect);
            bmb.addBuilder(builder);
            builder.listener(this);
        }
    }

    @Override
    public void onBoomButtonClick(int index) {
        switch (index) {
            case 0:
                Fragment TimelineFragment = fragmentManager.findFragmentByTag(Constant.TimelineFragment);
                if (TimelineFragment == null) {
                    changeFragment(new TimelineFragment(), Constant.TimelineFragment);
                }
                break;
            case 1:
                Fragment ProfileFragment = fragmentManager.findFragmentByTag(Constant.ProfileFragment);
                if (ProfileFragment == null) {
                    changeFragment(new ProfileFragment(), Constant.ProfileFragment);
                }
                break;
            case 2:
                Fragment NotificationFragment = fragmentManager.findFragmentByTag(Constant.NotificationFragment);
                if (NotificationFragment == null) {
                    changeFragment(new NotificationFragment(), Constant.NotificationFragment);
                }
                break;
            case 3:
                Fragment SettingFragment = fragmentManager.findFragmentByTag(Constant.SettingFragment);
                if (SettingFragment == null) {
                    changeFragment(new SettingFragment(), Constant.SettingFragment);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabNewPost:
                startActivity(new Intent(getApplicationContext(), MyPostActivity.class));
                break;
            case R.id.imgSearch:
                startActivity(new Intent(getApplicationContext(), SearchListActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Fragment ProfileFragment = fragmentManager.findFragmentByTag(Constant.ProfileFragment);
        Fragment NotificationFragment = fragmentManager.findFragmentByTag(Constant.NotificationFragment);
        Fragment SettingFragment = fragmentManager.findFragmentByTag(Constant.SettingFragment);
        if (ProfileFragment != null)
            replaceTimelineFragment();
        else if (NotificationFragment != null)
            replaceTimelineFragment();
        else if (SettingFragment != null)
            replaceTimelineFragment();
        else
            super.onBackPressed();
    }

    public void replaceTimelineFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.fram_container, new TimelineFragment(),
                        Constant.TimelineFragment).commit();
    }

    private void changeFragment(Fragment fragment, String strTag) {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.fram_container, fragment,
                        strTag).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppPreference.getBooleanPreference(mContext, "UpdateProfile")) {
            AppPreference.setBooleanPreference(mContext, "UpdateProfile", false);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fram_container, new ProfileFragment(),
                            Constant.TimelineFragment).commit();
        } else if (AppPreference.getBooleanPreference(mContext, "NewPost")) {
            AppPreference.setBooleanPreference(mContext, "NewPost", false);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fram_container, new TimelineFragment(),
                            Constant.TimelineFragment).commit();
        }
    }
}
