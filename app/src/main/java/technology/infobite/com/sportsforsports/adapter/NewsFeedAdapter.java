package technology.infobite.com.sportsforsports.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import technology.infobite.com.sportsforsports.R;
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
        /*send data on post detail actiivity*/
        final Feed newPostModel = newPostModels.get(i);
        viewHolder.lloperpostdetailactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, PostDetailActivity.class);
                intent.putExtra("post_detail_model", (Parcelable) newPostModel);
                ctx.startActivity(intent);
            }
        });

        if (newPostModel.getAlhleteImages() == null || newPostModel.getAlhleteImages().isEmpty()) {
            viewHolder.postImage.setVisibility(View.GONE);
        } else {
            String currentString = newPostModel.getAlhleteImages();
            Picasso.with(ctx).load("http://infobitetechnology.in/sportforsport/" + currentString)
                    .placeholder(R.drawable.player_image)
                    .resize(250, 500)
                    .into(viewHolder.postImage);
        }

        if (newPostModel.getAthleteArticeHeadline() == null || newPostModel.getAthleteArticeHeadline().isEmpty()) {
            // viewHolder.postImage.setVisibility(View.VISIBLE);
            viewHolder.tv_headline.setVisibility(View.GONE);
            viewHolder.postImage.setImageDrawable(ctx.getResources().getDrawable(R.drawable.player_image));
        } else {
            viewHolder.tv_headline.setVisibility(View.VISIBLE);
            viewHolder.postImage.setVisibility(View.GONE);
            viewHolder.tv_headline.setText(newPostModel.getAthleteArticeHeadline());
        }
        viewHolder.likes.setText(newPostModel.getLikes() + " likes");
        viewHolder.postpersonname.setText("Virat kohli");
        viewHolder.timeduration.setText("2 HOURS AGO");
        viewHolder.postsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.post_comment_send:
                        if (viewHolder.postsend.isPressed()) {

                        }
                        break;
                }
            }
        });

        viewHolder.visibesendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {
                    check = false;
                    viewHolder.commentsection.setVisibility(View.GONE);
                } else {
                    check = true;
                    viewHolder.commentsection.setVisibility(View.VISIBLE);
                }
            }
        });

        viewHolder.llViewUserProfile.setTag(i);
        viewHolder.llViewUserProfile.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return newPostModels.size();
    }
/*
    private void newPostCommentApi(){
        String strId = AppPreference.getStringPreference(ctx, Constant.USER_ID);

    }
*/

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout commentsection, visibesendmessage, llViewUserProfile, lloperpostdetailactivity;
        ImageView profile, postImage;
        TextView postpersonname, likes, comments, timeduration, description, totalcommentcounts, tv_headline;
        EditText posteditmessage;
        Button postsend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lloperpostdetailactivity = itemView.findViewById(R.id.ll_open_post_detail_activity);
            tv_headline = itemView.findViewById(R.id.tv_headline);
            llViewUserProfile = itemView.findViewById(R.id.llViewUserProfile);
            profile = itemView.findViewById(R.id.post_person_profile);
            postpersonname = itemView.findViewById(R.id.post_person_name);
            postImage = itemView.findViewById(R.id.post_image);
            likes = itemView.findViewById(R.id.post_no_of_likes);
            comments = itemView.findViewById(R.id.post_no_of_comments);
            timeduration = itemView.findViewById(R.id.post_time);
            description = itemView.findViewById(R.id.post_description);
            totalcommentcounts = itemView.findViewById(R.id.post_total_comments);
            posteditmessage = itemView.findViewById(R.id.edit_post_comment);
            postsend = itemView.findViewById(R.id.post_comment_send);
            commentsection = itemView.findViewById(R.id.commentsection);
            visibesendmessage = itemView.findViewById(R.id.visibesendmessage);

        }
    }
}

