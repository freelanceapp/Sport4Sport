package technology.infobite.com.sportsforsports.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.adapter.LeagueListAdapter;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.league.LeagueList;
import technology.infobite.com.sportsforsports.modal.league.LeagueMainModal;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseActivity;

public class LeagueFollowingActivity extends BaseActivity implements View.OnClickListener, LeagueListAdapter.SearchAdapterListener {

    private List<LeagueList> leagueLists = new ArrayList<>();
    private RecyclerView gridDetailrclv;
    private LeagueListAdapter searchListAdapter;
    private EditText edtSearch;
    private String strUserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        init();
    }

    private void init() {
        strUserId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        edtSearch = findViewById(R.id.edtSearch);
        findViewById(R.id.imgBack).setOnClickListener(this);
        findViewById(R.id.cardViewSearch).setVisibility(View.GONE);

        gridDetailrclv = findViewById(R.id.grid_rclvlist);
        gridDetailrclv.setHasFixedSize(true);

        searchListAdapter = new LeagueListAdapter(leagueLists, mContext, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(LeagueFollowingActivity.this, 2);
        gridDetailrclv.setLayoutManager(gridLayoutManager);
        gridDetailrclv.setItemAnimator(new DefaultItemAnimator());
        gridDetailrclv.setAdapter(searchListAdapter);

        leagueListApi();
    }

    private void leagueListApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getLeagueList(new Dialog(mContext), retrofitApiClient.leagueFollowingList(strUserId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    LeagueMainModal leagueMainModal = (LeagueMainModal) result.body();
                    leagueLists.clear();
                    if (leagueMainModal == null)
                        return;
                    if (!leagueMainModal.getError()) {
                        leagueLists.addAll(leagueMainModal.getLeagueList());
                        findViewById(R.id.tvEmpty).setVisibility(View.GONE);
                    } else {
                        findViewById(R.id.tvEmpty).setVisibility(View.VISIBLE);
                        Alerts.show(mContext, leagueMainModal.getMessage());
                    }
                    searchListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

    @Override
    public void onClick(View v) {
        finish();
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
        }
    }

    private void leagueFollowApi(String strStatus, String strLeagueId) {
        String strFollow;
        if (strStatus.equalsIgnoreCase("Unfollow")) {
            strFollow = "1";
        } else {
            strFollow = "0";
        }
        String strUserId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        if (cd.isNetworkAvailable()) {
            RetrofitService.getContentData(new Dialog(mContext), retrofitApiClient.leagueFollow(
                    strUserId, strLeagueId, strFollow), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        if (!jsonObject.getBoolean("error")) {
                            Alerts.show(mContext, "League unfollow");
                        } else {
                            Alerts.show(mContext, jsonObject + "");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    leagueListApi();
                    searchListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

    @Override
    public void onSearchSelected(LeagueList contact) {
        leagueFollowApi(contact.getStatus(), contact.getLeagueId());
    }
}
