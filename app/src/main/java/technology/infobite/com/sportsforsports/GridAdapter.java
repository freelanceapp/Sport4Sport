package technology.infobite.com.sportsforsports;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridAdapter  extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private List<NotificationModel> gridDetailModels;
    Context context;

    public GridAdapter(List<NotificationModel> gridDetailModels, Context context) {
        this.gridDetailModels = gridDetailModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater li = LayoutInflater.from(context);
        View viewt = li.inflate(R.layout.grid_rclv_layout,null);
        return new ViewHolder(viewt);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        NotificationModel gridDetailmodels= gridDetailModels.get(i);

        viewHolder.image.setImageDrawable(context.getResources().getDrawable(gridDetailmodels.getImage()));
        viewHolder.text1.setText(gridDetailmodels.getText1());
        viewHolder.text2.setText(gridDetailmodels.getText2());
    }

    @Override
    public int getItemCount() {
        return gridDetailModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView text1,text2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.grid_image);
            text1 = itemView.findViewById(R.id.grid_text1);
            text2 = itemView.findViewById(R.id.grid_text2);

        }
    }
}

