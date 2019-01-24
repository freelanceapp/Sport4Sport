package technology.infobite.com.sportsforsports.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import technology.infobite.com.sportsforsports.adapter.SearchListAdapter;
import technology.infobite.com.sportsforsports.NotificationModel;
import technology.infobite.com.sportsforsports.R;

public class SearchListActivity extends AppCompatActivity implements View.OnClickListener {

    private List<NotificationModel> gridDetailModels = new ArrayList<>();
    private RecyclerView gridDetailrclv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        init();
    }

    private void init() {
        findViewById(R.id.imgBack).setOnClickListener(this);

        gridDetailrclv = findViewById(R.id.grid_rclvlist);
        gridDetailrclv.setHasFixedSize(true);
        for (int i = 0; i <= 30; i++) {
            gridDetailModels.add(
                    new NotificationModel(R.drawable.profile_image, "David Bekham",
                            "This evolution of olympied data"));
        }
        SearchListAdapter gridAdapter = new SearchListAdapter(gridDetailModels, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchListActivity.this, 2);
        gridDetailrclv.setLayoutManager(gridLayoutManager);
        gridDetailrclv.setItemAnimator(new DefaultItemAnimator());
        gridDetailrclv.setAdapter(gridAdapter);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
