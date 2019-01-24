package technology.infobite.com.sportsforsports.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.ui.activity.LoginActivity;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseFragment;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;

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
        ((LinearLayout) rootView.findViewById(R.id.llLogout)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
}
