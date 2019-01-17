package technology.infobite.com.sportsforsports;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewCommentAdapter extends RecyclerView.Adapter<RecyclerViewCommentAdapter.ViewHolder> {

    private List<CommentModel1> commentModels1;
    Context ctx;

    public RecyclerViewCommentAdapter(List<CommentModel1> commentModels1, Context ctx) {
        this.commentModels1 = commentModels1;
        this.ctx = ctx;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {

        LayoutInflater li = LayoutInflater.from(ctx);
        View viewt = li.inflate(R.layout.rclvcomment_rclv_layout, null);
        return new ViewHolder(viewt);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        CommentModel1 commentModel1 = commentModels1.get(i);
        viewHolder.textcommentrclv1.setText(commentModel1.getComment());
        viewHolder.texttimecommentrclv1.setText(commentModel1.getTimeduartion());
    }

    @Override
    public int getItemCount() {
        return commentModels1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textcommentrclv1,texttimecommentrclv1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textcommentrclv1 = itemView.findViewById(R.id.text_comment_rclv1);
            texttimecommentrclv1 = itemView.findViewById(R.id.text_time_rclv1);
        }
    }
}

