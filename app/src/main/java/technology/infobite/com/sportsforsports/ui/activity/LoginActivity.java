package technology.infobite.com.sportsforsports.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;
import technology.infobite.com.sportsforsports.Profile_Camera;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitApiClient;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonlogin, commentpage;
    TextView textView, cameractiivty;

    public RetrofitApiClient retrofitApiClient;
    public ConnectionDetector cd;
    public Context mContext;
    private CheckBox checkBoxRemember;
    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();

        init();
    }

    private void init() {
        buttonlogin = findViewById(R.id.btn_login);
        checkBoxRemember = (CheckBox) findViewById(R.id.checkBoxRemember);
        checkBoxRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isLogin = true;
                } else {
                    isLogin = false;
                }
            }
        });
        String text = "<font color=#ffffff>I accept </font> <font color=#000000> Terms </font> " +
                "<font color=#ffffff> and Privacy </font> <font color=#000000> settings ! </font>";
        //checkBoxAccept.setText(Html.fromHtml(text));

        cameractiivty = findViewById(R.id.cameradata);
        cameractiivty.setOnClickListener(this);
        buttonlogin.setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.llRegister)).setOnClickListener(this);
    }

    private void loginApi() {
        String strEmail = ((EditText) findViewById(R.id.edtEmail)).getText().toString();
        String strPassword = ((EditText) findViewById(R.id.edtPassword)).getText().toString();

        if (strEmail.isEmpty()) {
            Alerts.show(mContext, "Please enter email!!!");
        } else if (strPassword.isEmpty()) {
            Alerts.show(mContext, "Please enter password!!!");
        } else {
            if (cd.isNetworkAvailable()) {
                RetrofitService.getContentData(new Dialog(mContext), retrofitApiClient.signIn(strEmail, strPassword), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        ResponseBody responseBody = (ResponseBody) result.body();
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody.string());
                            if (!jsonObject.getBoolean("error")) {
                                Alerts.show(mContext, jsonObject.getString("message"));
                                startActivity(new Intent(mContext, NewPostsActivity.class));
                                finish();
                            } else {
                                Alerts.show(mContext, jsonObject.getString("message"));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cameradata:
                Intent intent = new Intent(LoginActivity.this, Profile_Camera.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                if (isLogin) {
                    AppPreference.setBooleanPreference(mContext, Constant.LOGIN_API, true);
                }
                loginApi();
                break;
            case R.id.llRegister:
                Intent intentB = new Intent(LoginActivity.this, RagistrationActivity.class);
                startActivity(intentB);
                break;
        }
    }
}
