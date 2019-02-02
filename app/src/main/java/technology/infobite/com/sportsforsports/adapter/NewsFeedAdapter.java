package technology.infobite.com.sportsforsports.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.List;

import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.Feed;
import technology.infobite.com.sportsforsports.ui.activity.PostDetailActivity;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {

    private boolean check = false;
    private List<Feed> newPostModels;
    private Context ctx;
    private View.OnClickListener onClickListener;

    public NewsFeedAdapter(List<Feed> newPostModels, Context ctx, View.OnClickListener onClickListener) {
        this.newPostModels = newPostModels;
        this.ctx = ctx;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(ctx);
        View viewt = li.inflate(R.layout.row_news_item, null);
        return new ViewHolder(viewt);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final Feed newPostModel = newPostModels.get(i);
        viewHolder.postpersonname.setText(newPostModel.getPostUserName());
        viewHolder.description.setText(newPostModel.getAthleteStatus());
        viewHolder.rlPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, PostDetailActivity.class);
                intent.putExtra("post_detail_model", (Parcelable) newPostModel);
                ctx.startActivity(intent);
            }
        });

        if (!newPostModel.getAthleteArticeHeadline().isEmpty()) {
            viewHolder.tv_headline.setVisibility(View.VISIBLE);
            viewHolder.postImage.setVisibility(View.GONE);
            viewHolder.postvideo.setVisibility(View.GONE);
            viewHolder.progressBar.setVisibility(View.GONE);
            viewHolder.tv_headline.setText(newPostModel.getAthleteArticeHeadline());
        } else if (!newPostModel.getAlhleteImages().isEmpty()) {
            viewHolder.postImage.setVisibility(View.VISIBLE);
            viewHolder.tv_headline.setVisibility(View.GONE);
            viewHolder.postvideo.setVisibility(View.GONE);
            viewHolder.progressBar.setVisibility(View.GONE);
            String currentString = newPostModel.getAlhleteImages();
            Picasso.with(ctx).load(Constant.IMAGE_BASE_URL + currentString)
                    .placeholder(R.drawable.app_logo)
                    //.resize(250, 350)
                    .into(viewHolder.postImage);
        } else if (!newPostModel.getAthleteVideo().isEmpty()) {
            viewHolder.postvideo.setVisibility(View.VISIBLE);
            viewHolder.tv_headline.setVisibility(View.GONE);
            viewHolder.postImage.setVisibility(View.GONE);
            viewHolder.progressBar.setVisibility(View.VISIBLE);
            String strVideoUrl = newPostModel.getAthleteVideo();
            Uri uri = Uri.parse(Constant.VIDEO_BASE_URL + strVideoUrl);
            viewHolder.postvideo.setVideoURI(uri);
            viewHolder.postvideo.start();
            viewHolder.postvideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    viewHolder.progressBar.setVisibility(View.GONE);
                }
            });
        }

        if (newPostModel.getLikes() == null || newPostModel.getLikes().isEmpty()) {
            viewHolder.likes.setText("0 like");
        } else {
            viewHolder.likes.setText(newPostModel.getLikes() + " like");
        }
        if (newPostModel.getComment() == null || newPostModel.getComment().isEmpty()) {
            viewHolder.comments.setText("0 comment");
        } else {
            viewHolder.comments.setText(newPostModel.getComment().size() + " comment");
        }
        if (newPostModel.getEntryDate() == null || newPostModel.getEntryDate().isEmpty()) {
            viewHolder.timeduration.setText("");
        } else {
            viewHolder.timeduration.setText(newPostModel.getEntryDate());
        }

        viewHolder.llViewUserProfile.setTag(i);
        viewHolder.llViewUserProfile.setOnClickListener(onClickListener);
        viewHolder.llPostComment.setTag(i);
        viewHolder.llPostComment.setOnClickListener(onClickListener);
        viewHolder.llLikePost.setTag(i);
        viewHolder.llLikePost.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return newPostModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlPost;
        private LinearLayout llViewUserProfile, llPostComment, llLikePost;
        private ImageView profile, postImage;
        private TextView postpersonname, likes, comments, timeduration, description, totalcommentcounts, tv_headline;
        private EditText posteditmessage;
        private Button postsend;
        private VideoView postvideo;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
            llPostComment = itemView.findViewById(R.id.llPostComment);
            llLikePost = itemView.findViewById(R.id.llLikePost);
            rlPost = itemView.findViewById(R.id.rlPost);
            llViewUserProfile = itemView.findViewById(R.id.llViewUserProfile);
            profile = itemView.findViewById(R.id.post_person_profile);
            postpersonname = itemView.findViewById(R.id.post_person_name);
            tv_headline = itemView.findViewById(R.id.get_tv_headline);
            postImage = itemView.findViewById(R.id.get_post_image);
            postvideo = itemView.findViewById(R.id.get_post_video);
            likes = itemView.findViewById(R.id.get_post_likes);
            comments = itemView.findViewById(R.id.get_post_comments);
            timeduration = itemView.findViewById(R.id.get_post_time);
            description = itemView.findViewById(R.id.post_description);
            totalcommentcounts = itemView.findViewById(R.id.post_total_comments);
            posteditmessage = itemView.findViewById(R.id.edit_post_comment);
            postsend = itemView.findViewById(R.id.post_comment_send);
        }
    }
}

