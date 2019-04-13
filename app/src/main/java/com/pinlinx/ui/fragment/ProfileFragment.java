package com.pinlinx.ui.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pinlinx.R;
import com.pinlinx.adapter.UserFeedAdapter;
import com.pinlinx.constant.Constant;
import com.pinlinx.modal.daily_news_feed.UserFeed;
import com.pinlinx.modal.user_data.UserDataModal;
import com.pinlinx.retrofit_provider.RetrofitService;
import com.pinlinx.retrofit_provider.WebResponse;
import com.pinlinx.swipe_classes.SwipeLayout;
import com.pinlinx.ui.activity.LeagueFollowingActivity;
import com.pinlinx.ui.activity.MyFollowersActivity;
import com.pinlinx.ui.activity.MyPostDetailActivity;
import com.pinlinx.ui.activity.UpdateProfileActivity;
import com.pinlinx.utils.Alerts;
import com.pinlinx.utils.AppPreference;
import com.pinlinx.utils.BaseFragment;
import com.pinlinx.utils.ConnectionDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private UserDataModal userDataModal;
    private static final int PICK_FROM_GALLERY = 1;
    private String imagePath = "";
    private File imageFile = null;

    private List<UserFeed> myPhotoList = new ArrayList<>();
    private List<UserFeed> myVideoList = new ArrayList<>();
    private List<UserFeed> myTextHeadlineList = new ArrayList<>();
    private List<UserFeed> originalTimeline = new ArrayList<>();

    private RecyclerView recyclerViewHeadlines, recyclerViewImage, recyclerViewVideos;
    private UserFeedAdapter headlineAdapter, photoAdapter, videoAdapter;

    private ImageView imgComment, imgCamera, imgVideoCamera;
    private SwipeLayout sample1;
    private String strListType = "text";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        activity = getActivity();
        mContext = getActivity();
        retrofitApiClient = RetrofitService.getRetrofit();
        cd = new ConnectionDetector(mContext);
        initView();
        return rootView;
    }

    private void initView() {
        Glide.with(mContext)
                .load(Constant.DEFAULT_IMAGE_URL)
                .apply(new RequestOptions().optionalCenterCrop())
                .into(((CircleImageView) rootView.findViewById(R.id.ic_profile_person)));

        recyclerViewHeadlines = rootView.findViewById(R.id.recyclerViewHeadlines);
        recyclerViewImage = rootView.findViewById(R.id.recyclerViewImage);
        recyclerViewVideos = rootView.findViewById(R.id.recyclerViewVideos);
        rootView.findViewById(R.id.imgEditProfile).setOnClickListener(this);
        rootView.findViewById(R.id.rlUpdateProfile).setOnClickListener(this);
        rootView.findViewById(R.id.llFollowLeague).setOnClickListener(this);
        rootView.findViewById(R.id.llFollowers).setOnClickListener(this);

        imgComment = rootView.findViewById(R.id.imgComment);
        imgCamera = rootView.findViewById(R.id.imgCamera);
        imgVideoCamera = rootView.findViewById(R.id.imgVideoCamera);

        imgComment.setOnClickListener(this);
        imgCamera.setOnClickListener(this);
        imgVideoCamera.setOnClickListener(this);

        setMyPhotoVideoData();
        initSwipeLayout();
    }

    private void initSwipeLayout() {
        sample1 = (SwipeLayout) rootView.findViewById(R.id.sample1);
        sample1.setShowMode(SwipeLayout.ShowMode.PullOut);
        sample1.addDrag(SwipeLayout.DragEdge.Left, sample1.findViewById(R.id.bottom_wrapper));
    }

    private void setMyPhotoVideoData() {
        /*Headline list data*/
        headlineAdapter = new UserFeedAdapter(mContext, myTextHeadlineList, this, retrofitApiClient, "MyProfile");
        recyclerViewHeadlines.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewHeadlines.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHeadlines.setAdapter(headlineAdapter);

        /*Image list data*/
        photoAdapter = new UserFeedAdapter(mContext, myPhotoList, this, retrofitApiClient, "MyProfile");
        GridLayoutManager photoLayoutManager = new GridLayoutManager(mContext, 4);
        recyclerViewImage.setLayoutManager(photoLayoutManager);
        recyclerViewImage.setItemAnimator(new DefaultItemAnimator());
        recyclerViewImage.setAdapter(photoAdapter);

        /*Video list data*/
        videoAdapter = new UserFeedAdapter(mContext, myVideoList, this, retrofitApiClient, "MyProfile");
        GridLayoutManager videoLayoutManager = new GridLayoutManager(mContext, 4);
        recyclerViewVideos.setLayoutManager(videoLayoutManager);
        recyclerViewVideos.setItemAnimator(new DefaultItemAnimator());
        recyclerViewVideos.setAdapter(videoAdapter);


        init();
    }

    private void init() {
        String strUserId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
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
                            AppPreference.setStringPreference(mContext, Constant.USER_ID, userDataModal.getUser().getUserId());
                            AppPreference.setStringPreference(mContext, Constant.USER_NAME, userDataModal.getUser().getUserName());
                            AppPreference.setStringPreference(mContext, Constant.USER_IMAGE, userDataModal.getUser().getAvtarImg());
                            rootView.findViewById(R.id.llProfile).setVisibility(View.VISIBLE);

                            if (userDataModal.getFeed() != null) {
                                if (userDataModal.getFeed().size() > 0) {
                                    //((TextView) findViewById(R.id.tvEmpty)).setVisibility(View.GONE);
                                    originalTimeline.addAll(userDataModal.getFeed());
                                } else {
                                    //((TextView) findViewById(R.id.tvEmpty)).setVisibility(View.VISIBLE);
                                }
                            } else {
                                //((TextView) findViewById(R.id.tvEmpty)).setVisibility(View.VISIBLE);
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
                            rootView.findViewById(R.id.llProfile).setVisibility(View.GONE);
                            Alerts.show(mContext, userDataModal.getMessage());
                        }
                    headlineAdapter.notifyDataSetChanged();
                    photoAdapter.notifyDataSetChanged();
                    videoAdapter.notifyDataSetChanged();
                }

                @Override
                public void onResponseFailed(String error) {
                    rootView.findViewById(R.id.llProfile).setVisibility(View.GONE);
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

    private void setViewData() {
        ((TextView) rootView.findViewById(R.id.txtMyName)).setText(userDataModal.getUser().getUserName());
        ((TextView) rootView.findViewById(R.id.tvMainSport)).setText(userDataModal.getUser().getMainSport());
        ((TextView) rootView.findViewById(R.id.txtBio)).setText(userDataModal.getUser().getBio());
        ((TextView) rootView.findViewById(R.id.txtFansCount)).setText(userDataModal.getFan().get(0).getFan());
        ((TextView) rootView.findViewById(R.id.txtDob)).setText(userDataModal.getUser().getDob());
        ((TextView) rootView.findViewById(R.id.txtCoachName)).setText(userDataModal.getUser().getCoach());
        ((TextView) rootView.findViewById(R.id.txtSportClub)).setText(userDataModal.getUser().getClub());

        String strAvtar = userDataModal.getUser().getAvtarImg();
        if (strAvtar != null) {
            Glide.with(mContext)
                    .load(Constant.PROFILE_IMAGE_BASE_URL + strAvtar)
                    .apply(new RequestOptions().optionalCenterCrop())
                    .placeholder(R.drawable.ic_profile)
                    .into(((CircleImageView) rootView.findViewById(R.id.ic_profile_person)));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgEditProfile:
                Intent intent = new Intent(mContext, UpdateProfileActivity.class);
                intent.putExtra("userDataModal", userDataModal);
                startActivity(intent);
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
            case R.id.rlUpdateProfile:
                try {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.llHeadline:
            case R.id.rlImageVideo:
                if (strListType.equalsIgnoreCase("text")) {
                    sendPostData(v, myTextHeadlineList);
                } else if (strListType.equalsIgnoreCase("photo")) {
                    sendPostData(v, myPhotoList);
                } else if (strListType.equalsIgnoreCase("video")) {
                    sendPostData(v, myVideoList);
                }
                break;
            case R.id.llFollowLeague:
                startActivity(new Intent(mContext, LeagueFollowingActivity.class));
                break;
            case R.id.llFollowers:
                startActivity(new Intent(mContext, MyFollowersActivity.class));
                break;
        }
    }

    private void sendPostData(View view, List<UserFeed> userFeedList) {
        int pos = Integer.parseInt(view.getTag().toString());
        UserFeed imageFeed = userFeedList.get(pos);

        Intent intent = new Intent(mContext, MyPostDetailActivity.class);
        intent.putExtra("get_from", "user");
        intent.putExtra("post_id", imageFeed.getFeedId());
        mContext.startActivity(intent);
    }

    /***********************************************************************/
    /*
     * Update profile api
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && null != data) {
            final Uri uriImage = data.getData();
            final InputStream inputStream;
            try {
                inputStream = mContext.getContentResolver().openInputStream(uriImage);
                final Bitmap imageMap = BitmapFactory.decodeStream(inputStream);
                ((CircleImageView) rootView.findViewById(R.id.ic_profile_person)).setImageBitmap(imageMap);

                imagePath = getPath(uriImage);
                imageFile = new File(imagePath);
                updateProfilePhotoApi();
            } catch (FileNotFoundException e) {
                Toast.makeText(mContext, "Image not found", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String strPath = cursor.getString(column_index);
        cursor.close();
        return strPath;
    }

    private void updateProfilePhotoApi() {
        RequestBody _id = RequestBody.create(MediaType.parse("text/plain"), userDataModal.getUser().getUserId());

        RequestBody imageBodyFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("avtar_img", imageFile.getName(), imageBodyFile);

        RetrofitService.getContentData(new Dialog(mContext), retrofitApiClient.updateProfileImage(_id, fileToUpload), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                ResponseBody responseBody = (ResponseBody) result.body();
                if (responseBody != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        Log.e("Profile_data:- ", jsonObject + "");
                        Alerts.show(mContext, "Profile pic updated");
                        //init();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Alerts.show(mContext, "Error in submit");
                }
            }

            @Override
            public void onResponseFailed(String error) {
                Alerts.show(mContext, error);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    Alerts.show(mContext, "Please give permission");
                }
                break;
        }
    }

    /*************************************************************************/
    /*
     *
     * */
}
