package technology.infobite.com.sportsforsports.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.UserFeed;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.utils.BaseFragment;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;
import technology.infobite.com.sportsforsports.utils.exoplayer.VideoPlayerConfig;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class DraggabbleTopFragment extends BaseFragment {

    private View rootView;
    private CircleImageView imgUserProfile;
    private ImageView imgPostImage;
    private TextView tvUserName, tvPostTime;
    private ProgressBar progressBar;
    private UserFeed imageFeed;

    private SimpleExoPlayer player;
    private PlayerView videoSurfaceView;
    private RelativeLayout rlVideoView;
    private ProgressBar mProgressBar;

    private boolean isFirstTime = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_draggable_top, container, false);
        mContext = getActivity();
        retrofitApiClient = RetrofitService.getRetrofit();
        cd = new ConnectionDetector(mContext);
        init();
        return rootView;
    }

    private void init() {
        mProgressBar = rootView.findViewById(R.id.mProgressBar);
        rlVideoView = rootView.findViewById(R.id.rlVideoView);
        imgUserProfile = rootView.findViewById(R.id.imgUserProfile);
        imgPostImage = rootView.findViewById(R.id.imgPostImage);
        tvUserName = rootView.findViewById(R.id.tvUserName);
        tvPostTime = rootView.findViewById(R.id.tvPostTime);
        progressBar = rootView.findViewById(R.id.progressBar);

        initPlayer();
    }

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

    public void showImage(UserFeed imageFeed, String strType) {
        this.imageFeed = imageFeed;
        tvUserName.setText(imageFeed.getPostUserName());
        tvPostTime.setText(imageFeed.getEntryDate());

        rootView.findViewById(R.id.llTopFrag).setVisibility(GONE);
        Picasso.with(mContext)
                .load(Constant.PROFILE_IMAGE_BASE_URL + imageFeed.getPostUserImage())
                .placeholder(R.drawable.app_logo)
                .into(imgUserProfile);

        removePlayer();
        initPlayer();

        if (strType.equalsIgnoreCase("text")) {
            imgPostImage.setVisibility(View.GONE);
            rlVideoView.setVisibility(GONE);
            rootView.findViewById(R.id.tvHeadline).setVisibility(VISIBLE);
            ((TextView) rootView.findViewById(R.id.tvHeadline)).setText(imageFeed.getAthleteArticeHeadline());
        } else if (strType.equalsIgnoreCase("photo")) {
            imgPostImage.setVisibility(View.VISIBLE);
            rlVideoView.setVisibility(GONE);
            rootView.findViewById(R.id.tvHeadline).setVisibility(GONE);
            Picasso.with(mContext)
                    .load(Constant.IMAGE_BASE_URL + imageFeed.getAlhleteImages())
                    .placeholder(R.drawable.app_logo)
                    .into(imgPostImage);
        } else if (strType.equalsIgnoreCase("video")) {
            mProgressBar.setVisibility(VISIBLE);
            rlVideoView.setVisibility(VISIBLE);
            initVideoView(imageFeed.getAthleteVideo());
        }

        rootView.findViewById(R.id.llTopFrag).setVisibility(VISIBLE);
    }

    /*************************/
    /*Play video*/
    private void initVideoView(String strVideoUrl) {
        /****************************************/

        FrameLayout frameLayout = rootView.findViewById(R.id.video_layout);
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

        /*rootView.findViewById(R.id.imgClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.release();
                player = null;
                videoSurfaceView.setVisibility(INVISIBLE);
                videoSurfaceView.removeAllViews();
            }
        });*/
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

}
