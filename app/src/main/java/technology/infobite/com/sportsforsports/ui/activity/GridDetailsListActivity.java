package technology.infobite.com.sportsforsports.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import technology.infobite.com.sportsforsports.GridAdapter;
import technology.infobite.com.sportsforsports.NotificationModel;
import technology.infobite.com.sportsforsports.R;

public class GridDetailsListActivity extends AppCompatActivity {

    private List<NotificationModel> gridDetailModels = new ArrayList<>();

    RecyclerView gridDetailrclv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_details_list);

        gridDetailrclv = findViewById(R.id.grid_rclvlist);
        gridDetailrclv.setHasFixedSize(true);

        for (int i =0; i<=30; i++){

            gridDetailModels.add(
                    new NotificationModel(
                            R.drawable.profile_image
                            ,"David Bekham"
                            ,"This evolution of olympied data"
                    ));
        }

        GridAdapter gridAdapter = new GridAdapter(gridDetailModels,this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(GridDetailsListActivity.this,2);
        gridDetailrclv.setLayoutManager(gridLayoutManager);
        gridDetailrclv.setItemAnimator(new DefaultItemAnimator());
        gridDetailrclv.setAdapter(gridAdapter);

    }

}
