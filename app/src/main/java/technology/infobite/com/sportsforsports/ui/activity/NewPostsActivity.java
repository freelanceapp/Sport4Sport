package technology.infobite.com.sportsforsports.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Util;

import java.util.ArrayList;
import java.util.List;

import technology.infobite.com.sportsforsports.CommentModel1;
import technology.infobite.com.sportsforsports.NewPostAdapter;
import technology.infobite.com.sportsforsports.NewPostModel;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.utils.BaseActivity;

public class NewPostsActivity extends BaseActivity implements OnBMClickListener {

    private List<CommentModel1> commentModels1 = new ArrayList();
    private List<NewPostModel> newPostModels = new ArrayList<>();
    private RecyclerView newpostrclv;
    private BoomMenuButton bmb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_posts);

        init();
    }

    private void init() {
        newpostrclv = findViewById(R.id.new_post_rclv);
        newpostrclv.setHasFixedSize(true);
        newpostrclv.setLayoutManager(new LinearLayoutManager(this));

        newPostModels.add(new NewPostModel(R.drawable.player_image, "David Beckham", R.drawable.player_image
                , "2.206 likes", "5000 comments", "2 HOURS AGO",
                getResources().getString(R.string.demo_text), "View all 24 comment"));

        for (int j = 0; j <= 35; j++) {
            if (commentModels1.size() <= 9) {
                commentModels1.add(new CommentModel1("Hii your loocking good",
                        "10 hours ago"));
            }
        }

        NewPostAdapter newPostAdapter = new NewPostAdapter(newPostModels, this, commentModels1);
        LinearLayoutManager lm = new LinearLayoutManager(NewPostsActivity.this, LinearLayoutManager.VERTICAL, false);
        newpostrclv.setLayoutManager(lm);
        newpostrclv.setItemAnimator(new DefaultItemAnimator());
        newpostrclv.setAdapter(newPostAdapter);

        ((FloatingActionButton) findViewById(R.id.fabNewPost)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyPageActivity.class));
            }
        });

        initBoomMenu();
    }

    private void initBoomMenu() {
        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.Ham);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder();
            Rect imageRect = new Rect(Util.dp2px(10), Util.dp2px(10), Util.dp2px(44), Util.dp2px(44));
            if (i == 0) {
                builder.normalImageRes(R.drawable.ic_timeline);
                builder.normalTextRes(R.string.timeline);
                builder.pieceColor(Color.WHITE);
                builder.highlightedColorRes(R.color.white);
                builder.normalColorRes(R.color.pink);
            }
            if (i == 1) {
                builder.normalImageRes(R.drawable.ic_person);
                builder.normalTextRes(R.string.profile);
                builder.pieceColor(Color.WHITE);
                builder.highlightedColorRes(R.color.white);
                builder.normalColorRes(R.color.pink);
            }
            if (i == 2) {
                builder.normalImageRes(R.drawable.ic_notification);
                builder.normalTextRes(R.string.notification);
                builder.pieceColor(Color.WHITE);
                builder.highlightedColorRes(R.color.white);
                builder.normalColorRes(R.color.pink);
            }
            if (i == 3) {
                builder.normalImageRes(R.drawable.ic_setting);
                builder.normalTextRes(R.string.settings);
                builder.pieceColor(Color.WHITE);
                builder.highlightedColorRes(R.color.white);
                builder.normalColorRes(R.color.pink);
            }
            builder.imageRect(imageRect);
            bmb.addBuilder(builder);
            builder.listener(this);
        }
    }

    @Override
    public void onBoomButtonClick(int index) {
        switch (index) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
}
