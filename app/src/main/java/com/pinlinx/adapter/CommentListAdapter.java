package com.pinlinx.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pinlinx.R;
import com.pinlinx.constant.Constant;
import com.pinlinx.modal.daily_news_feed.Comment;
import com.pinlinx.utils.AppPreference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    private List<Comment> notificationModels;
    private Context context;
    private View.OnLongClickListener onLongClickListener;

    public CommentListAdapter(List<Comment> notificationModels, Context context, View.OnLongClickListener onLongClickListener) {
        this.notificationModels = notificationModels;
        this.context = context;
        this.onLongClickListener = onLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater li = LayoutInflater.from(context);
        View viewt = li.inflate(R.layout.row_comment_list, null);
        return new ViewHolder(viewt);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Comment notificationmodel = notificationModels.get(i);

        String strUserImage = AppPreference.getStringPreference(context, Constant.USER_IMAGE);

        if (notificationmodel.getUserImage() == null || notificationmodel.getUserImage().isEmpty()) {
            Glide.with(context).load(Constant.PROFILE_IMAGE_BASE_URL + strUserImage)
                    .placeholder(R.drawable.ic_profile)
                    .into(viewHolder.imgUserProfile);
        } else {
            Glide.with(context).load(Constant.PROFILE_IMAGE_BASE_URL + notificationmodel.getUserImage())
                    .placeholder(R.drawable.ic_profile)
                    .into(viewHolder.imgUserProfile);
        }

        viewHolder.tvUserName.setText(notificationmodel.getUserName());
        viewHolder.tvCommentTime.setText(notificationmodel.getDate());
        viewHolder.tvComment.setText(notificationmodel.getComment());

        viewHolder.llComment.setTag(i);
        viewHolder.llComment.setOnLongClickListener(onLongClickListener);
    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imgUserProfile;
        private TextView tvUserName, tvCommentTime, tvComment;
        private LinearLayout llComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            llComment = itemView.findViewById(R.id.llComment);
            imgUserProfile = itemView.findViewById(R.id.imgUserProfile);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvCommentTime = itemView.findViewById(R.id.tvCommentTime);
            tvComment = itemView.findViewById(R.id.tvComment);
        }
    }
}
