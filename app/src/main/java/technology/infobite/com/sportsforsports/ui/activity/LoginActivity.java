package technology.infobite.com.sportsforsports.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import retrofit2.Response;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.user_data.UserDataModal;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitApiClient;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String EMAIL = "email";
    private CallbackManager callbackManager;

    private RetrofitApiClient retrofitApiClient;
    private ConnectionDetector cd;
    private Context mContext;
    private boolean isLogin = false;

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
        Button buttonlogin = findViewById(R.id.btn_login);
        CheckBox checkBoxRemember = findViewById(R.id.checkBoxRemember);
        checkBoxRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isLogin = isChecked;
            }
        });

        buttonlogin.setOnClickListener(this);
        findViewById(R.id.txtForgotPassword).setOnClickListener(this);
        findViewById(R.id.llRegister).setOnClickListener(this);
        findViewById(R.id.btnFb).setOnClickListener(this);
        //initFacebook();
    }

    private void initFacebook() {
        LoginButton loginButton = null;
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        //loginButton = (LoginButton) findViewById(R.id.login_button);
        LoginManager.getInstance().logOut();
        loginButton.setReadPermissions(Arrays.asList(EMAIL));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(LoginResult loginResult) {
                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            Log.e("facebook - profile", currentProfile.getFirstName());
                            String strName = currentProfile.getFirstName();
                            String strUsername = currentProfile.getFirstName();
                            Alerts.show(mContext, strName);
                            mProfileTracker.stopTracking();
                            //btnFb.setText("Logout from Facebook");
                        }
                    };
                } else {
                    Profile profile = Profile.getCurrentProfile();
                    Log.e("facebook - profile", profile.getFirstName());
                    String strName = profile.getFirstName();
                    String strUsername = profile.getFirstName();
                    Alerts.show(mContext, strName);
                }
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    private void loginApi() {
        String strEmail = ((EditText) findViewById(R.id.edtEmail)).getText().toString();
        String strPassword = ((EditText) findViewById(R.id.edtPassword)).getText().toString();
        String strToken = AppPreference.getStringPreference(mContext, Constant.TOKEN);

        if (strEmail.isEmpty()) {
            Alerts.show(mContext, "Please enter email!!!");
        } else if (strPassword.isEmpty()) {
            Alerts.show(mContext, "Please enter password!!!");
        } else {
            if (cd.isNetworkAvailable()) {
                RetrofitService.getLoginData(new Dialog(mContext), retrofitApiClient.signIn(strEmail, strPassword, strToken), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        UserDataModal responseBody = (UserDataModal) result.body();
                        if (!responseBody.getError()) {
                            AppPreference.setStringPreference(mContext, Constant.USER_ID, responseBody.getUser().getUserId());
                            AppPreference.setStringPreference(mContext, Constant.USER_NAME, responseBody.getUser().getUserName());
                            AppPreference.setStringPreference(mContext, Constant.USER_IMAGE, responseBody.getUser().getAvtarImg());
                            Alerts.show(mContext, responseBody.getMessage());
                            if (responseBody.getUser().getDob() == null || responseBody.getUser().getDob().isEmpty()) {
                                Alerts.show(mContext, "Please create profile first");
                                Intent intent = new Intent(mContext, CreateProfileActivity.class);
                                intent.putExtra("user_id", responseBody.getUser().getUserId());
                                intent.putExtra("name", responseBody.getUser().getUserName());
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(mContext, HomeActivity.class);
                                intent.putExtra("user_id", responseBody.getUser().getUserId());
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Alerts.show(mContext, responseBody.getMessage());
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
            case R.id.btnFb:
                Alerts.show(mContext, "Under development !!");
                //loginButton.performClick();
                break;
            case R.id.txtForgotPassword:
                startActivity(new Intent(mContext, ForgotPasswordActivity.class));
                break;
        }
    }
}
