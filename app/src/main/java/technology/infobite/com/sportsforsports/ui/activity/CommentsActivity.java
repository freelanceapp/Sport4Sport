package technology.infobite.com.sportsforsports.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.Comment;

public class CommentsActivity extends AppCompatActivity {

    private List<Comment> commentModels = new ArrayList<>();
    protected RecyclerView commetnrclv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        commetnrclv = findViewById(R.id.comments_rclv);
        commetnrclv.setHasFixedSize(true);
        commetnrclv.setLayoutManager(new LinearLayoutManager(this));
    }
}
