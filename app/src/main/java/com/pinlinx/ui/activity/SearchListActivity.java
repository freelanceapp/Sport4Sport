package com.pinlinx.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import com.pinlinx.R;
import com.pinlinx.adapter.SearchListAdapter;
import com.pinlinx.constant.Constant;
import com.pinlinx.modal.all_user_list_modal.AllUserList;
import com.pinlinx.modal.all_user_list_modal.AllUserMainModal;
import com.pinlinx.retrofit_provider.RetrofitService;
import com.pinlinx.retrofit_provider.WebResponse;
import com.pinlinx.utils.Alerts;
import com.pinlinx.utils.AppPreference;
import com.pinlinx.utils.BaseActivity;

public class SearchListActivity extends BaseActivity implements View.OnClickListener, SearchListAdapter.SearchAdapterListener {

    private List<AllUserList> allUserLists = new ArrayList<>();
    private RecyclerView gridDetailrclv;
    private SearchListAdapter searchListAdapter;
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

        gridDetailrclv = findViewById(R.id.grid_rclvlist);
        gridDetailrclv.setHasFixedSize(true);

        searchListAdapter = new SearchListAdapter(allUserLists, mContext, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchListActivity.this, 2);
        gridDetailrclv.setLayoutManager(gridLayoutManager);
        gridDetailrclv.setItemAnimator(new DefaultItemAnimator());
        gridDetailrclv.setAdapter(searchListAdapter);

        allUserListApi();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchListAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void allUserListApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getAllUserData(new Dialog(mContext), retrofitApiClient.getAllUserList(strUserId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    AllUserMainModal allUserMainModal = (AllUserMainModal) result.body();
                    allUserLists.clear();
                    if (allUserMainModal == null)
                        return;
                    if (!allUserMainModal.getError()) {
                        allUserLists.addAll(allUserMainModal.getAllUserList());
                    } else {
                        Alerts.show(mContext, allUserMainModal.getMessage());
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
    }

    @Override
    public void onSearchSelected(AllUserList contact) {
        //Intent postUserId = new Intent(mContext, ProfileActivity.class);
        Intent postUserId = new Intent(mContext, UserProfileActivity.class);
        postUserId.putExtra("fan_id", contact.getUserId());
        startActivity(postUserId);
    }
}
