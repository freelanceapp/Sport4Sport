package technology.infobite.com.sportsforsports.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.Feed;
import technology.infobite.com.sportsforsports.ui.activity.PostDetailActivity;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_TEXT = 0;
    public static final int VIEW_TYPE_IMAGE = 1;
    public static final int VIEW_TYPE_VIDEO = 2;

    private List<Feed> mInfoList;
    private Context mContext;
    private Feed feed;

    public VideoRecyclerViewAdapter(Context mContext, List<Feed> infoList) {
        this.mContext = mContext;
        this.mInfoList = infoList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_VIDEO:
                return new ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_timeline_video, parent, false));
            case VIEW_TYPE_TEXT:
                return new HeadlineViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_timeline_text, parent, false));
            case VIEW_TYPE_IMAGE:
                return new ImageViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_timeline_image, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        /*holder.onBind(position);*/
        feed = mInfoList.get(position);
        switch (holder.getItemViewType()) {
            case 0:
                HeadlineViewHolder viewHolder = (HeadlineViewHolder) holder;
                viewHolder.tvUserName.setText(feed.getPostUserName());
                viewHolder.tvPostDescription.setText(feed.getAthleteStatus());
                viewHolder.tvHeadline.setText(feed.getAthleteArticeHeadline());
                viewHolder.llPostComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, PostDetailActivity.class);
                        intent.putExtra("post_detail_model", (Parcelable) feed);
                        mContext.startActivity(intent);
                    }
                });

                if (feed.getComment().size() == 0) {
                    viewHolder.tvTotalComment.setVisibility(View.GONE);
                } else if (feed.getComment().size() == 1) {
                    viewHolder.tvTotalComment.setVisibility(View.VISIBLE);
                    viewHolder.tvTotalComment.setText("View " + feed.getComment().size() + " comments");
                } else {
                    viewHolder.tvTotalComment.setVisibility(View.VISIBLE);
                    viewHolder.tvTotalComment.setText("View all " + feed.getComment().size() + " comments");
                }

                if (feed.getLikes() == null || feed.getLikes().isEmpty()) {
                    viewHolder.tvPostLikeCount.setText("0 like");
                } else {
                    viewHolder.tvPostLikeCount.setText(feed.getLikes() + " like");
                }

                if (feed.getComment() == null || feed.getComment().isEmpty()) {
                    viewHolder.tvCommentCount.setText("0 comment");
                } else {
                    viewHolder.tvCommentCount.setText(feed.getComment().size() + " comment");
                }

                if (feed.getEntryDate() == null || feed.getEntryDate().isEmpty()) {
                    viewHolder.tvPostTime.setVisibility(View.GONE);
                } else {
                    viewHolder.tvPostTime.setVisibility(View.VISIBLE);
                    viewHolder.tvPostTime.setText(feed.getEntryDate());
                }
                break;
            case 1:
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                imageViewHolder.tvUserName.setText(feed.getPostUserName());
                imageViewHolder.tvPostDescription.setText(feed.getAthleteStatus());
                imageViewHolder.llPostComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, PostDetailActivity.class);
                        intent.putExtra("post_detail_model", (Parcelable) feed);
                        mContext.startActivity(intent);
                    }
                });
                if (feed.getComment().size() == 0) {
                    imageViewHolder.tvTotalComment.setVisibility(View.GONE);
                } else if (feed.getComment().size() == 1) {
                    imageViewHolder.tvTotalComment.setVisibility(View.VISIBLE);
                    imageViewHolder.tvTotalComment.setText("View " + feed.getComment().size() + " comments");
                } else {
                    imageViewHolder.tvTotalComment.setVisibility(View.VISIBLE);
                    imageViewHolder.tvTotalComment.setText("View all " + feed.getComment().size() + " comments");
                }

                if (feed.getLikes() == null || feed.getLikes().isEmpty()) {
                    imageViewHolder.tvPostLikeCount.setText("0 like");
                } else {
                    imageViewHolder.tvPostLikeCount.setText(feed.getLikes() + " like");
                }

                if (feed.getComment() == null || feed.getComment().isEmpty()) {
                    imageViewHolder.tvCommentCount.setText("0 comment");
                } else {
                    imageViewHolder.tvCommentCount.setText(feed.getComment().size() + " comment");
                }

                if (feed.getEntryDate() == null || feed.getEntryDate().isEmpty()) {
                    imageViewHolder.tvPostTime.setVisibility(View.GONE);
                } else {
                    imageViewHolder.tvPostTime.setVisibility(View.VISIBLE);
                    imageViewHolder.tvPostTime.setText(feed.getEntryDate());
                }
                Glide.with(imageViewHolder.itemView.getContext())
                        .load(Constant.IMAGE_BASE_URL + feed.getAlhleteImages())
                        .apply(new RequestOptions().optionalCenterCrop())
                        .into(imageViewHolder.imgPostImage);
                break;
            case 2:
                ViewHolder videoViewHolder = (ViewHolder) holder;
                videoViewHolder.tvUserName.setText(feed.getPostUserName());
                videoViewHolder.tvPostDescription.setText(feed.getAthleteStatus());
                videoViewHolder.llPostComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, PostDetailActivity.class);
                        intent.putExtra("post_detail_model", (Parcelable) feed);
                        mContext.startActivity(intent);
                    }
                });
                if (feed.getComment().size() == 0) {
                    videoViewHolder.tvTotalComment.setVisibility(View.GONE);
                } else if (feed.getComment().size() == 1) {
                    videoViewHolder.tvTotalComment.setVisibility(View.VISIBLE);
                    videoViewHolder.tvTotalComment.setText("View " + feed.getComment().size() + " comments");
                } else {
                    videoViewHolder.tvTotalComment.setVisibility(View.VISIBLE);
                    videoViewHolder.tvTotalComment.setText("View all " + feed.getComment().size() + " comments");
                }

                if (feed.getLikes() == null || feed.getLikes().isEmpty()) {
                    videoViewHolder.tvPostLikeCount.setText("0 like");
                } else {
                    videoViewHolder.tvPostLikeCount.setText(feed.getLikes() + " like");
                }

                if (feed.getComment() == null || feed.getComment().isEmpty()) {
                    videoViewHolder.tvCommentCount.setText("0 comment");
                } else {
                    videoViewHolder.tvCommentCount.setText(feed.getComment().size() + " comment");
                }

                if (feed.getEntryDate() == null || feed.getEntryDate().isEmpty()) {
                    videoViewHolder.tvPostTime.setText("");
                    videoViewHolder.tvPostTime.setVisibility(View.GONE);
                } else {
                    videoViewHolder.tvPostTime.setVisibility(View.VISIBLE);
                    videoViewHolder.tvPostTime.setText(feed.getEntryDate());
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!mInfoList.get(position).getAthleteVideo().isEmpty()) {
            return VIEW_TYPE_VIDEO;
        } else if (!mInfoList.get(position).getAlhleteImages().isEmpty()) {
            return VIEW_TYPE_IMAGE;
        } else {
            return VIEW_TYPE_TEXT;
        }
    }

    @Override
    public int getItemCount() {
        if (mInfoList != null && mInfoList.size() > 0) {
            return mInfoList.size();
        } else {
            return 1;
        }
    }

    public void onRelease() {
        if (mInfoList != null) {
            mInfoList.clear();
            mInfoList = null;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlPost;
        private LinearLayout llLikePost, llPostComment;
        private ImageView imgLike;
        private CircleImageView imgUserProfile;
        private TextView tvUserName, tvPostLikeCount, tvCommentCount, tvPostTime, tvTotalComment, tvPostDescription;
        private CardView cardViewVideo;

        public ImageView mCover;
        public ProgressBar mProgressBar;
        public final View parent;

        public ViewHolder(View itemView) {
            super(itemView);
            parent = itemView;
            parent.setTag(this);
            cardViewVideo = itemView.findViewById(R.id.cardViewVideo);
            mCover = itemView.findViewById(R.id.cover);
            mProgressBar = itemView.findViewById(R.id.progressBar);

            rlPost = itemView.findViewById(R.id.rlPost);
            imgUserProfile = itemView.findViewById(R.id.imgUserProfile);
            tvUserName = itemView.findViewById(R.id.tvUserName);
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

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlPost;
        private LinearLayout llLikePost, llPostComment;
        private ImageView imgPostImage, imgLike;
        private CircleImageView imgUserProfile;
        private TextView tvUserName, tvPostLikeCount, tvCommentCount, tvPostTime, tvTotalComment, tvPostDescription;

        public ImageViewHolder(View itemView) {
            super(itemView);
            rlPost = itemView.findViewById(R.id.rlPost);
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

    public class HeadlineViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlPost;
        private LinearLayout llLikePost, llPostComment;
        private ImageView imgLike;
        private CircleImageView imgUserProfile;
        private TextView tvHeadline, tvUserName, tvPostLikeCount, tvCommentCount, tvPostTime, tvTotalComment, tvPostDescription;

        public HeadlineViewHolder(View itemView) {
            super(itemView);
            rlPost = itemView.findViewById(R.id.rlPost);
            imgUserProfile = itemView.findViewById(R.id.imgUserProfile);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvHeadline = itemView.findViewById(R.id.tvHeadline);
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
