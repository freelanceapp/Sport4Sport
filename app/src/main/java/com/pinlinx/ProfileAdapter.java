package com.pinlinx;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private List<GalleryModel> galleryModels1;
    Context ctx;

    public ProfileAdapter(List<GalleryModel> galleryModels1, Context ctx) {
        this.galleryModels1 = galleryModels1;
        this.ctx = ctx;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {

        LayoutInflater li = LayoutInflater.from(ctx);
        View viewt = li.inflate(R.layout.profile_rclv_layout, null);
        return new ViewHolder(viewt);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        GalleryModel gallerymodel1 = galleryModels1.get(i);

        viewHolder.imagetools.setImageDrawable(ctx.getResources().getDrawable(gallerymodel1.getImage()));
    }

    @Override
    public int getItemCount() {
        return galleryModels1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagetools;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagetools = itemView.findViewById(R.id.profile_image);


        }
    }
}



