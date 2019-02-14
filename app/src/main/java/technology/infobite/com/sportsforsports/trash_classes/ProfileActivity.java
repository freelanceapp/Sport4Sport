package technology.infobite.com.sportsforsports.trash_classes;

public class ProfileActivity {
/*

    private UserDataModal userDataModal;
    private String strUserId = "", strMyId = "";

    private List<UserFeed> myPhotoList = new ArrayList<>();
    private List<UserFeed> myVideoList = new ArrayList<>();
    private List<UserFeed> myTextHeadlineList = new ArrayList<>();
    private List<UserFeed> originalTimeline = new ArrayList<>();

    private RecyclerView recyclerViewHeadlines, recyclerViewImage, recyclerViewVideos;
    private UserFeedAdapter headlineAdapter, photoAdapter, videoAdapter;

    private ImageView imgComment, imgCamera, imgVideoCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();

        initView();
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
        */
    /*Headline list data*//*

        headlineAdapter = new UserFeedAdapter(mContext, myTextHeadlineList, this, retrofitApiClient, "UserProfile");
        recyclerViewHeadlines.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewHeadlines.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHeadlines.setAdapter(headlineAdapter);

        */
    /*Image list data*//*

        photoAdapter = new UserFeedAdapter(mContext, myPhotoList, this, retrofitApiClient, "UserProfile");
        GridLayoutManager photoLayoutManager = new GridLayoutManager(mContext, 4);
        recyclerViewImage.setLayoutManager(photoLayoutManager);
        recyclerViewImage.setItemAnimator(new DefaultItemAnimator());
        recyclerViewImage.setAdapter(photoAdapter);

        */
    /*Video list data*//*

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
            case R.id.llFollow:
                followUser();
                break;
            case R.id.imgComment:
                imgComment.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
                imgCamera.setColorFilter(ContextCompat.getColor(mContext, R.color.transparent));
                imgVideoCamera.setColorFilter(ContextCompat.getColor(mContext, R.color.transparent));

                recyclerViewHeadlines.setVisibility(View.VISIBLE);
                recyclerViewVideos.setVisibility(View.GONE);
                recyclerViewImage.setVisibility(View.GONE);
                break;
            case R.id.imgCamera:
                imgComment.setColorFilter(ContextCompat.getColor(mContext, R.color.transparent));
                imgCamera.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
                imgVideoCamera.setColorFilter(ContextCompat.getColor(mContext, R.color.transparent));

                recyclerViewHeadlines.setVisibility(View.GONE);
                recyclerViewImage.setVisibility(View.VISIBLE);
                recyclerViewVideos.setVisibility(View.GONE);
                break;
            case R.id.imgVideoCamera:
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
*/

}
