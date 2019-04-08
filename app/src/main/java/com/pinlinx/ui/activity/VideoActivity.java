package com.pinlinx.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.pinlinx.R;
import com.pinlinx.constant.Constant;
import com.pinlinx.ui.fragment.VideoGalleryFragment;
import com.pinlinx.utils.BaseActivity;

public class VideoActivity extends BaseActivity implements View.OnClickListener {

    public static FragmentManager videoFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mContext = this;
        init();
    }

    private void init() {
        videoFragmentManager = getSupportFragmentManager();
        videoFragmentManager
                .beginTransaction()
                .replace(R.id.fram_container, new VideoGalleryFragment(),
                        Constant.VideoGalleryFragment).commit();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        Fragment VideoGalleryFragment = videoFragmentManager.findFragmentByTag(Constant.VideoGalleryFragment);
        Fragment VideoTrimmerFragment = videoFragmentManager.findFragmentByTag(Constant.VideoTrimmerFragment);
        if (VideoGalleryFragment != null) {
            super.onBackPressed();
        } else if (VideoTrimmerFragment != null) {
            replaceTimelineFragment();
        }else {
            super.onBackPressed();
        }
    }

    private void replaceTimelineFragment() {
        videoFragmentManager
                .beginTransaction()
                .replace(R.id.fram_container, new VideoGalleryFragment(),
                        Constant.VideoGalleryFragment).commit();
    }
}

