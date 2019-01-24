package technology.infobite.com.sportsforsports.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import technology.infobite.com.sportsforsports.GalleryModel;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.adapter.MyPhotoVideoAdapter;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.user_data.UserDataModal;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.ui.activity.UpdateProfileActivity;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseFragment;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private UserDataModal userDataModal;

    private List<GalleryModel> myPhotoList = new ArrayList<>();
    private List<GalleryModel> myVideoList = new ArrayList<>();
    private RecyclerView recyclerViewMyPhotos, recyclerViewMyVideos;

    private ViewFlipper viewFlipper;
    private ImageView imgComment, imgCamera, imgVideoCamera;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = getActivity();
        retrofitApiClient = RetrofitService.getRetrofit();
        cd = new ConnectionDetector(mContext);
        init();
        initView();
        return rootView;
    }

    private void initView() {
        ((ImageView) rootView.findViewById(R.id.imgEditProfile)).setOnClickListener(this);

        imgComment = ((ImageView) rootView.findViewById(R.id.imgComment));
        imgCamera = ((ImageView) rootView.findViewById(R.id.imgCamera));
        imgVideoCamera = ((ImageView) rootView.findViewById(R.id.imgVideoCamera));

        imgComment.setOnClickListener(this);
        imgCamera.setOnClickListener(this);
        imgVideoCamera.setOnClickListener(this);
        viewFlipper = rootView.findViewById(R.id.viewFlipper);
    }

    private void init() {
        String strUserId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        if (cd.isNetworkAvailable()) {
            RetrofitService.getLoginData(new Dialog(mContext), retrofitApiClient.userProfile(strUserId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    userDataModal = (UserDataModal) result.body();
                    if (userDataModal != null)
                        if (!userDataModal.getError()) {
                            ((LinearLayout)rootView.findViewById(R.id.llProfile)).setVisibility(View.VISIBLE);
                            //Alerts.show(mContext, userDataModal.getMessage());
                            setViewData();
                        } else {
                            ((LinearLayout)rootView.findViewById(R.id.llProfile)).setVisibility(View.GONE);
                            Alerts.show(mContext, userDataModal.getMessage());
                        }
                }

                @Override
                public void onResponseFailed(String error) {
                    ((LinearLayout)rootView.findViewById(R.id.llProfile)).setVisibility(View.GONE);
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }

        setMyPhotoVideoData();
    }

    private void setViewData() {
        ((TextView) rootView.findViewById(R.id.txtMyName)).setText(userDataModal.getUser().getUserName());
        ((TextView) rootView.findViewById(R.id.txtBio)).setText(userDataModal.getUser().getBio());
        //((TextView) rootView.findViewById(R.id.txtFansCount)).setText(userDataModal.getUser().getBio());
        ((TextView) rootView.findViewById(R.id.txtDob)).setText(userDataModal.getUser().getDob());
        ((TextView) rootView.findViewById(R.id.txtCoachName)).setText(userDataModal.getUser().getCoach());
        ((TextView) rootView.findViewById(R.id.txtSportClub)).setText(userDataModal.getUser().getClub());
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
                imgComment.setColorFilter(ContextCompat.getColor(getContext(), R.color.transparent));
                imgCamera.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray_d));
                imgVideoCamera.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray_d));
                viewFlipper.setDisplayedChild(0);
                break;
            case R.id.imgCamera:
                imgComment.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray_d));
                imgCamera.setColorFilter(ContextCompat.getColor(getContext(), R.color.transparent));
                imgVideoCamera.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray_d));
                viewFlipper.setDisplayedChild(1);
                break;
            case R.id.imgVideoCamera:
                imgComment.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray_d));
                imgCamera.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray_d));
                imgVideoCamera.setColorFilter(ContextCompat.getColor(getContext(), R.color.transparent));
                viewFlipper.setDisplayedChild(2);
                break;
        }
    }

    /*******************************************************/
    private void setMyPhotoVideoData() {
        recyclerViewMyPhotos = rootView.findViewById(R.id.recyclerViewMyPhotos);
        recyclerViewMyVideos = rootView.findViewById(R.id.recyclerViewMyVideos);

        for (int i = 0; i <= 12; i++) {
            myPhotoList.add(new GalleryModel(R.drawable.player_image));
        }

        for (int i = 0; i <= 9; i++) {
            myVideoList.add(new GalleryModel(R.drawable.nature4));
        }

        MyPhotoVideoAdapter photoAdapter = new MyPhotoVideoAdapter(myPhotoList, mContext, "");
        MyPhotoVideoAdapter videoAdapter = new MyPhotoVideoAdapter(myVideoList, mContext, "video");
        GridLayoutManager photoLayoutManager = new GridLayoutManager(mContext, 4);
        GridLayoutManager videoLayoutManager = new GridLayoutManager(mContext, 4);
        recyclerViewMyPhotos.setLayoutManager(photoLayoutManager);
        recyclerViewMyPhotos.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMyPhotos.setAdapter(photoAdapter);

        recyclerViewMyVideos.setLayoutManager(videoLayoutManager);
        recyclerViewMyVideos.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMyVideos.setAdapter(videoAdapter);
    }
}
