package technology.infobite.com.sportsforsports.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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


public class RagistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private RetrofitApiClient retrofitApiClient;
    private ConnectionDetector cd;
    private Context mContext;
    private Button buttonregester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ragistration);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();
        buttonregester = findViewById(R.id.btn_register);
        buttonregester.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        signUpApi();
    }

    private void signUpApi() {
        String strname = ((EditText) findViewById(R.id.edtName)).getText().toString();
        String strEmail = ((EditText) findViewById(R.id.edtEmail)).getText().toString();
        String strPassword = ((EditText) findViewById(R.id.edtPassword)).getText().toString();
        String strToken = AppPreference.getStringPreference(mContext, Constant.TOKEN);

        if (strname.isEmpty()) {
            Alerts.show(mContext, "Please enter name!!!");
        } else if (strEmail.isEmpty()) {
            Alerts.show(mContext, "Please enter email!!!");
        } else if (!strEmail.matches(Constant.EmailPattern)) {
            Alerts.show(mContext, "Enter valid email!!!");
        } else if (strPassword.isEmpty()) {
            Alerts.show(mContext, "Please enter password!!!");
        } else if (strPassword.length() < 6) {
            Alerts.show(mContext, "Password length more than 5");
        } else if (strPassword.matches("[a-zA-Z0-9.? ]*")) {
            Alerts.show(mContext, "Password must contains capital, small, number and special characters !!!");
        } else {
            if (cd.isNetworkAvailable()) {
                RetrofitService.getLoginData(new Dialog(mContext), retrofitApiClient.signUp(strname, strEmail,
                        strPassword, strToken), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        UserDataModal responseBody = (UserDataModal) result.body();
                        if (!responseBody.getError()) {
                            Alerts.show(mContext, responseBody.getMessage());
                            AppPreference.setStringPreference(mContext, Constant.USER_ID, responseBody.getUser().getUserId());
                            AppPreference.setStringPreference(mContext, Constant.USER_NAME, responseBody.getUser().getUserName());
                            AppPreference.setStringPreference(mContext, Constant.USER_IMAGE, responseBody.getUser().getAvtarImg());
                            Intent intent = new Intent(mContext, CreateProfileActivity.class);
                            intent.putExtra("user_id", responseBody.getUser().getUserId());
                            intent.putExtra("name", responseBody.getUser().getUserName());
                            startActivity(intent);
                            finish();
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
}
