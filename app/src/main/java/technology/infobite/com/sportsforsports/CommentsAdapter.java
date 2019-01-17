package technology.infobite.com.sportsforsports;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CommentsAdapter  extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private List<CommentModel> commentModels;
    Context context;

    public CommentsAdapter(List<CommentModel> commentModels, Context context) {
        this.commentModels = commentModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater li = LayoutInflater.from(context);
        View viewt = li.inflate(R.layout.fragment_comments_rclv,null);
        return new ViewHolder(viewt);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        CommentModel commentmodel= commentModels.get(i);

        viewHolder.text1.setText(commentmodel.getTex1());
        viewHolder.text2.setText(commentmodel.getTex2());
    }

    @Override
    public int getItemCount() {
        return commentModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView text1,text2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text1 = itemView.findViewById(R.id.comment);
            text2 = itemView.findViewById(R.id.comment_time);

        }
    }
}
