package technology.infobite.com.sportsforsports.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.adapter.NewsFeedAdapter;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.Comment;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.DailyNewsFeedMainModal;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.Feed;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.ui.activity.UserProfileActivity;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseFragment;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;

public class TimelineFragment extends BaseFragment implements View.OnClickListener {
    private NewsFeedAdapter newPostAdapter;
    private View rootView;
    private List<Feed> newPostModels = new ArrayList<>();
    private RecyclerView newpostrclv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_timeline, container, false);
        mContext = getActivity();
        retrofitApiClient = RetrofitService.getRetrofit();
        cd = new ConnectionDetector(mContext);
        init();
        timelineApi();
        return rootView;
    }

    private void init() {
        newpostrclv = rootView.findViewById(R.id.new_post_rclv);
        newpostrclv.setHasFixedSize(true);
        newpostrclv.setLayoutManager(new LinearLayoutManager(mContext));


        newPostAdapter = new NewsFeedAdapter(newPostModels, mContext, this);
        LinearLayoutManager lm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        newpostrclv.setLayoutManager(lm);
        newpostrclv.setItemAnimator(new DefaultItemAnimator());
        newpostrclv.setAdapter(newPostAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llViewUserProfile:
                startActivity(new Intent(mContext, UserProfileActivity.class));
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
                    newPostModels.clear();
                    if (dailyNewsFeedMainModal.getError()) {
                        Alerts.show(mContext, dailyNewsFeedMainModal.getMessage());
                    } else {
                        newPostModels.addAll(dailyNewsFeedMainModal.getFeed());
                        Alerts.show(mContext, dailyNewsFeedMainModal.getMessage());
                    }
                    newPostAdapter.notifyDataSetChanged();

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
