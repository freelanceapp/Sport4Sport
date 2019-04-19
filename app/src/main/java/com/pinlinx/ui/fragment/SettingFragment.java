package com.pinlinx.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.pinlinx.R;
import com.pinlinx.retrofit_provider.RetrofitService;
import com.pinlinx.ui.activity.LoginActivity;
import com.pinlinx.ui.activity.TermsPolicyActivity;
import com.pinlinx.utils.AppPreference;
import com.pinlinx.utils.BaseFragment;
import com.pinlinx.utils.ConnectionDetector;

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        mContext = getActivity();
        retrofitApiClient = RetrofitService.getRetrofit();
        cd = new ConnectionDetector(mContext);
        init();
        return rootView;
    }

    private void init() {
        rootView.findViewById(R.id.llTerms).setOnClickListener(this);
        rootView.findViewById(R.id.llPrivacy).setOnClickListener(this);
        rootView.findViewById(R.id.llLogout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llTerms:
                Intent intent = new Intent(mContext, TermsPolicyActivity.class);
                intent.putExtra("type", "terms");
                startActivity(intent);
                break;
            case R.id.llPrivacy:
                Intent intentP = new Intent(mContext, TermsPolicyActivity.class);
                intentP.putExtra("type", "privacy");
                startActivity(intentP);
                break;
            case R.id.llLogout:
                logout();
                break;
        }
    }

    private void logout() {
        new AlertDialog.Builder(mContext)
                .setTitle("Logout")
                .setMessage("Are you sure want to logout ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logoutFromFacebook();
                        AppPreference.clearAllPreferences(mContext);
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("NO", null)
                .create()
                .show();
    }

    public void logoutFromFacebook() {
        FacebookSdk.sdkInitialize(mContext);
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // user already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                LoginManager.getInstance().logOut();
            }
        }).executeAsync();
    }

}
