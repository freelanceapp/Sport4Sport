package technology.infobite.com.sportsforsports.ui.activity;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.adapter.CommentListAdapter;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.Comment;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.DailyNewsFeedMainModal;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.Feed;
import technology.infobite.com.sportsforsports.modal.post_comment_modal.PostCommentResponseModal;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseActivity;

public class PostDetailActivity extends BaseActivity implements View.OnClickListener {

    private Feed newPostModel;

    private LinearLayout llPostComment, llLikePost;
    private RelativeLayout rlViewUserProfile;
    private ImageView imgPostImage;
    private CircleImageView imgUserProfile;
    private TextView tvUserName, tvPostLikeCount, tvCommentCount, tvPostTime, tvPostDescription, tvHeadline;
    private EditText posteditmessage;
    private Button postsend;
    private VideoView videoViewPost;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewCommentList;
    private CommentListAdapter commentListAdapter;
    private List<Comment> commentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        init();
    }

    private void init() {
        Gson gson = new Gson();
        String strPostDetail = AppPreference.getStringPreference(mContext, Constant.POST_DETAIL);
        newPostModel = gson.fromJson(strPostDetail, Feed.class);

        recyclerViewCommentList = findViewById(R.id.recyclerViewCommentList);
        recyclerViewCommentList.setHasFixedSize(true);
        recyclerViewCommentList.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewCommentList.setItemAnimator(new DefaultItemAnimator());

        progressBar = findViewById(R.id.progressBar);
        llPostComment = findViewById(R.id.llPostComment);
        llLikePost = findViewById(R.id.llLikePost);
        rlViewUserProfile = findViewById(R.id.rlViewUserProfile);
        imgUserProfile = findViewById(R.id.imgUserProfile);
        tvUserName = findViewById(R.id.tvUserName);
        tvHeadline = findViewById(R.id.tvHeadline);
        imgPostImage = findViewById(R.id.imgPostImage);
        videoViewPost = findViewById(R.id.videoViewPost);
        tvPostLikeCount = findViewById(R.id.tvPostLikeCount);
        tvCommentCount = findViewById(R.id.tvCommentCount);
        tvPostTime = findViewById(R.id.tvPostTime);
        tvPostDescription = findViewById(R.id.tvPostDescription);
        posteditmessage = findViewById(R.id.edit_post_comment);
        postsend = findViewById(R.id.post_comment_send);

        commentList.clear();
        commentList.addAll(newPostModel.getComment());

        findViewById(R.id.post_comment_send).setOnClickListener(this);
        llPostComment.setOnClickListener(this);
        setData();
        setCommentList();
    }

    private void setData() {
        tvUserName.setText(newPostModel.getPostUserName());
        tvPostDescription.setText(newPostModel.getAthleteStatus());

        Glide.with(mContext)
                .load(R.drawable.app_logo)
                .load(Constant.PROFILE_IMAGE_BASE_URL + newPostModel.getPostUserImage())
                .into(imgUserProfile);

        if (!newPostModel.getAthleteArticeHeadline().isEmpty()) {
            tvHeadline.setVisibility(View.VISIBLE);
            imgPostImage.setVisibility(View.GONE);
            videoViewPost.setVisibility(View.GONE);
            tvHeadline.setText(newPostModel.getAthleteArticeHeadline());
        } else if (!newPostModel.getAlhleteImages().isEmpty()) {
            imgPostImage.setVisibility(View.VISIBLE);
            tvHeadline.setVisibility(View.GONE);
            videoViewPost.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(R.drawable.app_logo)
                    .load(Constant.IMAGE_BASE_URL + newPostModel.getAlhleteImages())
                    .into(imgPostImage);
        } else if (!newPostModel.getAthleteVideo().isEmpty()) {
            videoViewPost.setVisibility(View.VISIBLE);
            tvHeadline.setVisibility(View.GONE);
            imgPostImage.setVisibility(View.GONE);
            String strVideoUrl = newPostModel.getAthleteVideo();
            Uri uri = Uri.parse(Constant.VIDEO_BASE_URL + strVideoUrl);
            videoViewPost.setVideoURI(uri);
            videoViewPost.start();
            progressBar.setVisibility(View.VISIBLE);
            videoViewPost.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

        if (newPostModel.getLikes() == null || newPostModel.getLikes().isEmpty()) {
            tvPostLikeCount.setText("0 like");
        } else {
            tvPostLikeCount.setText(newPostModel.getLikes() + " like");
        }
        if (newPostModel.getComment() == null || newPostModel.getComment().isEmpty()) {
            tvCommentCount.setText("0 comment");
        } else {
            tvCommentCount.setText(newPostModel.getComment().size() + " comment");
        }
        if (newPostModel.getEntryDate() == null || newPostModel.getEntryDate().isEmpty()) {
            tvPostTime.setText("");
        } else {
            tvPostTime.setText(newPostModel.getEntryDate());
        }
    }

    private void setCommentList() {
        commentListAdapter = new CommentListAdapter(commentList, this);
        recyclerViewCommentList.setAdapter(commentListAdapter);
        commentListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llPostComment:
                ((CardView) findViewById(R.id.cardViewComment)).setVisibility(View.VISIBLE);
                break;
            case R.id.post_comment_send:
                postCommentApi();
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.myLinearLayout);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
                break;
        }
    }

    private void postCommentApi() {
        String strUserId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        String strPostId = newPostModel.getFeedId();
        String strComments = ((EditText) findViewById(R.id.edit_post_comment)).getText().toString();

        if (!strComments.isEmpty()) {
            RetrofitService.postCommentResponse(retrofitApiClient.newPostComment(strPostId, strUserId, strComments), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    PostCommentResponseModal commentResponseModal = (PostCommentResponseModal) result.body();
                    commentList.clear();
                    AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, true);
                    timelineApi();
                    if (commentResponseModal == null)
                        return;
                    commentList.addAll(commentResponseModal.getComment());
                    commentListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onResponseFailed(String error) {
                    AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, false);
                    Alerts.show(mContext, error);
                }
            });
        } else {
            Alerts.show(mContext, "Enter some comments!!!");
        }
    }

    private void timelineApi() {
        String strId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        if (cd.isNetworkAvailable()) {
            RetrofitService.refreshTimeLine(retrofitApiClient.showPostTimeLine(strId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    DailyNewsFeedMainModal dailyNewsFeedMainModal = (DailyNewsFeedMainModal) result.body();
                    if (dailyNewsFeedMainModal.getError()) {
                        Alerts.show(mContext, "No data");
                    } else {
                        Gson gson = new GsonBuilder().setLenient().create();
                        String data = gson.toJson(dailyNewsFeedMainModal);
                        AppPreference.setStringPreference(mContext, Constant.TIMELINE_DATA, data);
                    }
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        }
    }

}
