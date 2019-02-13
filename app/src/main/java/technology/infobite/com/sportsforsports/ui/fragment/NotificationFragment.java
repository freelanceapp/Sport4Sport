package technology.infobite.com.sportsforsports.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.adapter.NotificationAdapter;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.notification_list_modal.NotificationList;
import technology.infobite.com.sportsforsports.modal.notification_list_modal.NotificationMainModal;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.AppPreference;
import technology.infobite.com.sportsforsports.utils.BaseFragment;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;

public class NotificationFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private List<NotificationList> notificationLists = new ArrayList<>();
    private RecyclerView notificatiorclv;
    private String strUserId = "";
    private NotificationAdapter notificationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        mContext = getActivity();
        retrofitApiClient = RetrofitService.getRetrofit();
        cd = new ConnectionDetector(mContext);
        init();
        return rootView;
    }

    private void init() {
        strUserId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        notificatiorclv = rootView.findViewById(R.id.notification_rclv);
        notificatiorclv.setHasFixedSize(true);
        notificatiorclv.setLayoutManager(new LinearLayoutManager(mContext));

        notificationAdapter = new NotificationAdapter(notificationLists, mContext, this);
        LinearLayoutManager lm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        notificatiorclv.setLayoutManager(lm);
        notificatiorclv.setItemAnimator(new DefaultItemAnimator());
        notificatiorclv.setAdapter(notificationAdapter);

        notificationListApi();
    }

    private void notificationListApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getNotificationList(new Dialog(mContext), retrofitApiClient.notificationList(strUserId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    NotificationMainModal notificationMainModal = (NotificationMainModal) result.body();
                    notificationLists.clear();
                    if (notificationMainModal.getNotifaction() != null) {
                        if (notificationMainModal.getNotifaction().size() > 0) {
                            notificationLists.addAll(notificationMainModal.getNotifaction());
                        }
                    }

                    notificationAdapter.notifyDataSetChanged();
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

    }
}
