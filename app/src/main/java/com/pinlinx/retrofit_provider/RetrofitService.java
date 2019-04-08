package com.pinlinx.retrofit_provider;

import android.app.Dialog;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.pinlinx.constant.Constant;
import com.pinlinx.modal.all_user_list_modal.AllUserMainModal;
import com.pinlinx.modal.daily_news_feed.DailyNewsFeedMainModal;
import com.pinlinx.modal.league.LeagueMainModal;
import com.pinlinx.modal.notification_list_modal.NotificationMainModal;
import com.pinlinx.modal.post_comment_modal.PostCommentResponseModal;
import com.pinlinx.modal.user_data.UserDataModal;
import com.pinlinx.utils.AppProgressDialog;

//import technology.sportsforsports.modal.post_feed.PostFeedMainModal;

public class RetrofitService {

    public static RetrofitApiClient client;

    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.MINUTES)
            .connectTimeout(20, TimeUnit.MINUTES)
            .build();

    public RetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        client = retrofit.create(RetrofitApiClient.class);
    }

    public static RetrofitApiClient getRxClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();
        return retrofit.create(RetrofitApiClient.class);
    }

    public static RetrofitApiClient getRetrofit() {

        if (client == null)
            new RetrofitService();
        return client;
    }

    public static void getContentData(final Dialog dialog, final Call<ResponseBody> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getLikeResponse(final Call<ResponseBody> method, final WebResponse webResponse) {
        method.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getLoginData(final Dialog dialog, final Call<UserDataModal> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<UserDataModal>() {
            @Override
            public void onResponse(Call<UserDataModal> call, Response<UserDataModal> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<UserDataModal> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getRefreshLoginData(final Call<UserDataModal> method, final WebResponse webResponse) {

        method.enqueue(new Callback<UserDataModal>() {
            @Override
            public void onResponse(Call<UserDataModal> call, Response<UserDataModal> response) {
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<UserDataModal> call, Throwable throwable) {
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getNewPostData(final Dialog dialog, final Call<ResponseBody> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    //Daily news Field
    public static void showPostTimeLine(final Dialog dialog, final Call<DailyNewsFeedMainModal> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<DailyNewsFeedMainModal>() {
            @Override
            public void onResponse(Call<DailyNewsFeedMainModal> call, Response<DailyNewsFeedMainModal> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<DailyNewsFeedMainModal> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void refreshTimeLine(final Call<DailyNewsFeedMainModal> method, final WebResponse webResponse) {
        method.enqueue(new Callback<DailyNewsFeedMainModal>() {
            @Override
            public void onResponse(Call<DailyNewsFeedMainModal> call, Response<DailyNewsFeedMainModal> response) {
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<DailyNewsFeedMainModal> call, Throwable throwable) {
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    // post comment field
    public static void postCommentResponse(final Call<PostCommentResponseModal> method, final WebResponse webResponse) {
        method.enqueue(new Callback<PostCommentResponseModal>() {
            @Override
            public void onResponse(Call<PostCommentResponseModal> call, Response<PostCommentResponseModal> response) {
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<PostCommentResponseModal> call, Throwable throwable) {
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getAllUserData(final Dialog dialog, final Call<AllUserMainModal> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<AllUserMainModal>() {
            @Override
            public void onResponse(Call<AllUserMainModal> call, Response<AllUserMainModal> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<AllUserMainModal> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getNotificationList(final Dialog dialog, final Call<NotificationMainModal> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<NotificationMainModal>() {
            @Override
            public void onResponse(Call<NotificationMainModal> call, Response<NotificationMainModal> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<NotificationMainModal> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getLeagueList(final Dialog dialog, final Call<LeagueMainModal> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<LeagueMainModal>() {
            @Override
            public void onResponse(Call<LeagueMainModal> call, Response<LeagueMainModal> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<LeagueMainModal> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

}