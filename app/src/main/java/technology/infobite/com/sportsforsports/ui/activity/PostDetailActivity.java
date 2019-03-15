package technology.infobite.com.sportsforsports.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
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
import technology.infobite.com.sportsforsports.utils.exoplayer.VideoPlayerConfig;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class PostDetailActivity extends BaseActivity implements View.OnClickListener {

    private UserFeed newPostModel;

    private LinearLayout llPostComment;
    private RelativeLayout rlVideoView;
    private ImageView imgPostImage;
    private CircleImageView imgUserProfile;
    private TextView tvUserName, tvPostLikeCount, tvCommentCount, tvPostTime, tvPostDescription, tvHeadline;

    private RecyclerView recyclerViewCommentList;
    private CommentListAdapter commentListAdapter;
    private List<Comment> commentList = new ArrayList<>();

    private String strId = "";
    private String strFrom = "";
    private String postId = "";
    private SwipeRefreshLayout swipeRefreshLayout;

    private SimpleExoPlayer player;
    private PlayerView videoSurfaceView;
    private ProgressBar mProgressBar, imageProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        init();
    }

    private void init() {
        strId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        mProgressBar = findViewById(R.id.mProgressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        strFrom = getIntent().getStringExtra("get_from");
        postId = getIntent().getStringExtra("post_id");

        recyclerViewCommentList = findViewById(R.id.recyclerViewCommentList);
        recyclerViewCommentList.setHasFixedSize(true);
        recyclerViewCommentList.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewCommentList.setItemAnimator(new DefaultItemAnimator());

        llPostComment = findViewById(R.id.llPostComment);
        findViewById(R.id.llLikePost).setOnClickListener(this);
        findViewById(R.id.imgMoreMenu).setOnClickListener(this);
        rlVideoView = findViewById(R.id.rlVideoView);
        imgUserProfile = findViewById(R.id.imgUserProfile);
        tvUserName = findViewById(R.id.tvUserName);
        tvHeadline = findViewById(R.id.tvHeadline);
        imgPostImage = findViewById(R.id.imgPostImage);
        tvPostLikeCount = findViewById(R.id.tvPostLikeCount);
        tvCommentCount = findViewById(R.id.tvCommentCount);
        tvPostTime = findViewById(R.id.tvPostTime);
        tvPostDescription = findViewById(R.id.tvPostDescription);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postDetailApi();
            }
        });
        postDetailApi();
        initPlayer();
    }

    /*
     * Player initialise
     * */
    private void initPlayer() {
        videoSurfaceView = new PlayerView(mContext);
        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl(
                new DefaultAllocator(true, 16),
                VideoPlayerConfig.MIN_BUFFER_DURATION,
                VideoPlayerConfig.MAX_BUFFER_DURATION,
                VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER, -1, true);
        player = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
        videoSurfaceView.setUseController(false);
        videoSurfaceView.setPlayer(player);
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
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                    swipeRefreshLayout.setRefreshing(false);
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
                .placeholder(R.drawable.ic_profile)
                .into(imgUserProfile);

        if (!newPostModel.getAthleteArticeHeadline().isEmpty()) {
            tvHeadline.setVisibility(View.VISIBLE);
            imgPostImage.setVisibility(View.GONE);
            rlVideoView.setVisibility(View.GONE);
            imageProgress.setVisibility(View.GONE);
            tvHeadline.setText(newPostModel.getAthleteArticeHeadline());
        } else if (!newPostModel.getAlhleteImages().isEmpty()) {
            imgPostImage.setVisibility(View.VISIBLE);
            tvHeadline.setVisibility(View.GONE);
            rlVideoView.setVisibility(View.GONE);
            imageProgress.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(R.drawable.app_logo)
                    .load(Constant.IMAGE_BASE_URL + newPostModel.getAlhleteImages())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            imageProgress.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                            imageProgress.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imgPostImage);
        } else if (!newPostModel.getAthleteVideo().isEmpty()) {
            rlVideoView.setVisibility(View.VISIBLE);
            tvHeadline.setVisibility(View.GONE);
            imgPostImage.setVisibility(View.GONE);
            imageProgress.setVisibility(View.GONE);
            String strVideoUrl = newPostModel.getAthleteVideo();
            initVideoView(strVideoUrl);
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

    /*************************/
    /*Play video*/
    private void initVideoView(String strVideoUrl) {
        /****************************************/

        FrameLayout frameLayout = findViewById(R.id.video_layout);
        frameLayout.addView(videoSurfaceView);
        videoSurfaceView.requestFocus();
        videoSurfaceView.setPlayer(player);

        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent
                (mContext, "android_wave_list"), defaultBandwidthMeter);
        String uriString = Constant.VIDEO_BASE_URL + strVideoUrl;
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(uriString));
        player.prepare(videoSource);
        player.setPlayWhenReady(true);

        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {

                    case Player.STATE_BUFFERING:
                        videoSurfaceView.setAlpha(0.5f);
                        if (mProgressBar != null) {
                            mProgressBar.setVisibility(VISIBLE);
                        }
                        break;
                    case Player.STATE_ENDED:
                        player.seekTo(0);
                        break;
                    case Player.STATE_IDLE:
                        break;
                    case Player.STATE_READY:
                        if (mProgressBar != null) {
                            mProgressBar.setVisibility(GONE);
                        }
                        videoSurfaceView.setVisibility(VISIBLE);
                        videoSurfaceView.setAlpha(1);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
    }

    public void removePlayer() {
        player.release();
        player = null;
        videoSurfaceView.setVisibility(INVISIBLE);
        videoSurfaceView.removeAllViews();
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

    /*****************************************************************************************/
    /*
     *
     * */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (player != null) {
            removePlayer();
        } else {
            finish();
        }
    }
}
