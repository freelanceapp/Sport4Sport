package technology.infobite.com.sportsforsports.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.league.LeagueList;

public class LeagueListAdapter extends RecyclerView.Adapter<LeagueListAdapter.ViewHolder> implements Filterable {

    private List<LeagueList> filteredAllUserLists;
    private List<LeagueList> allUserLists;
    private Context context;
    private SearchAdapterListener searchAdapterListener;

    public LeagueListAdapter(List<LeagueList> allUserLists, Context context, SearchAdapterListener searchAdapterListener) {
        this.allUserLists = allUserLists;
        this.filteredAllUserLists = allUserLists;
        this.context = context;
        this.searchAdapterListener = searchAdapterListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(context);
        View viewt = li.inflate(R.layout.row_league_list, null);
        return new ViewHolder(viewt);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        LeagueList gridDetailmodels = filteredAllUserLists.get(i);

        Glide.with(context)
                .load(Constant.LEAGUE_IMAGE_BASE_URL + gridDetailmodels.getImage())
                .into(viewHolder.imgUser);

        viewHolder.tvUserName.setText(gridDetailmodels.getLeagueName());
        viewHolder.tvCategory.setText(gridDetailmodels.getCategory());

        if (gridDetailmodels.getStatus().equalsIgnoreCase("Unfollow")) {
            viewHolder.btnFollow.setText("Follow");
            viewHolder.btnFollow.setTextColor(context.getResources().getColor(R.color.white));
            viewHolder.btnFollow.setBackground(context.getResources().getDrawable(R.drawable.gridbutton_background));
        } else {
            viewHolder.btnFollow.setText("Following");
            viewHolder.btnFollow.setTextColor(context.getResources().getColor(R.color.black));
            viewHolder.btnFollow.setBackground(context.getResources().getDrawable(R.drawable.btn_back_b));
        }
        viewHolder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAdapterListener.onSearchSelected(filteredAllUserLists.get(i));
            }
        });

        if (i == 0 || i == 1) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(24, 56, 24, 24);
            viewHolder.llItem.setLayoutParams(params);
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(24, 12, 24, 12);
            viewHolder.llItem.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return filteredAllUserLists.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredAllUserLists = allUserLists;
                } else {
                    List<LeagueList> filteredList = new ArrayList<>();
                    for (LeagueList row : allUserLists) {
                        if (row.getLeagueName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filteredAllUserLists = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredAllUserLists;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredAllUserLists = (ArrayList<LeagueList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUser;
        private TextView tvUserName, tvCategory;
        private CardView llItem;
        private Button btnFollow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btnFollow = itemView.findViewById(R.id.btnFollow);
            llItem = itemView.findViewById(R.id.llItem);
            imgUser = itemView.findViewById(R.id.imgUser);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvCategory = itemView.findViewById(R.id.tvCategory);
        }
    }

    public interface SearchAdapterListener {
        void onSearchSelected(LeagueList contact);
    }
}

