package com.pinlinx.firebase_utils;

import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import retrofit2.Response;
import com.pinlinx.constant.Constant;
import com.pinlinx.retrofit_provider.RetrofitApiClient;
import com.pinlinx.retrofit_provider.RetrofitService;
import com.pinlinx.retrofit_provider.WebResponse;
import com.pinlinx.utils.Alerts;
import com.pinlinx.utils.AppPreference;
import com.pinlinx.utils.ConnectionDetector;
import com.pinlinx.utils.NotificationHelper;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private String TAG = "message_data";

    public RetrofitApiClient retrofitApiClient;
    public ConnectionDetector cd;
    public Context mContext;
    private NotificationHelper notificationHelper;

    @Override
    public void onNewToken(String s) {

        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();

        Log.d(TAG, "Refreshed token: " + s);
        AppPreference.setStringPreference(getApplicationContext(), Constant.TOKEN, s);

        if (AppPreference.getBooleanPreference(getApplicationContext(), Constant.LOGIN_API)) {
            updateToken(s);
        }
    }

    private void updateToken(String strToken) {
        String strUserId = AppPreference.getStringPreference(mContext, Constant.TOKEN);
        if (cd.isNetworkAvailable()) {
            RetrofitService.getLikeResponse(retrofitApiClient.updateToken(strUserId, strToken), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.e(TAG, "sendRegistrationToServer: " + response.message());
                    } else {
                        Log.e(TAG, "sendRegistrationToServer: " + response.message());
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
    public void onMessageReceived(RemoteMessage remoteMessage) {
        mContext = this;
        notificationHelper = new NotificationHelper(mContext);
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
            String strPayload = "" + remoteMessage.getData();
            Log.e(TAG, "StrPayload: " + strPayload);

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                JSONObject jsonObject = json.getJSONObject("message");
                String strTitle = jsonObject.getString("title");
                String strBody = jsonObject.getString("body");

                String strPostId = "";
                String strGetFrom = "";
                String strFanId = "";
                if (strTitle.equalsIgnoreCase("Comment") || strTitle.equalsIgnoreCase("Like")) {
                    strPostId = json.getString("post_id");
                    strGetFrom = json.getString("get_from");
                } else {
                    strFanId = json.getString("fan_id");
                }

                notificationHelper.showNotification(strTitle, strBody, strPostId, strGetFrom, strFanId);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            String strPayload = "" + remoteMessage.getNotification().getBody();
            Log.e(TAG, "NotiBody: " + strPayload);
        }
    }
}