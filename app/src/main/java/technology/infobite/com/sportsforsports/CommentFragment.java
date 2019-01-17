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
class CommentFragment extends Fragment {
    private CommentsAdapter commentAdapter;
    private RecyclerView commentfragmenrclv;
    private List<CommentModel> commentModels = new ArrayList<>();
    private Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.comments_rclv_layout,container,false);
        commentfragmenrclv = view.findViewById(R.id.comment_rclv);

        commentAdapter = new CommentsAdapter(commentModels,context);
        LinearLayoutManager lm = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        commentfragmenrclv.setLayoutManager(lm);
        commentfragmenrclv.setItemAnimator(new DefaultItemAnimator());
        commentfragmenrclv.setAdapter(commentAdapter);
        return view;
    }
}
