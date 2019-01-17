package technology.infobite.com.sportsforsports.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import technology.infobite.com.sportsforsports.CommentModel;
import technology.infobite.com.sportsforsports.CommentsAdapter;
import technology.infobite.com.sportsforsports.R;

public class CommentsActivity extends AppCompatActivity {

    private List<CommentModel> commentModels = new ArrayList<>();
    protected RecyclerView commetnrclv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        commetnrclv = findViewById(R.id.comments_rclv);
        commetnrclv.setHasFixedSize(true);
        commetnrclv.setLayoutManager(new LinearLayoutManager(this));


        commentModels.add(
                new CommentModel(
                        "username Hi! Awesome"
                        ,"5 hours ago"
                ));
        commentModels.add(
                new CommentModel(
                        "username FFav Player"
                        ,"12 hours ago"
                ));
        commentModels.add(
                new CommentModel(
                        "username you are one of the bestblayer"
                        ,"15 hours ago"
                ));
        commentModels.add(
                new CommentModel(
                        "username message"
                        ,"5 hours ago"
                ));
        commentModels.add(
                new CommentModel(
                        "usename message"
                        ,"9 hours ago"

                ));
        commentModels.add(
                new CommentModel(
                        "usename message"
                        ,"10 hours ago"
                ));
        commentModels.add(
                new CommentModel(
                        "usename message"
                        ,"2 hours ago"
                ));

        CommentsAdapter commentsAdapter = new CommentsAdapter(commentModels,this);

        LinearLayoutManager lm = new LinearLayoutManager(CommentsActivity.this,LinearLayoutManager.VERTICAL,false);
        commetnrclv.setLayoutManager(lm);
        commetnrclv.setItemAnimator(new DefaultItemAnimator());
        commetnrclv.setAdapter(commentsAdapter);
    }
}
