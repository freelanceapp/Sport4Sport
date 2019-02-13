package technology.infobite.com.sportsforsports.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.UserFeed;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.utils.BaseFragment;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;

public class DraggabbleTopFragment extends BaseFragment {

    private View rootView;
    private CircleImageView imgUserProfile;
    private ImageView imgPostImage;
    private TextView tvUserName, tvPostTime;
    private VideoView videoViewPost;
    private ProgressBar progressBar;
    private UserFeed imageFeed;

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
        imgUserProfile = rootView.findViewById(R.id.imgUserProfile);
        imgPostImage = rootView.findViewById(R.id.imgPostImage);
        tvUserName = rootView.findViewById(R.id.tvUserName);
        tvPostTime = rootView.findViewById(R.id.tvPostTime);
        videoViewPost = rootView.findViewById(R.id.videoViewPost);
        progressBar = rootView.findViewById(R.id.progressBar);
    }

    public void showImage(UserFeed imageFeed) {
        this.imageFeed = imageFeed;
        tvUserName.setText(imageFeed.getPostUserName());
        tvPostTime.setText(imageFeed.getEntryDate());
        imgPostImage.setVisibility(View.VISIBLE);
        Picasso.with(mContext)
                .load(Constant.IMAGE_BASE_URL + imageFeed.getAlhleteImages())
                .placeholder(R.drawable.app_logo)
                .into(imgPostImage);

        Picasso.with(mContext)
                .load(Constant.PROFILE_IMAGE_BASE_URL + imageFeed.getPostUserImage())
                .placeholder(R.drawable.app_logo)
                .into(imgUserProfile);
    }
}
