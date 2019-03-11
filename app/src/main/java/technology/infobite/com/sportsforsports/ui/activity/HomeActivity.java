package technology.infobite.com.sportsforsports.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import technology.infobite.com.sportsforsports.BuildConfig;
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
    private final int REQUEST_TAKE_PHOTO = 124;
    private final int LOAD_IMAGE_CAMERA = 125;
    private File mPhotoFile;

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
                requestStoragePermission();
                break;
        }
    }

    private void requestStoragePermission() {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            dispatchTakePictureIntent();
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Alerts.show(mContext, "Please allow for permissions");
            }
        })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                HomeActivity.this.openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        requestStoragePermission();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO) {
            newPostFeedApi(mPhotoFile);
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
