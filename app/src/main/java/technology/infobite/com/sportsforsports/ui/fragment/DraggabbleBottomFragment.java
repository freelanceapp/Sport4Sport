package technology.infobite.com.sportsforsports.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.adapter.CommentListAdapter;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.Comment;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.DailyNewsFeedMainModal;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.UserFeed;
import technology.infobite.com.sportsforsports.modal.post_comment_modal.PostCommentResponseModal;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseFragment;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;

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
        commentListAdapter = new CommentListAdapter(commentList, mContext);
        recyclerViewCommentList.setAdapter(commentListAdapter);
        commentListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llLikePost:
                likeApi(imageFeed, ((ImageView) rootView.findViewById(R.id.imgLike)),
                        ((TextView) rootView.findViewById(R.id.tvPostLikeCount)));
                break;
            case R.id.llPostComment:
                ((CardView) rootView.findViewById(R.id.cardViewComment)).setVisibility(View.VISIBLE);
                break;
            case R.id.post_comment_send:
                postCommentApi();
                LinearLayout mainLayout = (LinearLayout) rootView.findViewById(R.id.myLinearLayout);
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
