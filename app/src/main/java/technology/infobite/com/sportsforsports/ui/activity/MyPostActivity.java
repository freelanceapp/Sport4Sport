package technology.infobite.com.sportsforsports.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Response;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitApiClient;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseActivity;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;

public class MyPostActivity extends BaseActivity implements View.OnClickListener {

    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_LOAD_VIDEO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        ((TextView) findViewById(R.id.tv_post_feed)).setOnClickListener(this);
        findViewById(R.id.img_Comment).setOnClickListener(this);
        findViewById(R.id.img_Camera).setOnClickListener(this);
        findViewById(R.id.img_Video_Camera).setOnClickListener(this);
        findViewById(R.id.tv_select_image).setOnClickListener(this);
        findViewById(R.id.tv_select_video).setOnClickListener(this);

    }

    private void newPostFeedApi() {

        String strId = AppPreference.getStringPreference(getApplicationContext(), Constant.USER_ID);
        String strAthleteStatus = "y";
        String strAthleteVideo = "";
        String strArticleUrl = Constant.DEMO_URL;
        String strArticleHeadline = ((EditText) findViewById(R.id.edit_new_headline)).getText().toString();
        String  strImage= "";
        if (cd.isNetworkAvailable()) {
            RetrofitService.getNewPostData(new Dialog(mContext), retrofitApiClient.newPostFeed(strId,
                    strAthleteStatus,
                    strAthleteVideo, strArticleUrl, strArticleHeadline, strImage), new WebResponse() {
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

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_post_feed:
                    newPostFeedApi();
                    break;
                case R.id.img_Comment:
                    (findViewById(R.id.edit_new_headline)).setVisibility(View.VISIBLE);
                    (findViewById(R.id.rl_image)).setVisibility(View.INVISIBLE);
                    (findViewById(R.id.rl_video)).setVisibility(View.INVISIBLE);
                    break;
                case R.id.img_Camera:
                    (findViewById(R.id.edit_new_headline)).setVisibility(View.VISIBLE);
                    (findViewById(R.id.rl_image)).setVisibility(View.VISIBLE);
                    (findViewById(R.id.rl_video)).setVisibility(View.INVISIBLE);
                    break;
                case R.id.img_Video_Camera:
                    (findViewById(R.id.edit_new_headline)).setVisibility(View.VISIBLE);
                    (findViewById(R.id.rl_image)).setVisibility(View.VISIBLE);
                    (findViewById(R.id.rl_video)).setVisibility(View.VISIBLE);
                    break;
                case R.id.tv_select_image:
                    Intent i = new Intent(
                            Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                    break;
                case R.id.tv_select_video:
                    Intent i1 = new Intent(
                            Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i1, RESULT_LOAD_VIDEO);
                    break;
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            final Uri uriImage = data.getData();
            final InputStream inputStream;
            try {
                inputStream = getContentResolver().openInputStream(uriImage);
                final Bitmap imageMap = BitmapFactory.decodeStream(inputStream);
                ((ImageView) findViewById(R.id.img_upload)).setImageBitmap(imageMap);
                ((TextView) findViewById(R.id.tv_select_image)).setVisibility(View.INVISIBLE);
            } catch (FileNotFoundException e) {
                Toast.makeText(this, "Image was not found", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            Uri video = data.getData();
            ((VideoView) findViewById(R.id.vid_upload)).setVideoURI(video);
            ((VideoView) findViewById(R.id.vid_upload)).start();
        }
/*
        if(resultCode == RESULT_OK){
            Uri uri = data.getData();
            try{
                videoField.setVideoURI(uri);
                videoField.start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }*/
    }
}
