package com.pinlinx.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pinlinx.R;
import com.pinlinx.adapter.CommentListAdapter;
import com.pinlinx.constant.Constant;
import com.pinlinx.modal.daily_news_feed.Comment;
import com.pinlinx.modal.daily_news_feed.DailyNewsFeedMainModal;
import com.pinlinx.modal.daily_news_feed.UserFeed;
import com.pinlinx.modal.post_comment_modal.PostCommentResponseModal;
import com.pinlinx.retrofit_provider.RetrofitService;
import com.pinlinx.retrofit_provider.WebResponse;
import com.pinlinx.utils.Alerts;
import com.pinlinx.utils.AppPreference;
import com.pinlinx.utils.BaseFragment;
import com.pinlinx.utils.ConnectionDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class DraggabbleBottomFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private UserFeed imageFeed;
    private TextView tvPostLikeCount, tvCommentCount, tvPostDescription;
    private RecyclerView recyclerViewCommentList;
    private CommentListAdapter commentListAdapter;

    private List<Comment> commentList = new ArrayList<>();
    private String strId = "", strFrom = "", strPostId = "";
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_draggable_bottom, container, false);
        mContext = getActivity();
        retrofitApiClient = RetrofitService.getRetrofit();
        cd = new ConnectionDetector(mContext);
        init();
        return rootView;
    }

    private void init() {
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        strId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        recyclerViewCommentList = rootView.findViewById(R.id.recyclerViewCommentList);
        recyclerViewCommentList.setHasFixedSize(true);
        recyclerViewCommentList.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewCommentList.setItemAnimator(new DefaultItemAnimator());

        tvPostLikeCount = rootView.findViewById(R.id.tvPostLikeCount);
        tvCommentCount = rootView.findViewById(R.id.tvCommentCount);
        tvPostDescription = rootView.findViewById(R.id.tvPostDescription);
        rootView.findViewById(R.id.post_comment_send).setOnClickListener(this);
        rootView.findViewById(R.id.llLikePost).setOnClickListener(this);
        rootView.findViewById(R.id.llLikePost).setOnClickListener(this);
        rootView.findViewById(R.id.llPostComment).setOnClickListener(this);
    }

    public void showImage(String strPostId, String strFrom) {
        this.strPostId = strPostId;
        this.strFrom = strFrom;

        rootView.findViewById(R.id.rlCommentList).setVisibility(View.GONE);
        commentList.clear();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postDetailApi();
            }
        });
        postDetailApi();
    }

    private void postDetailApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.showPostTimeLine(new Dialog(mContext), retrofitApiClient.postDetail(strId, strPostId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    DailyNewsFeedMainModal dailyNewsFeedMainModal = (DailyNewsFeedMainModal) result.body();
                    if (dailyNewsFeedMainModal != null)
                        if (dailyNewsFeedMainModal.getFeed() != null)
                            imageFeed = dailyNewsFeedMainModal.getFeed().get(0);

                    rootView.findViewById(R.id.rlCommentList).setVisibility(View.VISIBLE);
                    setDataInModal();
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onResponseFailed(String error) {
                    swipeRefreshLayout.setRefreshing(false);
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

    private void setDataInModal() {
        commentList.clear();
        commentList.addAll(imageFeed.getComment());

        if (imageFeed.getIsLike().equals("unlike")) {
            ((ImageView) rootView.findViewById(R.id.imgLike)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_icon));
        } else {
            ((ImageView) rootView.findViewById(R.id.imgLike)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_fill));
        }

        setData();
        setCommentList();
    }

    private void setData() {
        tvPostDescription.setText(imageFeed.getAthleteStatus());

        if (imageFeed.getLikes() == null || imageFeed.getLikes().isEmpty()) {
            tvPostLikeCount.setText("0 like");
        } else {
            tvPostLikeCount.setText(imageFeed.getLikes() + " like");
        }
        if (imageFeed.getComment() == null || imageFeed.getComment().isEmpty()) {
            tvCommentCount.setText("0 comment");
        } else {
            tvCommentCount.setText(imageFeed.getComment().size() + " comment");
        }
    }

    private void setCommentList() {
        commentListAdapter = new CommentListAdapter(commentList, mContext, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = Integer.parseInt(v.getTag().toString());
                if (strId.equals(commentList.get(pos).getUserId())) {
                    deleteCommentDialog(commentList.get(pos).getCommentId());
                }
                return false;
            }
        });
        recyclerViewCommentList.setAdapter(commentListAdapter);
        commentListAdapter.notifyDataSetChanged();
    }

    private void deleteCommentDialog(final String commentId) {
        final Dialog dialogCustomerInfo = new Dialog(mContext);
        dialogCustomerInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCustomerInfo.setContentView(R.layout.dialog_delete_comment);

        dialogCustomerInfo.setCanceledOnTouchOutside(true);
        dialogCustomerInfo.setCancelable(true);
        if (dialogCustomerInfo.getWindow() != null)
            dialogCustomerInfo.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialogCustomerInfo.findViewById(R.id.tvOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCommentApi(commentId, dialogCustomerInfo);
            }
        });

        dialogCustomerInfo.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCustomerInfo.dismiss();
            }
        });

       /* Window window = dialogCustomerInfo.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);*/
        dialogCustomerInfo.show();
    }

    private void deleteCommentApi(String commentId, final Dialog dialogCustomerInfo) {
        String strPostId = imageFeed.getFeedId();

        if (!commentId.isEmpty()) {
            RetrofitService.postCommentResponse(retrofitApiClient.deletePostComment(strPostId, commentId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    PostCommentResponseModal commentResponseModal = (PostCommentResponseModal) result.body();
                    commentList.clear();
                    if (strFrom.equals("user")) {
                        AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, false);
                    } else {
                        AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, true);
                    }
                    if (commentResponseModal == null)
                        return;
                    commentList.addAll(commentResponseModal.getComment());
                    commentListAdapter.notifyDataSetChanged();
                    dialogCustomerInfo.dismiss();
                }

                @Override
                public void onResponseFailed(String error) {
                    AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, false);
                    Alerts.show(mContext, error);
                }
            });
        } else {
            Alerts.show(mContext, "Enter some comments!!!");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llLikePost:
                likeApi(imageFeed, ((ImageView) rootView.findViewById(R.id.imgLike)),
                        ((TextView) rootView.findViewById(R.id.tvPostLikeCount)));
                break;
            case R.id.llPostComment:
                rootView.findViewById(R.id.cardViewComment).setVisibility(View.VISIBLE);
                break;
            case R.id.post_comment_send:
                postCommentApi();
                LinearLayout mainLayout = rootView.findViewById(R.id.myLinearLayout);
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
                break;
        }
    }

    /***********************************************************************/
    /*
     * Like/Unlike function
     * */
    private void likeApi(final UserFeed feed, final ImageView imgLike, final TextView textView) {

        if (cd.isNetworkAvailable()) {
            RetrofitService.getLikeResponse(retrofitApiClient.postLike(feed.getFeedId(), strId, "1"), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        if (!jsonObject.getBoolean("error")) {
                            if (feed.getIsLike().equals("unlike")) {
                                imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_fill));
                            } else {
                                imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_icon));
                            }
                            postDetailApi();
                            textView.setText(jsonObject.getString("total_fan") + " like");
                        } else {
                            Alerts.show(mContext, jsonObject.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

    /***********************************************************************/
    /*
     * POst comment api
     * */
    private void postCommentApi() {
        String strUserId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        String strPostId = imageFeed.getFeedId();
        String strComments = ((EditText) rootView.findViewById(R.id.edit_post_comment)).getText().toString();

        if (!strComments.isEmpty()) {
            RetrofitService.postCommentResponse(retrofitApiClient.newPostComment(strPostId, strUserId, strComments), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    PostCommentResponseModal commentResponseModal = (PostCommentResponseModal) result.body();
                    commentList.clear();
                    /*if (strFrom.equals("user")) {
                        AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, false);
                    } else {
                        AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, true);
                    }*/
                    //timelineApi();
                    if (commentResponseModal == null)
                        return;
                    commentList.addAll(commentResponseModal.getComment());
                    commentListAdapter.notifyDataSetChanged();
                    ((EditText) rootView.findViewById(R.id.edit_post_comment)).setText("");
                }

                @Override
                public void onResponseFailed(String error) {
                    AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, false);
                    Alerts.show(mContext, error);
                }
            });
        } else {
            Alerts.show(mContext, "Enter some comments!!!");
        }
    }

}
