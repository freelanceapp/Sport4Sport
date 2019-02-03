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
    /*private RecyclerView newpostrclv;*/
    private ExoPlayerRecyclerView recyclerViewFeed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_timeline, container, false);
        mContext = getActivity();
        retrofitApiClient = RetrofitService.getRetrofit();
        cd = new ConnectionDetector(mContext);
        timelineApi();
        return rootView;
    }

    private void init() {
        mContext = getActivity();
        /*newpostrclv = rootView.findViewById(R.id.new_post_rclv);
        newpostrclv.setHasFixedSize(true);
        newpostrclv.setLayoutManager(new LinearLayoutManager(mContext));

        newPostAdapter = new NewsFeedAdapter(feedList, mContext, this);
        LinearLayoutManager lm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        newpostrclv.setLayoutManager(lm);
        newpostrclv.setItemAnimator(new DefaultItemAnimator());
        newpostrclv.setAdapter(newPostAdapter);*/

        recyclerViewFeed = rootView.findViewById(R.id.recyclerViewFeed);
        recyclerViewFeed.setVideoInfoList(feedList);
        mAdapter = new VideoRecyclerViewAdapter(mContext,feedList);
        recyclerViewFeed.setLayoutManager(new LinearLayoutManager(mContext, VERTICAL, false));
        //Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_drawable);
        //recyclerViewFeed.addItemDecoration(new DividerItemDecoration(dividerDrawable));
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
            case R.id.llLikePost:
                Alerts.show(mContext, "Under development!!!");
                break;
            case R.id.llPostComment:
                Alerts.show(mContext, "Under development!!!");
                break;
        }
    }

    private void timelineApi() {
        String strId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        if (cd.isNetworkAvailable()) {
            RetrofitService.showPostTimeLine(new Dialog(mContext), retrofitApiClient.showPostTimeLine(strId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    DailyNewsFeedMainModal dailyNewsFeedMainModal = (DailyNewsFeedMainModal) result.body();
                    feedList.clear();
                    if (dailyNewsFeedMainModal.getError()) {
                        Alerts.show(mContext, dailyNewsFeedMainModal.getMessage());
                    } else {
                        feedList.addAll(dailyNewsFeedMainModal.getFeed());
                        init();
                    }
                    //newPostAdapter.notifyDataSetChanged();
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
       /* init();
        timelineApi();*/
    }
}
