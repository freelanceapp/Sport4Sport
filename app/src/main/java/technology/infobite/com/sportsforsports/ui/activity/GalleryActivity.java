package technology.infobite.com.sportsforsports.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import technology.infobite.com.sportsforsports.adapter.MyPhotoVideoAdapter;
import technology.infobite.com.sportsforsports.GalleryModel;
import technology.infobite.com.sportsforsports.R;

public class GalleryActivity extends AppCompatActivity {

    private List<GalleryModel> galleryModels = new ArrayList<>();
    private RecyclerView galleryrclv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        galleryrclv = findViewById(R.id.gallery_rclv);

        for (int i = 0; i <= 30; i++) {
            galleryModels.add(new GalleryModel(R.drawable.player_image));
        }

        MyPhotoVideoAdapter galleryAdapter = new MyPhotoVideoAdapter(galleryModels, this,"");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(GalleryActivity.this, 4);
        galleryrclv.setLayoutManager(gridLayoutManager);
        galleryrclv.setItemAnimator(new DefaultItemAnimator());
        galleryrclv.setAdapter(galleryAdapter);
    }
}
