package technology.infobite.com.sportsforsports.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.Feed;

public class PostDetailActivity extends AppCompatActivity {
    private Feed postDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        TextView showpostpersonname = findViewById(R.id._showpost_person_name);
        TextView showopostheadline = findViewById(R.id.tv_headline_show);
        ImageView showpostimage = findViewById(R.id.post_image_show);
        VideoView showpostvideo = findViewById(R.id.post_video_show);
        TextView showopostlikes = findViewById(R.id.show_post_likes);
        TextView showopostcomments = findViewById(R.id.show_post_comments);
        TextView showoposttime = findViewById(R.id.show_post_time);

        Intent intent = getIntent();
        postDetails = intent.getParcelableExtra("post_detail_model");

        showpostpersonname.setText("Virat Kohli");
        if (postDetails.getAthleteArticeHeadline() == null || postDetails.getAthleteArticeHeadline().isEmpty()) {
           showopostheadline.setVisibility(View.GONE);
            showpostimage.setImageDrawable(getResources().getDrawable(R.drawable.player_image));
        } else {
            showopostheadline.setVisibility(View.VISIBLE);
            showpostimage.setVisibility(View.GONE);
           showopostheadline.setText(postDetails.getAthleteArticeHeadline());
        }
        if (postDetails.getAlhleteImages() == null || postDetails.getAlhleteImages().isEmpty()) {
            String currentString = postDetails.getAlhleteImages();
            Log.e("image", "..." + currentString);
           showpostimage.setVisibility(View.GONE);
        } else {
            showopostheadline.setVisibility(View.VISIBLE);
            showpostimage.setVisibility(View.VISIBLE);
            String currentString = postDetails.getAlhleteImages();
            Picasso.with(getApplicationContext()).load("http://codeencrypt.in/sport/images/alhlete_images/" + currentString).placeholder(R.drawable.player_image).resize(250, 500).into(showpostimage);
        }
        if (postDetails.getLikes() == null || postDetails.getLikes().isEmpty()) {
            showopostlikes.setText("0 like");
        } else {
            showopostlikes.setText(postDetails.getLikes() +" like");
        }
        if (postDetails.getComment() == null || postDetails.getComment().isEmpty()) {
            showopostcomments.setText("0 comment");
        } else {
            showopostcomments.setText(postDetails.getComment().size() +" comment");
        }
        if (postDetails.getEntryDate() == null || postDetails.getEntryDate().isEmpty()) {
            showopostlikes.setText("");
        } else {
            showoposttime.setText(postDetails.getEntryDate());
        }
        postDetails.getAthleteVideo();




    }
}
