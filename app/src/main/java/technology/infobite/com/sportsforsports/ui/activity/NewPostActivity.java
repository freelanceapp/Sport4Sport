package technology.infobite.com.sportsforsports.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.upload_with_progress.ProgressRequestBody;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseActivity;

public class NewPostActivity extends BaseActivity implements View.OnClickListener, ProgressRequestBody.UploadCallbacks {

    private static final int PICK_FROM_GALLERY = 1;
    private File imageFile = null, videoFile = null;
    private static int VIDEO_FROM_GALLERY = 1;
    private String imagePath = "", strVideoPath = "";
    private String strPostType = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        ((TextView) findViewById(R.id.tv_post_feed)).setOnClickListener(this);
        findViewById(R.id.img_Comment).setOnClickListener(this);
        findViewById(R.id.img_Camera).setOnClickListener(this);
        findViewById(R.id.img_Video_Camera).setOnClickListener(this);
        findViewById(R.id.tv_select_image).setOnClickListener(this);
        findViewById(R.id.tv_select_video).setOnClickListener(this);
    }

    private void checkValidation() {
        String strNewThoughts = ((EditText) findViewById(R.id.edit_new_headline)).getText().toString();
        if (strPostType.equals("text")) {
            if (strNewThoughts.isEmpty()) {
                Alerts.show(mContext, "Please enter some text for post!!!");
            } else {
                newPostFeedApi();
            }
        } else if (strPostType.equals("image")) {
            if (imageFile == null) {
                Alerts.show(mContext, "Please select image for post!!!");
            } else {
                newPostFeedApi();
            }
        } else if (strPostType.equals("video")) {
            if (videoFile == null) {
                Alerts.show(mContext, "Please select video for post!!!");
            } else {
                newPostFeedApi();
            }
        }
    }

    private void newPostFeedApi() {
        String strId = AppPreference.getStringPreference(getApplicationContext(), Constant.USER_ID);
        String strAthleteStatus = ((EditText) findViewById(R.id.edtPostDescription)).getText().toString();
        String strArticleUrl = Constant.DEMO_URL;
        String strArticleHeadline = ((EditText) findViewById(R.id.edit_new_headline)).getText().toString();

        if (cd.isNetworkAvailable()) {
            RequestBody _id = RequestBody.create(MediaType.parse("text/plain"), strId);
            RequestBody _Status = RequestBody.create(MediaType.parse("text/plain"), strAthleteStatus);
            RequestBody _Url = RequestBody.create(MediaType.parse("text/plain"), strArticleUrl);
            RequestBody _Headline = RequestBody.create(MediaType.parse("text/plain"), strArticleHeadline);

            MultipartBody.Part fileToUpload = null;
            MultipartBody.Part videoFileUpload = null;

            if (strPostType.equals("image")) {
                RequestBody imageBodyFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
                fileToUpload = MultipartBody.Part.createFormData("alhlete_images", imageFile.getName(),
                        imageBodyFile);
            } else if (strPostType.equals("video")) {
                ProgressRequestBody fileBody = new ProgressRequestBody(videoFile, "video/mp4", this);
                videoFileUpload = MultipartBody.Part.createFormData("athlete_video", videoFile.getName(), fileBody);
            }

            RetrofitService.getNewPostData(new Dialog(mContext), retrofitApiClient.newPostFeed(_id, _Status,
                    videoFileUpload, _Url, _Headline, fileToUpload), new WebResponse() {
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
                            finish();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_post_feed:
                checkValidation();
                break;
            case R.id.img_Comment:
                strPostType = "text";
                ((EditText) findViewById(R.id.edtPostDescription)).setText("");
                (findViewById(R.id.edit_new_headline)).setVisibility(View.VISIBLE);
                (findViewById(R.id.viewBottom)).setVisibility(View.GONE);
                (findViewById(R.id.edtPostDescription)).setVisibility(View.GONE);
                (findViewById(R.id.rl_image)).setVisibility(View.GONE);
                (findViewById(R.id.rl_video)).setVisibility(View.GONE);
                break;
            case R.id.img_Camera:
                strPostType = "image";
                ((EditText) findViewById(R.id.edit_new_headline)).setText("");
                (findViewById(R.id.viewBottom)).setVisibility(View.VISIBLE);
                (findViewById(R.id.edtPostDescription)).setVisibility(View.VISIBLE);
                (findViewById(R.id.edit_new_headline)).setVisibility(View.GONE);
                (findViewById(R.id.rl_image)).setVisibility(View.VISIBLE);
                (findViewById(R.id.rl_video)).setVisibility(View.GONE);
                break;
            case R.id.img_Video_Camera:
                strPostType = "video";
                ((EditText) findViewById(R.id.edit_new_headline)).setText("");
                (findViewById(R.id.viewBottom)).setVisibility(View.VISIBLE);
                (findViewById(R.id.edtPostDescription)).setVisibility(View.VISIBLE);
                (findViewById(R.id.edit_new_headline)).setVisibility(View.GONE);
                (findViewById(R.id.rl_image)).setVisibility(View.GONE);
                (findViewById(R.id.rl_video)).setVisibility(View.VISIBLE);
                break;
            case R.id.tv_select_image:
                try {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(NewPostActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_select_video:
                Intent intent = new Intent(mContext, VideoActivity.class);
                startActivityForResult(intent, 786);
                /*try {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(NewPostActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, VIDEO_FROM_GALLERY);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, VIDEO_FROM_GALLERY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    Alerts.show(mContext, "Please give permission");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && null != data) {
            final Uri uriImage = data.getData();
            final InputStream inputStream;
            try {
                inputStream = getContentResolver().openInputStream(uriImage);
                final Bitmap imageMap = BitmapFactory.decodeStream(inputStream);
                ((ImageView) findViewById(R.id.img_upload)).setImageBitmap(imageMap);

                imagePath = getPath(uriImage);
                imageFile = new File(imagePath);
                ((TextView) findViewById(R.id.tv_select_image)).setVisibility(View.INVISIBLE);
            } catch (FileNotFoundException e) {
                Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else if (requestCode == VIDEO_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri video = data.getData();
            strVideoPath = getPath(video);
            videoFile = new File(strVideoPath);
            ((VideoView) findViewById(R.id.vid_upload)).setVideoURI(video);
            ((VideoView) findViewById(R.id.vid_upload)).start();
        } else if (requestCode == 786) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("crop_video");
                String thumb = data.getStringExtra("video_thumb");
                Uri video = Uri.parse(result);
                Glide.with(mContext).load("file://" + thumb)
                        .into(((ImageView) findViewById(R.id.imgVideoThumb)));

                videoFile = new File(result);
                ((VideoView) findViewById(R.id.vid_upload)).setVideoURI(video);
                ((VideoView) findViewById(R.id.vid_upload)).start();
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String strPath = cursor.getString(column_index);
        cursor.close();
        return strPath;
    }

    @Override
    public void onProgressUpdate(int percentage) {
        ((TextView) findViewById(R.id.tvProgress)).setText(percentage + "");
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {
        ((TextView) findViewById(R.id.tvProgress)).setText("Finished");
    }
}
