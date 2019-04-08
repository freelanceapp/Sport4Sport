package com.pinlinx.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import retrofit2.Response;
import com.pinlinx.R;
import com.pinlinx.constant.Constant;
import com.pinlinx.modal.user_data.UserDataModal;
import com.pinlinx.retrofit_provider.RetrofitApiClient;
import com.pinlinx.retrofit_provider.RetrofitService;
import com.pinlinx.retrofit_provider.WebResponse;
import com.pinlinx.utils.Alerts;
import com.pinlinx.utils.AppPreference;
import com.pinlinx.utils.ConnectionDetector;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String EMAIL = "email";
    private CallbackManager callbackManager;

    private RetrofitApiClient retrofitApiClient;
    private ConnectionDetector cd;
    private Context mContext;
    private boolean isLogin = false;
    private LoginButton loginButton;

    private String email = "";

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

        String strKeyHash = printKeyHash(this);
        Log.e("Key_Hash:-", strKeyHash);
        initFacebook();
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    private void initFacebook() {

        FacebookSdk.sdkInitialize(mContext);
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        LoginManager.getInstance().logOut();
        loginButton.setReadPermissions(Arrays.asList(EMAIL));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
                                try {
                                    email = object.getString("email");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();

                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                            /*********************************/
                            String strName = currentProfile.getFirstName();
                            Uri uri = currentProfile.getProfilePictureUri(150, 150);
                            String profile = uri.toString();
                            String socialType = "Facebook";
                            String socialId = currentProfile.getId();
                            mProfileTracker.stopTracking();
                            //btnFb.setText("Logout from Facebook");
                            Log.e("facebook_profile_data:-", strName+"-"+socialId+"-"+email);
                            //Alerts.show(mContext,strName+"-"+socialId+"-"+email);
                            fbLoginApi(strName, email, profile, socialType, socialId);
                        }
                    };
                } else {
                    Profile profile = Profile.getCurrentProfile();
                    Log.e("facebook - profile", profile.getFirstName());
                    String strName = profile.getFirstName();
                    Uri uri = profile.getProfilePictureUri(150, 150);
                    String profileImage = uri.toString();
                    String socialType = "Facebook";
                    String socialId = profile.getId();
                    //Alerts.show(mContext,strName+"-"+socialId+"-"+email);
                    Log.e("facebook_profile_data:-", strName+"-"+socialId+"-"+email);
                    fbLoginApi(strName, email, profileImage, socialType, socialId);
                }
            }

            @Override
            public void onCancel() {
                Alerts.show(mContext, "Cancle");
            }

            @Override
            public void onError(FacebookException exception) {
                Alerts.show(mContext, exception.toString());
            }
        });
    }

    private void fbLoginApi(String name, String email, String profile, String socialType, String socialId) {
        if (cd.isNetworkAvailable()) {
            String strToken = AppPreference.getStringPreference(mContext, Constant.TOKEN);
            RetrofitService.getLoginData(new Dialog(mContext), retrofitApiClient.fbLogin(name, email,
                    profile, socialType, socialId, strToken), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    UserDataModal responseBody = (UserDataModal) result.body();
                    if (!responseBody.getError()) {
                        AppPreference.setStringPreference(mContext, Constant.USER_ID, responseBody.getUser().getUserId());
                        AppPreference.setStringPreference(mContext, Constant.USER_NAME, responseBody.getUser().getUserName());
                        AppPreference.setStringPreference(mContext, Constant.USER_IMAGE, responseBody.getUser().getAvtarImg());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }

        /*if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }*/
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
                //Alerts.show(mContext, "Under development !!");
                loginButton.performClick();
                break;
            case R.id.txtForgotPassword:
                startActivity(new Intent(mContext, ForgotPasswordActivity.class));
                break;
        }
    }
}
