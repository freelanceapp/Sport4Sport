package technology.infobite.com.sportsforsports.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import technology.infobite.com.boommenu2.boom_menu.BoomButtons.HamButton;
import technology.infobite.com.boommenu2.boom_menu.BoomButtons.OnBMClickListener;
import technology.infobite.com.boommenu2.boom_menu.BoomMenuButton;
import technology.infobite.com.boommenu2.boom_menu.ButtonEnum;
import technology.infobite.com.boommenu2.boom_menu.Util;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.ui.fragment.NotificationFragment;
import technology.infobite.com.sportsforsports.ui.fragment.ProfileFragment;
import technology.infobite.com.sportsforsports.ui.fragment.SettingFragment;
import technology.infobite.com.sportsforsports.ui.fragment.TimelineFragment;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseActivity;

public class HomeActivity extends BaseActivity implements OnBMClickListener, View.OnClickListener {

    public static FragmentManager fragmentManager;
    private String strUserId = "";
    private final int PICK_IMAGE_CAMERA = 124;
    private final int LOAD_IMAGE_CAMERA = 125;

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
        findViewById(R.id.rlNewPost).setOnClickListener(this);
        findViewById(R.id.fabNewPost).setOnClickListener(this);
        findViewById(R.id.imgSearch).setOnClickListener(this);
        findViewById(R.id.imgLeague).setOnClickListener(this);
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
                startActivity(new Intent(getApplicationContext(), NewPostActivity.class));
                break;
            case R.id.imgLeague:
                startActivity(new Intent(getApplicationContext(), LeagueActivity.class));
                break;
            case R.id.imgSearch:
                startActivity(new Intent(getApplicationContext(), SearchListActivity.class));
                break;
            case R.id.rlNewPost:
                try {
                    if (checkPermission()) {
                        selectImage();
                    } else {
                        Alerts.show(mContext, "Please accept permission for camera");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void selectImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PICK_IMAGE_CAMERA);
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, LOAD_IMAGE_CAMERA);
            return false;
        }
/*
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Alerts.show(mContext, "Permission not granted");
            return false;
        }*/
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOAD_IMAGE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage();
                } else {
                    Alerts.show(mContext, "Please give permission");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(mContext, photo);
                File finalFile = new File(getRealPathFromURI(tempUri));

                newPostFeedApi(finalFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        Bitmap OutImage = Bitmap.createScaledBitmap(inImage, 1000, 1000, true);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title", null);
        return Uri.parse(path);
    }

    /*public Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }*/

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
            refreshTimeline();
        }
    }

    private void refreshTimeline() {
        AppPreference.setBooleanPreference(mContext, "NewPost", false);
        fragmentManager
                .beginTransaction()
                .replace(R.id.fram_container, new TimelineFragment(),
                        Constant.TimelineFragment).commit();
    }

    /****************************************************************************/
    /*New post api*/
    private void newPostFeedApi(File imageFile) {
        String strId = AppPreference.getStringPreference(getApplicationContext(), Constant.USER_ID);
        String strAthleteStatus = "";
        String strArticleUrl = Constant.DEMO_URL;
        String strArticleHeadline = "";

        if (cd.isNetworkAvailable()) {
            RequestBody _id = RequestBody.create(MediaType.parse("text/plain"), strId);
            RequestBody _Status = RequestBody.create(MediaType.parse("text/plain"), strAthleteStatus);
            RequestBody _Url = RequestBody.create(MediaType.parse("text/plain"), strArticleUrl);
            RequestBody _Headline = RequestBody.create(MediaType.parse("text/plain"), strArticleHeadline);

            MultipartBody.Part fileToUpload;
            MultipartBody.Part videoFileUpload = null;
            MultipartBody.Part videoThumbnailUpload = null;

            RequestBody imageBodyFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
            fileToUpload = MultipartBody.Part.createFormData("alhlete_images", imageFile.getName(),
                    imageBodyFile);

            RetrofitService.getNewPostData(new Dialog(mContext), retrofitApiClient.newPostFeed(_id, _Status,
                    videoFileUpload, _Url, _Headline, fileToUpload, videoThumbnailUpload), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        boolean isError = jsonObject.getBoolean("error");
                        if (isError) {
                            Alerts.show(mContext, jsonObject.getString("message"));
                        } else {
                            AppPreference.setBooleanPreference(mContext, "NewPost", true);
                            Alerts.show(mContext, jsonObject.getString("message"));
                            refreshTimeline();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

}
