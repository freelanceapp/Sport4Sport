package technology.infobite.com.sportsforsports.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.modal.VideoListModel;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    ArrayList<VideoListModel> al_video;
    Context context;
    Activity activity;

    public VideoListAdapter(Context context, ArrayList<VideoListModel> al_video, Activity activity) {

        this.al_video = al_video;
        this.context = context;
        this.activity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_image;

        public ViewHolder(View v) {
            super(v);
            iv_image = (ImageView) v.findViewById(R.id.iv_image);
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
                //.skipMemoryCache(false)
                .into(Vholder.iv_image);

        /*Vholder.rl_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_gallery = new Intent(context, VideoGalleryActivity.class);
                intent_gallery.putExtra("video", al_video.get(position).getStr_path());
                activity.startActivity(intent_gallery);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return al_video.size();
    }
}

