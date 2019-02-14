package technology.infobite.com.sportsforsports.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Response;
import technology.infobite.com.draggableview.DraggablePanel;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.adapter.UserFeedAdapter;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.UserFeed;
import technology.infobite.com.sportsforsports.modal.user_data.UserDataModal;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.ui.fragment.DraggabbleBottomFragment;
import technology.infobite.com.sportsforsports.ui.fragment.DraggabbleTopFragment;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseActivity;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;

public class DragablePanelUserProfileActivity extends BaseActivity implements View.OnClickListener {

    private DraggablePanel draggablePanel;
    private DraggabbleTopFragment draggabbleTopFragment;
    private DraggabbleBottomFragment draggabbleBottomFragment;
    private boolean isPanelOpen = false;
    /************************************************************/

    private UserDataModal userDataModal;
    private String strUserId = "", strMyId = "";

    private List<UserFeed> myPhotoList = new ArrayList<>();
    private List<UserFeed> myVideoList = new ArrayList<>();
    private List<UserFeed> myTextHeadlineList = new ArrayList<>();
    private List<UserFeed> originalTimeline = new ArrayList<>();

    private RecyclerView recyclerViewHeadlines, recyclerViewImage, recyclerViewVideos;
    private UserFeedAdapter headlineAdapter, photoAdapter, videoAdapter;

    private ImageView imgComment, imgCamera, imgVideoCamera;

    private String strListType = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragable_panel_user_profile);

        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();

        initDragable();
        initView();
    }

    private void initDragable() {
        draggabbleTopFragment = new DraggabbleTopFragment();
        draggabbleBottomFragment = new DraggabbleBottomFragment();

        draggablePanel = (DraggablePanel) findViewById(R.id.draggable_panel);
        draggablePanel.setVisibility(View.GONE);
        draggablePanel.setFragmentManager(getSupportFragmentManager());
        draggablePanel.setTopFragment(draggabbleTopFragment);
        draggablePanel.setBottomFragment(draggabbleBottomFragment);

        TypedValue typedValue = new TypedValue();
        getResources().getValue(R.dimen.x_scale_factor, typedValue, true);
        float xScaleFactor = typedValue.getFloat();
        typedValue = new TypedValue();
        getResources().getValue(R.dimen.y_scale_factor, typedValue, true);
        float yScaleFactor = typedValue.getFloat();
        draggablePanel.setXScaleFactor(xScaleFactor);
        draggablePanel.setYScaleFactor(yScaleFactor);
        draggablePanel.setTopViewHeight(550);
        draggablePanel.setTopFragmentMarginRight(getResources().getDimensionPixelSize(R.dimen.top_fragment_margin));
        draggablePanel.setTopFragmentMarginBottom(getResources().getDimensionPixelSize(R.dimen.top_fragment_margin));
        draggablePanel.initializeView();
        draggablePanel.setVisibility(View.GONE);
    }

    private void initView() {
        strMyId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        strUserId = getIntent().getStringExtra("fan_id");

        recyclerViewHeadlines = findViewById(R.id.recyclerViewHeadlines);
        recyclerViewImage = findViewById(R.id.recyclerViewImage);
        recyclerViewVideos = findViewById(R.id.recyclerViewVideos);

        imgComment = findViewById(R.id.imgComment);
        imgCamera = findViewById(R.id.imgCamera);
        imgVideoCamera = findViewById(R.id.imgVideoCamera);

        findViewById(R.id.imgBack).setOnClickListener(this);
        findViewById(R.id.llFollow).setOnClickListener(this);
        imgComment.setOnClickListener(this);
        imgCamera.setOnClickListener(this);
        imgVideoCamera.setOnClickListener(this);

        setMyPhotoVideoData();
        checkFollowApi();
    }

    private void setMyPhotoVideoData() {
        /*Headline list data*/
        headlineAdapter = new UserFeedAdapter(mContext, myTextHeadlineList, this, retrofitApiClient, "UserProfile");
        recyclerViewHeadlines.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewHeadlines.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHeadlines.setAdapter(headlineAdapter);

        /*Image list data*/
        photoAdapter = new UserFeedAdapter(mContext, myPhotoList, this, retrofitApiClient, "UserProfile");
        GridLayoutManager photoLayoutManager = new GridLayoutManager(mContext, 4);
        recyclerViewImage.setLayoutManager(photoLayoutManager);
        recyclerViewImage.setItemAnimator(new DefaultItemAnimator());
        recyclerViewImage.setAdapter(photoAdapter);

        /*Video list data*/
        videoAdapter = new UserFeedAdapter(mContext, myVideoList, this, retrofitApiClient, "UserProfile");
        GridLayoutManager videoLayoutManager = new GridLayoutManager(mContext, 4);
        recyclerViewVideos.setLayoutManager(videoLayoutManager);
        recyclerViewVideos.setItemAnimator(new DefaultItemAnimator());
        recyclerViewVideos.setAdapter(videoAdapter);


        init();
    }

    private void init() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getLoginData(new Dialog(mContext), retrofitApiClient.userProfile(strUserId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    userDataModal = (UserDataModal) result.body();
                    originalTimeline.clear();
                    myTextHeadlineList.clear();
                    myPhotoList.clear();
                    myVideoList.clear();
                    if (userDataModal != null)
                        if (!userDataModal.getError()) {
                            findViewById(R.id.llProfile).setVisibility(View.VISIBLE);
                            if (userDataModal.getFeed() != null) {
                                if (userDataModal.getFeed().size() > 0) {
                                    ((TextView) findViewById(R.id.tvEmpty)).setVisibility(View.GONE);
                                    originalTimeline.addAll(userDataModal.getFeed());
                                } else {
                                    ((TextView) findViewById(R.id.tvEmpty)).setVisibility(View.VISIBLE);
                                }
                            } else {
                                ((TextView) findViewById(R.id.tvEmpty)).setVisibility(View.VISIBLE);
                            }

                            if (originalTimeline.size() > 0) {
                                for (int i = 0; i < originalTimeline.size(); i++) {
                                    if (!originalTimeline.get(i).getAthleteArticeHeadline().isEmpty()) {
                                        myTextHeadlineList.add(userDataModal.getFeed().get(i));
                                    } else if (!originalTimeline.get(i).getAlhleteImages().isEmpty()) {
                                        myPhotoList.add(userDataModal.getFeed().get(i));
                                    } else if (!originalTimeline.get(i).getAthleteVideo().isEmpty()) {
                                        myVideoList.add(userDataModal.getFeed().get(i));
                                    }
                                }
                            }
                            setViewData();
                        } else {
                            findViewById(R.id.llProfile).setVisibility(View.GONE);
                            Alerts.show(mContext, userDataModal.getMessage());
                        }
                    headlineAdapter.notifyDataSetChanged();
                    photoAdapter.notifyDataSetChanged();
                    videoAdapter.notifyDataSetChanged();
                }

                @Override
                public void onResponseFailed(String error) {
                    findViewById(R.id.llProfile).setVisibility(View.GONE);
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

    private void setViewData() {
        ((TextView) findViewById(R.id.txtMyName)).setText(userDataModal.getUser().getUserName());
        ((TextView) findViewById(R.id.txtBio)).setText(userDataModal.getUser().getBio());
        ((TextView) findViewById(R.id.txtFansCount)).setText(userDataModal.getFan().get(0).getFan());
        ((TextView) findViewById(R.id.txtDob)).setText(userDataModal.getUser().getDob());
        ((TextView) findViewById(R.id.txtCoachName)).setText(userDataModal.getUser().getCoach());
        ((TextView) findViewById(R.id.txtSportClub)).setText(userDataModal.getUser().getClub());

        Glide.with(mContext)
                .load(Constant.PROFILE_IMAGE_BASE_URL + userDataModal.getUser().getAvtarImg())
                .apply(new RequestOptions().optionalCenterCrop())
                .into(((CircleImageView) findViewById(R.id.ic_profile_person)));

        if (myTextHeadlineList.size() == 0) {
            ((TextView) findViewById(R.id.tvEmpty)).setVisibility(View.VISIBLE);
        } else if (myPhotoList.size() == 0) {
            ((TextView) findViewById(R.id.tvEmpty)).setVisibility(View.VISIBLE);
        } else if (myVideoList.size() == 0) {
            ((TextView) findViewById(R.id.tvEmpty)).setVisibility(View.VISIBLE);
        } else {
            ((TextView) findViewById(R.id.tvEmpty)).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llHeadline:
                sendPostData(v, myTextHeadlineList);
                break;
            case R.id.rlImageVideo:
                if (strListType.equalsIgnoreCase("photo")) {
                    openPanelWithData(v, myPhotoList);
                } else if (strListType.equalsIgnoreCase("video")) {
                    openPanelWithData(v, myVideoList);
                }
                break;
            case R.id.llFollow:
                followUser();
                break;
            case R.id.imgComment:
                strListType = "text";
                imgComment.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
                imgCamera.setColorFilter(ContextCompat.getColor(mContext, R.color.transparent));
                imgVideoCamera.setColorFilter(ContextCompat.getColor(mContext, R.color.transparent));

                recyclerViewHeadlines.setVisibility(View.VISIBLE);
                recyclerViewVideos.setVisibility(View.GONE);
                recyclerViewImage.setVisibility(View.GONE);
                break;
            case R.id.imgCamera:
                strListType = "photo";
                imgComment.setColorFilter(ContextCompat.getColor(mContext, R.color.transparent));
                imgCamera.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
                imgVideoCamera.setColorFilter(ContextCompat.getColor(mContext, R.color.transparent));

                recyclerViewHeadlines.setVisibility(View.GONE);
                recyclerViewImage.setVisibility(View.VISIBLE);
                recyclerViewVideos.setVisibility(View.GONE);
                break;
            case R.id.imgVideoCamera:
                strListType = "video";
                imgComment.setColorFilter(ContextCompat.getColor(mContext, R.color.transparent));
                imgCamera.setColorFilter(ContextCompat.getColor(mContext, R.color.transparent));
                imgVideoCamera.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));

                recyclerViewVideos.setVisibility(View.VISIBLE);
                recyclerViewHeadlines.setVisibility(View.GONE);
                recyclerViewImage.setVisibility(View.GONE);
                break;
            case R.id.imgBack:
                finish();
                break;
        }
    }

    private void followUser() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getLikeResponse(retrofitApiClient.followUser(strUserId, strMyId, "1"), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        if (!jsonObject.getBoolean("error")) {
                            ((TextView) findViewById(R.id.txtFansCount)).setText(jsonObject.getString("total_fan"));
                            checkFollowApi();
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

    private void checkFollowApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getLikeResponse(retrofitApiClient.checkFollow(strUserId, strMyId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        if (!jsonObject.getBoolean("error")) {
                            String strStatus = jsonObject.getString("status");
                            if (strStatus.equalsIgnoreCase("Follow")) {
                                ((TextView) findViewById(R.id.tvFollow)).setText("Unfollow");
                                ((ImageView) findViewById(R.id.imgFollow)).setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_fill));
                            } else {
                                ((TextView) findViewById(R.id.tvFollow)).setText("Follow");
                                ((ImageView) findViewById(R.id.imgFollow)).setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_icon));
                            }
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

    /*
     * Set data in Dragabele panel
     * */
    private void sendPostData(View view, List<UserFeed> dataList) {
        int pos = Integer.parseInt(view.getTag().toString());
        UserFeed imageFeed = dataList.get(pos);

        Intent intent = new Intent(mContext, PostDetailActivity.class);
        intent.putExtra("get_from", "user");
        intent.putExtra("post_id", imageFeed.getFeedId());
        mContext.startActivity(intent);
    }

    private void openPanelWithData(View view, List<UserFeed> dataList) {
        int pos = Integer.parseInt(view.getTag().toString());
        UserFeed imageFeed = dataList.get(pos);

        draggablePanel.setVisibility(View.VISIBLE);
        draggablePanel.maximize();
        draggabbleTopFragment.showImage(imageFeed);
        draggabbleBottomFragment.showImage(imageFeed.getFeedId(), "timeline");
    }

    @Override
    public void onBackPressed() {
       /* if (draggablePanel.isMaximized()) {
            draggablePanel.minimize();
        } else {
            finish();
        }*/
        finish();
    }
}

 /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgA:
            case R.id.imgB:
            case R.id.imgC:
            case R.id.imgD:
                *//*draggablePanel.setFragmentManager(getSupportFragmentManager());
                draggablePanel.setTopFragment(new DraggabbleTopFragment());
                draggablePanel.setBottomFragment(new DraggabbleBottomFragment());
                draggablePanel.setTopViewHeight(300);
                draggablePanel.initializeView();*//*
 *//*draggablePanel.setVisibility(View.VISIBLE);
                draggablePanel.maximize();*//*
                draggableView.setVisibility(View.VISIBLE);
                draggableView.maximize();
                break;
        }
    }*/