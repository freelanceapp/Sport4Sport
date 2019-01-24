package technology.infobite.com.sportsforsports.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import technology.infobite.com.sportsforsports.NotificationModel;
import technology.infobite.com.sportsforsports.R;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

    private List<NotificationModel> gridDetailModels;
    private Context context;

    public SearchListAdapter(List<NotificationModel> gridDetailModels, Context context) {
        this.gridDetailModels = gridDetailModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater li = LayoutInflater.from(context);
        View viewt = li.inflate(R.layout.row_search_list, null);
        return new ViewHolder(viewt);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        NotificationModel gridDetailmodels = gridDetailModels.get(i);

        viewHolder.image.setImageDrawable(context.getResources().getDrawable(gridDetailmodels.getImage()));
        viewHolder.text1.setText(gridDetailmodels.getText1());
        viewHolder.text2.setText(gridDetailmodels.getText2());

        if (i == 0 || i == 1) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(12, 56, 12, 12);
            viewHolder.llItem.setLayoutParams(params);
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(12, 12, 12, 12);
            viewHolder.llItem.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return gridDetailModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView text1, text2;
        CardView llItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            llItem = itemView.findViewById(R.id.llItem);
            image = itemView.findViewById(R.id.grid_image);
            text1 = itemView.findViewById(R.id.grid_text1);
            text2 = itemView.findViewById(R.id.grid_text2);

        }
    }
}

