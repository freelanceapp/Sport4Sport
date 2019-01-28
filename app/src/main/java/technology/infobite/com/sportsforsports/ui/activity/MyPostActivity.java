package technology.infobite.com.sportsforsports.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        ((TextView) findViewById(R.id.tv_post_feed)).setOnClickListener(this);

    }

    private void newPostFeedApi() {

        String strId = AppPreference.getStringPreference(getApplicationContext(), Constant.USER_ID);
        String strAthleteStatus = "y";
        String strAthleteVideo = "";
        String strArticleUrl = Constant.DEMO_URL;
        String strArticleHeadline = ((EditText) findViewById(R.id.edit_new_headline)).getText().toString();
        String strImage = "";
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
            }
    }
}
