package technology.infobite.com.sportsforsports.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import technology.infobite.com.sportsforsports.modal.daily_news_feed.UserFeed;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.ui.activity.MyPostDetailActivity;
import technology.infobite.com.sportsforsports.ui.activity.PostDetailActivity;
import technology.infobite.com.sportsforsports.ui.activity.UserProfileActivity;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseFragment;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;
import technology.infobite.com.sportsforsports.utils.exoplayer.ExoPlayerRecyclerView;

import static android.widget.LinearLayout.VERTICAL;
import static technology.infobite.com.sportsforsports.ui.activity.HomeActivity.fragmentManager;

public class TimelineFragment extends BaseFragment implements View.OnClickListener {

    private VideoRecyclerViewAdapter mAdapter;
    private boolean firstTime = true;
    private SwipeRefreshLayout swipeRefreshLayout;

    /***********************************************/
    //private NewsFeedAdapter newPostAdapter;
    private View rootView;
    private List<UserFeed> feedList = new ArrayList<>();
    private DailyNewsFeedMainModal dailyNewsFeedMainModal;
    private ExoPlayerRecyclerView recyclerViewFeed;
    private int playPosition = -1;
    private String strId;
    private String strUserId = "";

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
        strUserId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                timelineApi();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTotalComment:
            case R.id.rlPost:
            case R.id.llPostComment:
                int position = Integer.parseInt(v.getTag().toString());
                Gson gson = new GsonBuilder().setLenient().create();
                String data = gson.toJson(feedList.get(position));
                String postUserId = feedList.get(position).getPostUserId();
                AppPreference.setStringPreference(mContext, Constant.POST_DETAIL, data);
                Intent intent = null;
                if (postUserId.equals(strUserId)) {
                    intent = new Intent(mContext, MyPostDetailActivity.class);
                } else {
                    intent = new Intent(mContext, PostDetailActivity.class);
                }
                intent.putExtra("get_from", "timeline");
                intent.putExtra("post_id", feedList.get(position).getFeedId());
                startActivity(intent);
                break;
            case R.id.llViewUserProfile:
                int pos = Integer.parseInt(v.getTag().toString());
                String strPostUserId = feedList.get(pos).getPostUserId();
                if (strPostUserId.equals(strUserId)) {
                    Fragment ProfileFragment = fragmentManager.findFragmentByTag(Constant.ProfileFragment);
                    if (ProfileFragment == null) {
                        changeFragment(new ProfileFragment(), Constant.ProfileFragment);
                    }
                } else {
                    Intent intentPostUserId = new Intent(mContext, UserProfileActivity.class);
                    intentPostUserId.putExtra("fan_id", strPostUserId);
                    startActivity(intentPostUserId);
                }
                break;
        }
    }

    private void changeFragment(Fragment fragment, String strTag) {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.fram_container, fragment,
                        strTag).commit();
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

                        if (dailyNewsFeedMainModal.getFeed().size() > 0) {
                            feedList.addAll(dailyNewsFeedMainModal.getFeed());
                        }
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
        //recyclerViewFeed.onRelease();
        if (AppPreference.getBooleanPreference(mContext, Constant.IS_DATA_UPDATE)) {
            feedList.clear();
            String strTimelineData = AppPreference.getStringPreference(mContext, Constant.TIMELINE_DATA);
            Gson gson = new Gson();
            dailyNewsFeedMainModal = gson.fromJson(strTimelineData, DailyNewsFeedMainModal.class);
            feedList.addAll(dailyNewsFeedMainModal.getFeed());
            if (mAdapter != null)
                mAdapter.notifyDataSetChanged();
            AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, false);
        }
    }
}
