package technology.infobite.com.sportsforsports.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import technology.infobite.com.sportsforsports.CommentModel1;
import technology.infobite.com.sportsforsports.NewPostModel;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.adapter.NewsFeedAdapter;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.ui.activity.UserProfileActivity;
import technology.infobite.com.sportsforsports.utils.BaseFragment;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;

public class TimelineFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private List<NewPostModel> newPostModels = new ArrayList<>();
    private RecyclerView newpostrclv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_timeline, container, false);
        mContext = getActivity();
        retrofitApiClient = RetrofitService.getRetrofit();
        cd = new ConnectionDetector(mContext);
        init();
        return rootView;
    }

    private void init() {
        newpostrclv = rootView.findViewById(R.id.new_post_rclv);
        newpostrclv.setHasFixedSize(true);
        newpostrclv.setLayoutManager(new LinearLayoutManager(mContext));



        for (int j = 0; j <= 35; j++) {
            newPostModels.add(new NewPostModel(R.drawable.player_image, "David Beckham", R.drawable.player_image
                    , "2.206 likes", "5000 comments", "2 HOURS AGO",
                    getResources().getString(R.string.demo_text), "View all 24 comment"));
        }

        NewsFeedAdapter newPostAdapter = new NewsFeedAdapter(newPostModels, mContext, this);
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
}
