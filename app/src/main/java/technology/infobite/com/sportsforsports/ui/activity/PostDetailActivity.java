package technology.infobite.com.sportsforsports.ui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.Feed;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseActivity;

public class PostDetailActivity extends BaseActivity implements View.OnClickListener {

    private Feed newPostModel;

    private LinearLayout llViewUserProfile, llPostComment, llLikePost;
    private ImageView profile, postImage;
    private TextView postpersonname, likes, comments, timeduration, description, totalcommentcounts, tv_headline;
    private EditText posteditmessage;
    private Button postsend;
    private VideoView postvideo;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        init();
    }

    private void init() {
        Intent intent = getIntent();
        newPostModel = intent.getParcelableExtra("post_detail_model");
        progressBar = findViewById(R.id.progressBar);
        llPostComment = findViewById(R.id.llPostComment);
        llLikePost = findViewById(R.id.llLikePost);
        llViewUserProfile = findViewById(R.id.llViewUserProfile);
        profile = findViewById(R.id.post_person_profile);
        postpersonname = findViewById(R.id.post_person_name);
        tv_headline = findViewById(R.id.get_tv_headline);
        postImage = findViewById(R.id.get_post_image);
        postvideo = findViewById(R.id.get_post_video);
        likes = findViewById(R.id.get_post_likes);
        comments = findViewById(R.id.get_post_comments);
        timeduration = findViewById(R.id.get_post_time);
        description = findViewById(R.id.post_description);
        totalcommentcounts = findViewById(R.id.post_total_comments);
        posteditmessage = findViewById(R.id.edit_post_comment);
        postsend = findViewById(R.id.post_comment_send);

        findViewById(R.id.post_comment_send).setOnClickListener(this);
        llPostComment.setOnClickListener(this);
        setData();
    }

    private void setData() {
        if (!newPostModel.getAthleteArticeHeadline().isEmpty()) {
            tv_headline.setVisibility(View.VISIBLE);
            postImage.setVisibility(View.GONE);
            postvideo.setVisibility(View.GONE);
            tv_headline.setText(newPostModel.getAthleteArticeHeadline());
        } else if (!newPostModel.getAlhleteImages().isEmpty()) {
            postImage.setVisibility(View.VISIBLE);
            tv_headline.setVisibility(View.GONE);
            postvideo.setVisibility(View.GONE);
            String currentString = newPostModel.getAlhleteImages();
            Picasso.with(mContext).load(Constant.IMAGE_BASE_URL + currentString)
                    .placeholder(R.drawable.player_image)
                    //.resize(250, 350)
                    .into(postImage);
        } else if (!newPostModel.getAthleteVideo().isEmpty()) {
            postvideo.setVisibility(View.VISIBLE);
            tv_headline.setVisibility(View.GONE);
            postImage.setVisibility(View.GONE);
            String strVideoUrl = newPostModel.getAthleteVideo();
            Uri uri = Uri.parse(Constant.VIDEO_BASE_URL + strVideoUrl);
            postvideo.setVideoURI(uri);
            postvideo.start();
            progressBar.setVisibility(View.VISIBLE);
            postvideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

        if (newPostModel.getLikes() == null || newPostModel.getLikes().isEmpty()) {
            likes.setText("0 like");
        } else {
            likes.setText(newPostModel.getLikes() + " like");
        }
        if (newPostModel.getComment() == null || newPostModel.getComment().isEmpty()) {
            comments.setText("0 comment");
        } else {
            comments.setText(newPostModel.getComment().size() + " comment");
        }
        if (newPostModel.getEntryDate() == null || newPostModel.getEntryDate().isEmpty()) {
            timeduration.setText("");
        } else {
            timeduration.setText(newPostModel.getEntryDate());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llPostComment:
                ((CardView) findViewById(R.id.cardViewComment)).setVisibility(View.VISIBLE);
                break;
            case R.id.post_comment_send:
                Alerts.show(mContext, "Under development!!!");
                break;
        }
    }

    private void postCommentApi() {
        String strUserId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        String strPostId = newPostModel.getFeedId();
        String strComments = ((EditText) findViewById(R.id.edit_post_comment)).getText().toString();
    }
}
