package com.pinlinx.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import com.pinlinx.GalleryModel;
import com.pinlinx.R;

public class MyPhotoVideoAdapter extends RecyclerView.Adapter<MyPhotoVideoAdapter.ViewHolder> {

    private List<GalleryModel> galleryModels;
    private Context ctx;
    private String strType = "";

    public MyPhotoVideoAdapter(List<GalleryModel> galleryModels, Context ctx, String strType) {
        this.galleryModels = galleryModels;
        this.ctx = ctx;
        this.strType = strType;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        LayoutInflater li = LayoutInflater.from(ctx);
        View viewt = li.inflate(R.layout.row_my_photo_video, null);
        return new ViewHolder(viewt);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        GalleryModel gallerymodel = galleryModels.get(i);
        viewHolder.imgPhotoVideo.setImageDrawable(ctx.getResources().getDrawable(gallerymodel.getImage()));

        if (!strType.isEmpty()) {
            viewHolder.imgPlayVideo.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgPlayVideo.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return galleryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhotoVideo, imgPlayVideo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhotoVideo = itemView.findViewById(R.id.imgPhotoVideo);
            imgPlayVideo = itemView.findViewById(R.id.imgPlayVideo);
        }
    }
}
