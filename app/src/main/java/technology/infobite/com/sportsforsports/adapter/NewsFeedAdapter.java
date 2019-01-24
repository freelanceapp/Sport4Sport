package technology.infobite.com.sportsforsports.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import technology.infobite.com.sportsforsports.NewPostModel;
import technology.infobite.com.sportsforsports.R;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {

    private boolean check = false;
    private List<NewPostModel> newPostModels;
    private Context ctx;
    private View.OnClickListener onClickListener;

    public NewsFeedAdapter(List<NewPostModel> newPostModels, Context ctx, View.OnClickListener onClickListener) {
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

        NewPostModel newPostModel = newPostModels.get(i);

        viewHolder.profile.setImageDrawable(ctx.getResources().getDrawable(newPostModel.getImage1()));
        viewHolder.postpersonname.setText(newPostModel.getName());
        viewHolder.postprofile.setImageDrawable(ctx.getResources().getDrawable(newPostModel.getPost()));
        viewHolder.likes.setText(newPostModel.getLike());
        viewHolder.comments.setText(newPostModel.getComent());
        viewHolder.timeduration.setText(newPostModel.getTimeDuration());
        viewHolder.description.setText(newPostModel.getText1());
        viewHolder.totalcommentcounts.setText(newPostModel.getAllcomment());

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout commentsection, visibesendmessage, llViewUserProfile;
        ImageView profile, postprofile;
        TextView postpersonname, likes, comments, timeduration, description, totalcommentcounts;
        EditText posteditmessage;
        Button postsend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            llViewUserProfile = itemView.findViewById(R.id.llViewUserProfile);
            profile = itemView.findViewById(R.id.post_person_profile);
            postpersonname = itemView.findViewById(R.id.post_person_name);
            postprofile = itemView.findViewById(R.id.post_image);
            likes = itemView.findViewById(R.id.post_no_of_likes);
            comments = itemView.findViewById(R.id.post_no_of_comments);
            timeduration = itemView.findViewById(R.id.post_time);
            description = itemView.findViewById(R.id.post_description);
            totalcommentcounts = itemView.findViewById(R.id.post_total_comments);
            posteditmessage = itemView.findViewById(R.id.post_edit_messsage);
            postsend = itemView.findViewById(R.id.post_comment_send);
            commentsection = itemView.findViewById(R.id.commentsection);
            visibesendmessage = itemView.findViewById(R.id.visibesendmessage);
        }
    }
}

