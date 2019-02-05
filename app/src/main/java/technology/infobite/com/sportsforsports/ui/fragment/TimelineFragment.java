package technology.infobite.com.sportsforsports.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.adapter.VideoRecyclerViewAdapter;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.DailyNewsFeedMainModal;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.Feed;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.ui.activity.PostDetailActivity;
import technology.infobite.com.sportsforsports.ui.activity.UserProfileActivity;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseFragment;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;
import technology.infobite.com.sportsforsports.utils.exoplayer.ExoPlayerRecyclerView;

import static android.widget.LinearLayout.VERTICAL;

public class TimelineFragment extends BaseFragment implements View.OnClickListener {

    private VideoRecyclerViewAdapter mAdapter;
    private boolean firstTime = true;

    /***********************************************/
    //private NewsFeedAdapter newPostAdapter;
    private View rootView;
    private List<Feed> feedList = new ArrayList<>();
    private DailyNewsFeedMainModal dailyNewsFeedMainModal;
    private ExoPlayerRecyclerView recyclerViewFeed;
    private int playPosition = -1;
    private String strId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_timeline, container, false);
        mContext = getActivity();
        retrofitApiClient = RetrofitService.getRetrofit();
        cd = new ConnectionDetector(mContext);
        strId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        timelineApi();
        return rootView;
    }

    private void init() {
        mContext = getActivity();
        recyclerViewFeed = rootView.findViewById(R.id.recyclerViewFeed);
        recyclerViewFeed.setVideoInfoList(feedList);
        mAdapter = new VideoRecyclerViewAdapter(mContext, feedList, this, retrofitApiClient);
        recyclerViewFeed.setLayoutManager(new LinearLayoutManager(mContext, VERTICAL, false));

        recyclerViewFeed.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFeed.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        if (firstTime) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    recyclerViewFeed.playVideo();
                }
            });
            firstTime = false;
        }
        recyclerViewFeed.scrollToPosition(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerViewFeed.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    recyclerViewFeed.playVideo();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llViewUserProfile:
                startActivity(new Intent(mContext, UserProfileActivity.class));
                break;
            case R.id.tvTotalComment:
            case R.id.llPostComment:
                int position = Integer.parseInt(v.getTag().toString());
                Gson gson = new GsonBuilder().setLenient().create();
                String data = gson.toJson(feedList.get(position));
                AppPreference.setStringPreference(mContext, Constant.POST_DETAIL, data);
                Intent intent = new Intent(mContext, PostDetailActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void timelineApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.showPostTimeLine(new Dialog(mContext), retrofitApiClient.showPostTimeLine(strId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    dailyNewsFeedMainModal = (DailyNewsFeedMainModal) result.body();
                    feedList.clear();
                    if (dailyNewsFeedMainModal.getError()) {
                        Alerts.show(mContext, dailyNewsFeedMainModal.getMessage());
                    } else {
                        Gson gson = new GsonBuilder().setLenient().create();
                        String data = gson.toJson(dailyNewsFeedMainModal);
                        AppPreference.setStringPreference(mContext, Constant.TIMELINE_DATA, data);

                        feedList.addAll(dailyNewsFeedMainModal.getFeed());
                        init();
                    }
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppPreference.getBooleanPreference(mContext, Constant.IS_DATA_UPDATE)) {
            feedList.clear();
            String strTimelineData = AppPreference.getStringPreference(mContext, Constant.TIMELINE_DATA);
            Gson gson = new Gson();
            dailyNewsFeedMainModal = gson.fromJson(strTimelineData, DailyNewsFeedMainModal.class);
            feedList.addAll(dailyNewsFeedMainModal.getFeed());
            mAdapter.notifyDataSetChanged();
            AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, false);
        }
    }
}
