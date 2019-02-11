package technology.infobite.com.sportsforsports.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Response;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.UserFeed;
import technology.infobite.com.sportsforsports.modal.user_data.UserDataModal;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitApiClient;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.ui.activity.PostDetailActivity;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;

public class UserFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<UserFeed> mInfoList;
    private Context mContext;
    private View.OnClickListener onClickListener;
    private RetrofitApiClient retrofitApiClient;

    public UserFeedAdapter(Context mContext, List<UserFeed> infoList, View.OnClickListener onClickListener,
                           RetrofitApiClient retrofitApiClient) {
        this.mContext = mContext;
        this.mInfoList = infoList;
        this.onClickListener = onClickListener;
        this.retrofitApiClient = retrofitApiClient;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_user_timeline, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        /*holder.onBind(position);*/
        final UserFeed imageFeed;
        if (AppPreference.getBooleanPreference(mContext, "userlikedPost")) {
            String strTimelineData = AppPreference.getStringPreference(mContext, Constant.USER_TIMELINE_DATA);
            Gson getGson = new Gson();
            UserDataModal dailyNewsFeedMainModal = getGson.fromJson(strTimelineData, UserDataModal.class);
            imageFeed = dailyNewsFeedMainModal.getFeed().get(position);
            int pos = position + 1;
            if (mInfoList.size() == pos) {
                AppPreference.setBooleanPreference(mContext, "userlikedPost", false);
            }
        } else {
            imageFeed = mInfoList.get(position);
        }

        String strUserName = AppPreference.getStringPreference(mContext, Constant.USER_NAME);
        String strUserImage = AppPreference.getStringPreference(mContext, Constant.USER_IMAGE);

        final ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
        imageViewHolder.tvUserName.setText(strUserName);
        imageViewHolder.tvPostDescription.setText(imageFeed.getAthleteStatus());

        imageViewHolder.imgLike.setTag(position);
        //imageViewHolder.viewData.setTag(position);

        imageViewHolder.llPostComment.setTag(position);
        imageViewHolder.llPostComment.setOnClickListener(onClickListener);
        imageViewHolder.tvTotalComment.setTag(position);
        imageViewHolder.tvTotalComment.setOnClickListener(onClickListener);

        imageViewHolder.llLikePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //likeApi(imageFeed, imageViewHolder.imgLike, imageViewHolder.tvPostLikeCount);
            }
        });

       /* if (imageFeed.getIsLike().equals("unlike")) {
            imageViewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_icon));
        } else {
            imageViewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }*/

        if (imageFeed.getComment().size() == 0) {
            imageViewHolder.tvTotalComment.setVisibility(View.GONE);
        } else if (imageFeed.getComment().size() == 1) {
            imageViewHolder.tvTotalComment.setVisibility(View.VISIBLE);
            imageViewHolder.tvTotalComment.setText("View " + imageFeed.getComment().size() + " comments");
        } else {
            imageViewHolder.tvTotalComment.setVisibility(View.VISIBLE);
            imageViewHolder.tvTotalComment.setText("View all " + imageFeed.getComment().size() + " comments");
        }

        if (imageFeed.getLikes() == null || imageFeed.getLikes().isEmpty()) {
            imageViewHolder.tvPostLikeCount.setText("0 like");
        } else {
            imageViewHolder.tvPostLikeCount.setText(imageFeed.getLikes() + " like");
        }

        if (imageFeed.getComment() == null || imageFeed.getComment().isEmpty()) {
            imageViewHolder.tvCommentCount.setText("0 comment");
        } else {
            imageViewHolder.tvCommentCount.setText(imageFeed.getComment().size() + " comment");
        }

        if (imageFeed.getEntryDate() == null || imageFeed.getEntryDate().isEmpty()) {
            imageViewHolder.tvPostTime.setVisibility(View.GONE);
        } else {
            imageViewHolder.tvPostTime.setVisibility(View.VISIBLE);
            imageViewHolder.tvPostTime.setText(imageFeed.getEntryDate());
        }

        if (!imageFeed.getAthleteArticeHeadline().isEmpty()) {
            imageViewHolder.rlImageVideo.setVisibility(View.GONE);
            imageViewHolder.llHeadline.setVisibility(View.VISIBLE);
            imageViewHolder.tvHeadline.setVisibility(View.VISIBLE);
            imageViewHolder.imgPostImage.setVisibility(View.GONE);
            imageViewHolder.tvHeadline.setText(imageFeed.getAthleteArticeHeadline());
        } else if (!imageFeed.getAlhleteImages().isEmpty()) {
            imageViewHolder.rlImageVideo.setVisibility(View.VISIBLE);
            imageViewHolder.llHeadline.setVisibility(View.GONE);
            imageViewHolder.imgPlayVideo.setVisibility(View.GONE);

            imageViewHolder.tvHeadline.setVisibility(View.GONE);
            imageViewHolder.imgPostImage.setVisibility(View.VISIBLE);
            Glide.with(imageViewHolder.itemView.getContext())
                    .load(Constant.IMAGE_BASE_URL + imageFeed.getAlhleteImages())
                    .apply(new RequestOptions().optionalCenterCrop())
                    .into(imageViewHolder.imgPhotoVideo);

        } else if (!imageFeed.getAthleteVideo().isEmpty()) {
            imageViewHolder.imgPlayVideo.setVisibility(View.GONE);
            imageViewHolder.rlImageVideo.setVisibility(View.VISIBLE);
            imageViewHolder.llHeadline.setVisibility(View.GONE);

            imageViewHolder.tvHeadline.setVisibility(View.GONE);
            imageViewHolder.imgPostImage.setVisibility(View.VISIBLE);

           /* if (position>4){
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(Constant.VIDEO_BASE_URL + imageFeed.getAthleteVideo(), new HashMap<String, String>());
                Bitmap  bitmap = retriever.getFrameAtTime(2000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                //bitmap = Bitmap.createScaledBitmap(bitmap, 240, 240, false);
                imageViewHolder.imgPhotoVideo.setImageBitmap(bitmap);
            }*/
        }

        imageViewHolder.rlImageVideo.setTag(position);
        imageViewHolder.rlImageVideo.setOnClickListener(this);
        imageViewHolder.llHeadline.setTag(position);
        imageViewHolder.llHeadline.setOnClickListener(this);

        Glide.with(imageViewHolder.itemView.getContext())
                .load(Constant.PROFILE_IMAGE_BASE_URL + strUserImage)
                .apply(new RequestOptions().optionalCenterCrop())
                .into(imageViewHolder.imgUserProfile);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llHeadline:
            case R.id.rlImageVideo:
                sendPostData(v);
                break;
        }
    }

    private void sendPostData(View view) {
        int pos = Integer.parseInt(view.getTag().toString());
        UserFeed imageFeed = mInfoList.get(pos);

        String strName = AppPreference.getStringPreference(mContext, Constant.USER_NAME);
        String strProfilePhoto = AppPreference.getStringPreference(mContext, Constant.USER_IMAGE);
        imageFeed.setPostUserName(strName);
        imageFeed.setPostUserImage(strProfilePhoto);
        imageFeed.setIsLike("unlike");

        Gson gson = new GsonBuilder().setLenient().create();
        String data = gson.toJson(imageFeed);
        AppPreference.setStringPreference(mContext, Constant.POST_DETAIL, data);

        Intent intent = new Intent(mContext, PostDetailActivity.class);
        intent.putExtra("from", "user");
        intent.putExtra("post_id", imageFeed.getFeedId());
        mContext.startActivity(intent);
    }

    private void likeApi(final UserFeed feed, final ImageView imgLike, final TextView textView) {
        final String strId = AppPreference.getStringPreference(mContext, Constant.USER_ID);

        RetrofitService.getLikeResponse(retrofitApiClient.postLike(feed.getFeedId(), strId, "1"), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                ResponseBody responseBody = (ResponseBody) result.body();
                try {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    if (!jsonObject.getBoolean("error")) {
                        /*if (feed.getIsLike().equals("unlike")) {
                            imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_fill));
                        } else {
                            imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_icon));
                        }*/
                        textView.setText(jsonObject.getString("total_fan") + " like");
                        refreshTimelineApi(strId);
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

    private void refreshTimelineApi(String strId) {
        RetrofitService.getRefreshLoginData(retrofitApiClient.userProfile(strId), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                UserDataModal dailyNewsFeedMainModal = (UserDataModal) result.body();
                mInfoList.clear();
                if (dailyNewsFeedMainModal.getError()) {
                    Alerts.show(mContext, "No data");
                } else {
                    Gson gson = new GsonBuilder().setLenient().create();
                    String data = gson.toJson(dailyNewsFeedMainModal);
                    AppPreference.setStringPreference(mContext, Constant.USER_TIMELINE_DATA, data);
                    AppPreference.setBooleanPreference(mContext, "userlikedPost", true);
                    mInfoList.addAll(dailyNewsFeedMainModal.getFeed());
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onResponseFailed(String error) {
                Alerts.show(mContext, error);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mInfoList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlPost;
        private LinearLayout llLikePost, llPostComment, llHeadline;
        private RelativeLayout rlImageVideo;
        private ImageView imgPostImage, imgLike, imgPlayVideo, imgPhotoVideo;
        private CircleImageView imgUserProfile;
        private TextView tvHeadline, tvUserName, tvPostLikeCount, tvCommentCount, tvPostTime, tvTotalComment, tvPostDescription;
        public final View viewData;

        public ImageViewHolder(View itemView) {
            super(itemView);
            viewData = itemView;
            imgPhotoVideo = itemView.findViewById(R.id.imgPhotoVideo);
            imgPlayVideo = itemView.findViewById(R.id.imgPlayVideo);
            llHeadline = itemView.findViewById(R.id.llHeadline);
            rlImageVideo = itemView.findViewById(R.id.rlImageVideo);
            rlPost = itemView.findViewById(R.id.rlPost);
            tvHeadline = itemView.findViewById(R.id.tvHeadline);
            imgUserProfile = itemView.findViewById(R.id.imgUserProfile);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            imgPostImage = itemView.findViewById(R.id.imgPostImage);
            imgLike = itemView.findViewById(R.id.imgLike);
            llLikePost = itemView.findViewById(R.id.llLikePost);
            llPostComment = itemView.findViewById(R.id.llPostComment);
            tvPostLikeCount = itemView.findViewById(R.id.tvPostLikeCount);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            tvPostTime = itemView.findViewById(R.id.tvPostTime);
            tvTotalComment = itemView.findViewById(R.id.tvTotalComment);
            tvPostDescription = itemView.findViewById(R.id.tvPostDescription);
        }
    }
}
