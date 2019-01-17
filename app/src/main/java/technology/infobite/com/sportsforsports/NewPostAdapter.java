package technology.infobite.com.sportsforsports;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewPostAdapter extends RecyclerView.Adapter<NewPostAdapter.ViewHolder> {
    private boolean check=false;
    private List<CommentModel1> commentModels1;
    private List<NewPostModel> newPostModels;
    Context ctx;

    public NewPostAdapter(List<NewPostModel> newPostModels, Context ctx, List<CommentModel1> commentModels1) {
        this.newPostModels = newPostModels;
        this.ctx = ctx;
        this.commentModels1 = commentModels1;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater li = LayoutInflater.from(ctx);
        View viewt = li.inflate(R.layout.post_rclv_layout, null);
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

        viewHolder.totalcommentcounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {
                    check=false;
                    viewHolder.postrecylerviewcomment.setVisibility(View.GONE);
                }  else {
                    check=true;
                    viewHolder.postrecylerviewcomment.setVisibility(View.VISIBLE);
                }
            }
        });
        viewHolder.visibesendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {
                    check=false;
                    viewHolder.commentsection.setVisibility(View.GONE);
                }  else {
                    check=true;
                    viewHolder.commentsection.setVisibility(View.VISIBLE);
                }
            }
        });


        viewHolder.postrecylerviewcomment.setHasFixedSize(true);
        RecyclerViewCommentAdapter recyclerViewCommentAdapter = new RecyclerViewCommentAdapter(commentModels1, ctx);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
        viewHolder.postrecylerviewcomment.setLayoutManager(layoutManager);
        viewHolder.postrecylerviewcomment.setItemAnimator(new DefaultItemAnimator());
        viewHolder.postrecylerviewcomment.setAdapter(recyclerViewCommentAdapter);
    }

    @Override
    public int getItemCount() {
        return newPostModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout commentsection,visibesendmessage;
        ImageView profile, postprofile;
        TextView postpersonname, likes, comments, timeduration, description, totalcommentcounts;
        RecyclerView postrecylerviewcomment;
        EditText posteditmessage;

        Button postsend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

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
            postrecylerviewcomment = itemView.findViewById(R.id.post_rclv_rclv);
            commentsection = itemView.findViewById(R.id.commentsection);
            visibesendmessage = itemView.findViewById(R.id.visibesendmessage);
        }
    }
}

