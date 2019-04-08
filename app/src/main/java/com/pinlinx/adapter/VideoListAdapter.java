package com.pinlinx.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.pinlinx.R;
import com.pinlinx.modal.VideoListModel;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    private ArrayList<VideoListModel> al_video;
    private Context context;
    private Activity activity;
    private View.OnClickListener onClickListener;

    public VideoListAdapter(Context context, ArrayList<VideoListModel> al_video, Activity activity, View.OnClickListener onClickListener) {

        this.al_video = al_video;
        this.context = context;
        this.activity = activity;
        this.onClickListener = onClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rl_select;
        private ImageView iv_image;

        public ViewHolder(View v) {
            super(v);
            rl_select = v.findViewById(R.id.rl_select);
            iv_image = v.findViewById(R.id.iv_image);
        }
    }

    @Override
    public VideoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video_list, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position) {
        Glide.with(context).load("file://" + al_video.get(position).getStr_thumb())
                .into(Vholder.iv_image);

        Vholder.rl_select.setTag(position);
        Vholder.rl_select.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return al_video.size();
    }
}

