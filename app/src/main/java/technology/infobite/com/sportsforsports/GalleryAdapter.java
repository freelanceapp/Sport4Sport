package technology.infobite.com.sportsforsports;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private List<GalleryModel> galleryModels;
    Context ctx;

    public GalleryAdapter(List<GalleryModel> galleryModels, Context ctx) {
        this.galleryModels = galleryModels;
        this.ctx = ctx;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {

        LayoutInflater li = LayoutInflater.from(ctx);
        View viewt = li.inflate(R.layout.fragemt_gallery_rclv, null);
        return new ViewHolder(viewt);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        GalleryModel gallerymodel = galleryModels.get(i);

        viewHolder.imagetools.setImageDrawable(ctx.getResources().getDrawable(gallerymodel.getImage()));
    }

    @Override
    public int getItemCount() {
        return galleryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout exerciseactivity1;
        ImageView imagetools;
        TextView texttools;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagetools = itemView.findViewById(R.id.gallery_images);


        }
    }
}
