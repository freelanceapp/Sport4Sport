package com.pinlinx.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;
import com.pinlinx.R;
import com.pinlinx.retrofit_provider.RetrofitService;
import com.pinlinx.retrofit_provider.WebResponse;
import com.pinlinx.utils.Alerts;
import com.pinlinx.utils.BaseActivity;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {

    private String strType = "otp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();
    }

    private void init() {
        SwitchCompat switchCompat = findViewById(R.id.switchCompat);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((EditText) findViewById(R.id.edtNewPassword)).setTransformationMethod(new HideReturnsTransformationMethod());
                    ((EditText) findViewById(R.id.edtConfirmPassword)).setTransformationMethod(new HideReturnsTransformationMethod());
                } else {
                    ((EditText) findViewById(R.id.edtNewPassword)).setTransformationMethod(new PasswordTransformationMethod());
                    ((EditText) findViewById(R.id.edtConfirmPassword)).setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        findViewById(R.id.btnSubmit).setOnClickListener(this);
        findViewById(R.id.imgBack).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                if (strType.equalsIgnoreCase("otp")) {
                    sendOtp();
                } else if (strType.equalsIgnoreCase("verify_otp")) {
                    verifyOtp();
                } else if (strType.equalsIgnoreCase("new_password")) {
                    newPasswordApi();
                }
                break;
            case R.id.imgBack:
                finish();
                break;
        }
    }

    private void sendOtp() {
        String strEmail = ((EditText) findViewById(R.id.edtEmail)).getText().toString();

        if (strEmail.isEmpty()) {
            Alerts.show(mContext, "Please enter email id");
        } else {
            if (cd.isNetworkAvailable()) {
                RetrofitService.getContentData(new Dialog(mContext), retrofitApiClient.emailOtp(strEmail), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        ResponseBody responseBody = (ResponseBody) result.body();
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody.string());
                            if (!jsonObject.getBoolean("error")) {
                                strType = "verify_otp";
                                findViewById(R.id.edtEmail).setFocusable(false);
                                findViewById(R.id.edtOtp).setVisibility(View.VISIBLE);
                                findViewById(R.id.llPassword).setVisibility(View.GONE);
                                ((Button) findViewById(R.id.btnSubmit)).setText("Verify");
                                Alerts.show(mContext, "OTP send successfully, please verify your email-id...!");
                            } else {
                                findViewById(R.id.edtOtp).setVisibility(View.GONE);
                                ((Button) findViewById(R.id.btnSubmit)).setText("Submit");
                                findViewById(R.id.edtEmail).setFocusable(true);
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

                    }
                });
            } else {
                cd.show(mContext);
            }
        }
    }

    private void verifyOtp() {
        String strEmail = ((EditText) findViewById(R.id.edtEmail)).getText().toString();
        String strOtp = ((EditText) findViewById(R.id.edtOtp)).getText().toString();

        if (strEmail.isEmpty()) {
            Alerts.show(mContext, "Please enter email id");
        } else {
            if (cd.isNetworkAvailable()) {
                RetrofitService.getContentData(new Dialog(mContext), retrofitApiClient.emailVerify(strEmail, strOtp), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        ResponseBody responseBody = (ResponseBody) result.body();
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody.string());
                            ((Button) findViewById(R.id.btnSubmit)).setText("Submit");
                            if (!jsonObject.getBoolean("error")) {
                                strType = "new_password";
                                findViewById(R.id.llPassword).setVisibility(View.VISIBLE);
                                findViewById(R.id.edtOtp).setVisibility(View.GONE);
                                Alerts.show(mContext, "Email verify , please create your new password");
                            } else {
                                findViewById(R.id.llPassword).setVisibility(View.GONE);
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

                    }
                });
            } else {
                cd.show(mContext);
            }
        }
    }

    private void newPasswordApi() {
        String strEmail = ((EditText) findViewById(R.id.edtEmail)).getText().toString();
        String strPassword = ((EditText) findViewById(R.id.edtNewPassword)).getText().toString();
        String strConfirmPassword = ((EditText) findViewById(R.id.edtConfirmPassword)).getText().toString();

        if (strEmail.isEmpty()) {
            Alerts.show(mContext, "Please enter email id");
        } else if (strPassword.isEmpty()) {
            Alerts.show(mContext, "Please enter password");
        } else if (strPassword.length() < 7) {
            Alerts.show(mContext, "Password length more than 7");
        } else if (strPassword.matches("[a-zA-Z0-9.? ]*")) {
            Alerts.show(mContext, "Password must contains capital, small, number and special characters !!!");
        } else if (strConfirmPassword.isEmpty()) {
            Alerts.show(mContext, "Please enter confirm password");
        } else if (!strPassword.equals(strConfirmPassword)) {
            Alerts.show(mContext, "Password not match");
        } else {
            if (cd.isNetworkAvailable()) {
                RetrofitService.getContentData(new Dialog(mContext), retrofitApiClient.newPassword(strEmail, strPassword), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        ResponseBody responseBody = (ResponseBody) result.body();
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody.string());
                            if (!jsonObject.getBoolean("error")) {
                                Alerts.show(mContext, "Password update successfully, please login to continue...!");
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

                    }
                });
            } else {
                cd.show(mContext);
            }
        }
    }
}
