package technology.infobite.com.sportsforsports;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
class VideoFragment extends Fragment {
    private GalleryAdapter galleryAdapter;
    private RecyclerView galleryfragmenrclv;
    private List<GalleryModel> galleryModels = new ArrayList<>();
    private Context context;


/*
  @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery_layout, container, false);
        galleryfragmenrclv = view.findViewById(R.id.fragment_gallery_rclv);

        galleryAdapter = new GalleryAdapter(galleryModels, context);
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        galleryfragmenrclv.setLayoutManager(lm);
        galleryfragmenrclv.setItemAnimator(new DefaultItemAnimator());
        galleryfragmenrclv.setAdapter(galleryAdapter);
        return view;


    }
          */
}
