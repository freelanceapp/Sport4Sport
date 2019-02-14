package technology.infobite.com.sportsforsports.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

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
import technology.infobite.com.sportsforsports.utils.exoplayer.VideoPlayerConfig;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
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
    private RecyclerView recyclerViewFeed;
    private int playPosition = -1;
    private String strId;
    private String strUserId = "";

    /***********************************************/
    private SimpleExoPlayer player;
    private PlayerView videoSurfaceView;
    private Dialog dialogCustomerInfo;

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
        //recyclerViewFeed.setVideoInfoList(feedList);
        mAdapter = new VideoRecyclerViewAdapter(mContext, feedList, this, retrofitApiClient);
        recyclerViewFeed.setLayoutManager(new LinearLayoutManager(mContext, VERTICAL, false));

        recyclerViewFeed.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFeed.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        /*if (firstTime) {
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
        }*/

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
            case R.id.video_layout:
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
            /*case R.id.video_layout:
                int videoPos = Integer.parseInt(v.getTag().toString());
                String strVideoUrl = feedList.get(videoPos).getAthleteVideo();
                videoPlayDialog(strVideoUrl);
                break;*/
        }
    }

    /************************************/
    /*
     * Video player dialog
     * */
    private void videoPlayDialog(String strVideoUrl) {
        dialogCustomerInfo = new Dialog(mContext);
        dialogCustomerInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCustomerInfo.setContentView(R.layout.dialog_video_play);

        dialogCustomerInfo.setCanceledOnTouchOutside(false);
        dialogCustomerInfo.setCancelable(false);
       /* if (dialogCustomerInfo.getWindow() != null)
            dialogCustomerInfo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);*/

        ProgressBar progressBar = dialogCustomerInfo.findViewById(R.id.progressBar);
        initVideoView(progressBar, strVideoUrl);

       /* Window window = dialogCustomerInfo.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);*/
        dialogCustomerInfo.show();
    }

    private void initVideoView(final ProgressBar mProgressBar, String strVideoUrl) {
        videoSurfaceView = new PlayerView(mContext);
        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
        mProgressBar.setVisibility(VISIBLE);
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
        /****************************************/

        FrameLayout frameLayout = dialogCustomerInfo.findViewById(R.id.video_layout);
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

        dialogCustomerInfo.findViewById(R.id.imgClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.release();
                player = null;
                videoSurfaceView.setVisibility(INVISIBLE);
                videoSurfaceView.removeAllViews();
                dialogCustomerInfo.dismiss();
            }
        });

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
