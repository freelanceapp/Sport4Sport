package com.pinlinx.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.pinlinx.R;
import com.pinlinx.constant.Constant;
import com.pinlinx.modal.notification_list_modal.NotificationList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationList> notificationLists;
    private Context context;
    private View.OnClickListener onClickListener;

    public NotificationAdapter(List<NotificationList> notificationLists, Context context, View.OnClickListener onClickListener) {
        this.notificationLists = notificationLists;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater li = LayoutInflater.from(context);
        View viewt = li.inflate(R.layout.row_notification, null);
        return new ViewHolder(viewt);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String strNotification = notificationLists.get(i).getNotifactionType();
        String strFanName = notificationLists.get(i).getFanUserName();
        String strTime = notificationLists.get(i).getDate();
        if (strNotification.equalsIgnoreCase("Like")) {
            viewHolder.tvNotificationType.setText(strFanName + " " + "likes your post");
        } else if (strNotification.equalsIgnoreCase("Comment")) {
            viewHolder.tvNotificationType.setText(strFanName + " " + "commented on your post");
        } else if (strNotification.equalsIgnoreCase("Follow")) {
            viewHolder.tvNotificationType.setText(strFanName + " " + "started following you");
        }

        viewHolder.tvTime.setText(strTime);

        Glide.with(context)
                .load(Constant.PROFILE_IMAGE_BASE_URL + notificationLists.get(i).getFanAvtarImg())
                .placeholder(R.drawable.ic_profile)
                .into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return notificationLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView tvNotificationType, tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.ic_profile_person);
            tvNotificationType = itemView.findViewById(R.id.tvNotificationType);
            tvTime = itemView.findViewById(R.id.tvTime);

        }
    }
}
