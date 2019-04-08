package com.pinlinx.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pinlinx.retrofit_provider.RetrofitApiClient;
import com.pinlinx.retrofit_provider.RetrofitService;

public class BaseFragment extends Fragment {

    public RetrofitApiClient retrofitApiClient;
    public ConnectionDetector cd;
    public Context mContext;
    public Activity activity;

    public BaseFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        activity = getActivity();
        mContext = getActivity();
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();
        return null;
    }
}