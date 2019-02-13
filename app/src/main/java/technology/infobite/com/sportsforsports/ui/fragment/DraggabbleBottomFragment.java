package technology.infobite.com.sportsforsports.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.adapter.CommentListAdapter;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.Comment;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.DailyNewsFeedMainModal;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.UserFeed;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseFragment;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;

public class DraggabbleBottomFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private UserFeed imageFeed;
    private TextView tvPostLikeCount, tvCommentCount, tvPostDescription;
    private RecyclerView recyclerViewCommentList;
    private CommentListAdapter commentListAdapter;

    private List<Comment> commentList = new ArrayList<>();
    private String strId = "", strFrom = "", strPostId = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_draggable_bottom, container, false);
        mContext = getActivity();
        retrofitApiClient = RetrofitService.getRetrofit();
        cd = new ConnectionDetector(mContext);
        init();
        return rootView;
    }

    private void init() {
        strId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        recyclerViewCommentList = rootView.findViewById(R.id.recyclerViewCommentList);
        recyclerViewCommentList.setHasFixedSize(true);
        recyclerViewCommentList.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewCommentList.setItemAnimator(new DefaultItemAnimator());

        tvPostLikeCount = rootView.findViewById(R.id.tvPostLikeCount);
        tvCommentCount = rootView.findViewById(R.id.tvCommentCount);
        tvPostDescription = rootView.findViewById(R.id.tvPostDescription);
        rootView.findViewById(R.id.post_comment_send).setOnClickListener(this);
        rootView.findViewById(R.id.llLikePost).setOnClickListener(this);
    }

    public void showImage(String strPostId, String strFrom) {
        this.strPostId = strPostId;
        this.strFrom = strFrom;
        postDetailApi();
    }

    private void postDetailApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.showPostTimeLine(new Dialog(mContext), retrofitApiClient.postDetail(strId, strPostId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    DailyNewsFeedMainModal dailyNewsFeedMainModal = (DailyNewsFeedMainModal) result.body();
                    if (dailyNewsFeedMainModal != null)
                        if (dailyNewsFeedMainModal.getFeed() != null)
                            imageFeed = dailyNewsFeedMainModal.getFeed().get(0);
                    setDataInModal();
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

    private void setDataInModal() {
        commentList.clear();
        commentList.addAll(imageFeed.getComment());

        if (imageFeed.getIsLike().equals("unlike")) {
            ((ImageView) rootView.findViewById(R.id.imgLike)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_icon));
        } else {
            ((ImageView) rootView.findViewById(R.id.imgLike)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }

        setData();
        setCommentList();
    }

    private void setData() {
        tvPostDescription.setText(imageFeed.getAthleteStatus());

        if (imageFeed.getLikes() == null || imageFeed.getLikes().isEmpty()) {
            tvPostLikeCount.setText("0 like");
        } else {
            tvPostLikeCount.setText(imageFeed.getLikes() + " like");
        }
        if (imageFeed.getComment() == null || imageFeed.getComment().isEmpty()) {
            tvCommentCount.setText("0 comment");
        } else {
            tvCommentCount.setText(imageFeed.getComment().size() + " comment");
        }
    }

    private void setCommentList() {
        commentListAdapter = new CommentListAdapter(commentList, mContext);
        recyclerViewCommentList.setAdapter(commentListAdapter);
        commentListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }
}
