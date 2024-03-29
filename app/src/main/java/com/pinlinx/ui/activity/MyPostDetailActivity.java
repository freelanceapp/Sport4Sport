package com.pinlinx.ui.activity;

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
import com.pinlinx.R;
import com.pinlinx.adapter.CommentListAdapter;
import com.pinlinx.constant.Constant;
import com.pinlinx.modal.daily_news_feed.Comment;
import com.pinlinx.modal.daily_news_feed.DailyNewsFeedMainModal;
import com.pinlinx.modal.daily_news_feed.UserFeed;
import com.pinlinx.modal.post_comment_modal.PostCommentResponseModal;
import com.pinlinx.retrofit_provider.RetrofitService;
import com.pinlinx.retrofit_provider.WebResponse;
import com.pinlinx.utils.Alerts;
import com.pinlinx.utils.AppPreference;
import com.pinlinx.utils.BaseActivity;
import com.pinlinx.utils.exoplayer.VideoPlayerConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MyPostDetailActivity extends BaseActivity implements View.OnClickListener {

    private UserFeed newPostModel;

    private LinearLayout llPostComment;
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
    private RelativeLayout rlVideoView;

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

        recyclerViewCommentList = findViewById(R.id.recyclerViewCommentList);
        recyclerViewCommentList.setHasFixedSize(true);
        recyclerViewCommentList.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewCommentList.setItemAnimator(new DefaultItemAnimator());

        mProgressBar = findViewById(R.id.mProgressBar);
        imageProgress = findViewById(R.id.imageProgress);
        llPostComment = findViewById(R.id.llPostComment);
        findViewById(R.id.llLikePost).setOnClickListener(this);
        findViewById(R.id.imgMoreMenu).setOnClickListener(this);
        imgUserProfile = findViewById(R.id.imgUserProfile);
        tvUserName = findViewById(R.id.tvUserName);
        tvHeadline = findViewById(R.id.tvHeadline);
        imgPostImage = findViewById(R.id.imgPostImage);
        rlVideoView = findViewById(R.id.rlVideoView);
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

        FrameLayout frameLayout = findViewById(R.id.video_layout);
        frameLayout.addView(videoSurfaceView);
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
            String strVideoUrl = newPostModel.getAthleteVideo();
            initVideoView(strVideoUrl);
        }

        if (newPostModel.getLikes() == null || newPostModel.getLikes().isEmpty()) {
            tvPostLikeCount.setText("0");
        } else {
            tvPostLikeCount.setText(newPostModel.getLikes() + "");
        }
        if (newPostModel.getComment() == null || newPostModel.getComment().isEmpty()) {
            tvCommentCount.setText("0");
        } else {
            tvCommentCount.setText(newPostModel.getComment().size() + "");
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
        commentListAdapter = new CommentListAdapter(commentList, this, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = Integer.parseInt(v.getTag().toString());
                /*if (strId.equals(commentList.get(pos).getUserId())) {
                    deleteCommentApi(commentList.get(pos).getCommentId());
                }*/
                deleteCommentDialog(commentList.get(pos).getCommentId());
                return false;
            }
        });
        recyclerViewCommentList.setAdapter(commentListAdapter);
        commentListAdapter.notifyDataSetChanged();
    }

    /*
     * Delete comment
     * */
    private void deleteCommentDialog(final String commentId) {
        final Dialog dialogCustomerInfo = new Dialog(mContext);
        dialogCustomerInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCustomerInfo.setContentView(R.layout.dialog_delete_comment);

        dialogCustomerInfo.setCanceledOnTouchOutside(true);
        dialogCustomerInfo.setCancelable(true);
        if (dialogCustomerInfo.getWindow() != null)
            dialogCustomerInfo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialogCustomerInfo.findViewById(R.id.tvOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCommentApi(commentId, dialogCustomerInfo);
            }
        });

        dialogCustomerInfo.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCustomerInfo.dismiss();
            }
        });

       /* Window window = dialogCustomerInfo.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);*/
        dialogCustomerInfo.show();
    }

    private void deleteCommentApi(String commentId, final Dialog dialogCustomerInfo) {
        String strPostId = newPostModel.getFeedId();

        if (!commentId.isEmpty()) {
            RetrofitService.postCommentResponse(retrofitApiClient.deletePostComment(strPostId, commentId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    PostCommentResponseModal commentResponseModal = (PostCommentResponseModal) result.body();
                    commentList.clear();
                    if (strFrom.equals("user")) {
                        AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, false);
                    } else {
                        AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, true);
                    }
                    timelineApi("comment");
                    if (commentResponseModal == null)
                        return;
                    commentList.addAll(commentResponseModal.getComment());
                    commentListAdapter.notifyDataSetChanged();
                    dialogCustomerInfo.dismiss();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llLikePost:
                likeApi(newPostModel, ((ImageView) findViewById(R.id.imgLike)), ((TextView) findViewById(R.id.tvPostLikeCount)));
                break;
            case R.id.llPostComment:
                findViewById(R.id.cardViewComment).setVisibility(View.VISIBLE);
                break;
            case R.id.post_comment_send:
                postCommentApi();
                LinearLayout mainLayout = findViewById(R.id.myLinearLayout);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
                break;
            case R.id.imgMoreMenu:
                openPopup();
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
                    timelineApi("comment");
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

    private void timelineApi(final String strType) {
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
                    if (strType.equalsIgnoreCase("delete")) {
                        finish();
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
                            textView.setText(jsonObject.getString("total_fan") + "");
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
    private void openPopup() {
        final Dialog dialogCustomerInfo = new Dialog(mContext);
        dialogCustomerInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCustomerInfo.setContentView(R.layout.dialog_delete);

        dialogCustomerInfo.setCanceledOnTouchOutside(true);
        dialogCustomerInfo.setCancelable(true);
        if (dialogCustomerInfo.getWindow() != null)
            dialogCustomerInfo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialogCustomerInfo.findViewById(R.id.tvOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePostApi(dialogCustomerInfo);
            }
        });

        dialogCustomerInfo.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCustomerInfo.dismiss();
            }
        });

       /* Window window = dialogCustomerInfo.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);*/
        dialogCustomerInfo.show();
    }

    private void deletePostApi(final Dialog dialogCustomerInfo) {
        removePlayer();
        RetrofitService.getContentData(new Dialog(mContext), retrofitApiClient.deletePost(postId), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                ResponseBody responseBody = (ResponseBody) result.body();
                try {
                    dialogCustomerInfo.dismiss();
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    Alerts.show(mContext, jsonObject.getString("message"));
                    timelineApi("delete");
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
