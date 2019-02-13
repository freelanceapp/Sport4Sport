package technology.infobite.com.sportsforsports.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Response;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.adapter.CommentListAdapter;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.Comment;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.DailyNewsFeedMainModal;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.UserFeed;
import technology.infobite.com.sportsforsports.modal.post_comment_modal.PostCommentResponseModal;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseActivity;

public class MyPostDetailActivity extends BaseActivity implements View.OnClickListener {

    private UserFeed newPostModel;

    private LinearLayout llPostComment;
    private ImageView imgPostImage;
    private CircleImageView imgUserProfile;
    private TextView tvUserName, tvPostLikeCount, tvCommentCount, tvPostTime, tvPostDescription, tvHeadline;

    private VideoView videoViewPost;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewCommentList;
    private CommentListAdapter commentListAdapter;
    private List<Comment> commentList = new ArrayList<>();

    private String strId = "";
    private String strFrom = "";
    private String postId = "";
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        init();
    }

    private void init() {
        strId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        strFrom = getIntent().getStringExtra("get_from");
        postId = getIntent().getStringExtra("post_id");

        /*Gson gson = new Gson();
        String strPostDetail = AppPreference.getStringPreference(mContext, Constant.POST_DETAIL);
        newPostModel = gson.fromJson(strPostDetail, UserFeed.class);*/

        recyclerViewCommentList = findViewById(R.id.recyclerViewCommentList);
        recyclerViewCommentList.setHasFixedSize(true);
        recyclerViewCommentList.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewCommentList.setItemAnimator(new DefaultItemAnimator());

        progressBar = findViewById(R.id.progressBar);
        llPostComment = findViewById(R.id.llPostComment);
        findViewById(R.id.llLikePost).setOnClickListener(this);
        findViewById(R.id.imgMoreMenu).setOnClickListener(this);
        imgUserProfile = findViewById(R.id.imgUserProfile);
        tvUserName = findViewById(R.id.tvUserName);
        tvHeadline = findViewById(R.id.tvHeadline);
        imgPostImage = findViewById(R.id.imgPostImage);
        videoViewPost = findViewById(R.id.videoViewPost);
        tvPostLikeCount = findViewById(R.id.tvPostLikeCount);
        tvCommentCount = findViewById(R.id.tvCommentCount);
        tvPostTime = findViewById(R.id.tvPostTime);
        tvPostDescription = findViewById(R.id.tvPostDescription);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postDetailApi();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        postDetailApi();
    }

    /* Post detail api */
    private void postDetailApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.showPostTimeLine(new Dialog(mContext), retrofitApiClient.postDetail(strId, postId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    DailyNewsFeedMainModal dailyNewsFeedMainModal = (DailyNewsFeedMainModal) result.body();
                    if (dailyNewsFeedMainModal != null)
                        if (dailyNewsFeedMainModal.getFeed() != null)
                            newPostModel = dailyNewsFeedMainModal.getFeed().get(0);
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
        commentList.addAll(newPostModel.getComment());

        findViewById(R.id.post_comment_send).setOnClickListener(this);
        llPostComment.setOnClickListener(this);

        if (newPostModel.getIsLike().equals("unlike")) {
            ((ImageView) findViewById(R.id.imgLike)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_icon));
        } else {
            ((ImageView) findViewById(R.id.imgLike)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }

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
            case R.id.llLikePost:
                likeApi(newPostModel, ((ImageView) findViewById(R.id.imgLike)), ((TextView) findViewById(R.id.tvPostLikeCount)));
                break;
            case R.id.llPostComment:
                ((CardView) findViewById(R.id.cardViewComment)).setVisibility(View.VISIBLE);
                break;
            case R.id.post_comment_send:
                postCommentApi();
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.myLinearLayout);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
                break;
            case R.id.imgMoreMenu:
                checkFollowApi(newPostModel.getPostUserId(), newPostModel.getPostUserName());
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
                    if (strFrom.equals("user")) {
                        AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, false);
                    }/* else if (strFrom.equals("notification")) {
                        AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, true);
                    }*/ else {
                        AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, true);
                    }
                    timelineApi();
                    if (commentResponseModal == null)
                        return;
                    commentList.addAll(commentResponseModal.getComment());
                    commentListAdapter.notifyDataSetChanged();
                    ((EditText) findViewById(R.id.edit_post_comment)).setText("");
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

    /***********************************************************************/
    /*
     * Like/Unlike function
     * */
    private void likeApi(final UserFeed feed, final ImageView imgLike, final TextView textView) {

        if (cd.isNetworkAvailable()) {
            RetrofitService.getLikeResponse(retrofitApiClient.postLike(feed.getFeedId(), strId, "1"), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        if (!jsonObject.getBoolean("error")) {
                            if (feed.getIsLike().equals("unlike")) {
                                imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_fill));
                            } else {
                                imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_icon));
                            }
                            postDetailApi();
                            textView.setText(jsonObject.getString("total_fan") + " like");
                        } else {
                            Alerts.show(mContext, jsonObject.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
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

    /*************************************************************************/
    /*
     * Follow unfollow function
     * */
    private void checkFollowApi(final String strUserId, final String strPostUsername) {
        String strMyId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        RetrofitService.getLikeResponse(retrofitApiClient.checkFollow(strUserId, strMyId), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                ResponseBody responseBody = (ResponseBody) result.body();
                try {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    if (!jsonObject.getBoolean("error")) {
                        String strStatus = jsonObject.getString("status");
                        openPopup(strPostUsername, strStatus, strUserId);
                    } else {
                        Alerts.show(mContext, jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponseFailed(String error) {
                Alerts.show(mContext, error);
            }
        });
    }

    private void openPopup(String strName, String strStatus, final String strFanId) {
        final Dialog dialogCustomerInfo = new Dialog(mContext);
        dialogCustomerInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCustomerInfo.setContentView(R.layout.popup_follow);

        dialogCustomerInfo.setCanceledOnTouchOutside(true);
        dialogCustomerInfo.setCancelable(true);
        if (dialogCustomerInfo.getWindow() != null)
            dialogCustomerInfo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ((TextView) dialogCustomerInfo.findViewById(R.id.tvUsername)).setText(strName);
        ((TextView) dialogCustomerInfo.findViewById(R.id.tvFollow)).setText(strStatus);
        ((TextView) dialogCustomerInfo.findViewById(R.id.tvFollow)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followUser(strFanId, dialogCustomerInfo);
            }
        });

       /* Window window = dialogCustomerInfo.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);*/
        dialogCustomerInfo.show();
    }

    private void followUser(String strUserId, final Dialog dialogCustomerInfo) {
        String strMyId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        RetrofitService.getLikeResponse(retrofitApiClient.followUser(strUserId, strMyId, "1"), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                ResponseBody responseBody = (ResponseBody) result.body();
                try {
                    dialogCustomerInfo.dismiss();
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    timelineApi();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponseFailed(String error) {
                Alerts.show(mContext, error);
            }
        });
    }
}
