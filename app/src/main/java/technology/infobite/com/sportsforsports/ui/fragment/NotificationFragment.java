package technology.infobite.com.sportsforsports.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import technology.infobite.com.sportsforsports.NotificationModel;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.adapter.NotificationAdapter;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.utils.BaseFragment;
import technology.infobite.com.sportsforsports.utils.ConnectionDetector;

public class NotificationFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private List<NotificationModel> notificationModels = new ArrayList<>();
    private RecyclerView notificatiorclv;

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
        notificatiorclv = rootView.findViewById(R.id.notification_rclv);
        notificatiorclv.setHasFixedSize(true);
        notificatiorclv.setLayoutManager(new LinearLayoutManager(mContext));

        notificationModels.add(new NotificationModel(R.drawable.profile_image, "Alternating Waves", "5h"));
        notificationModels.add(new NotificationModel(R.drawable.profile_image, "Double Arm Waves", "12h"));
        notificationModels.add(new NotificationModel(R.drawable.profile_image, "Double Arm Slam", "15h"));
        notificationModels.add(new NotificationModel(R.drawable.profile_image, "Double Arm Slam Jump", "5h"));
        notificationModels.add(new NotificationModel(R.drawable.profile_image, "Snakes", "9h"));
        notificationModels.add(new NotificationModel(R.drawable.profile_image, "Claps", "8h"));
        notificationModels.add(new NotificationModel(R.drawable.profile_image, "Outside Circles", "3h"));
        notificationModels.add(new NotificationModel(R.drawable.profile_image, "Ultimate Warrior", "5h"));
        notificationModels.add(new NotificationModel(R.drawable.profile_image, "Grappler HIp-to-Hip Toss", "5h"));
        notificationModels.add(new NotificationModel(R.drawable.profile_image, "Alternating Waves + Squats", "5h"));
        notificationModels.add(new NotificationModel(R.drawable.profile_image, "Double Arm Waves + Burpee", "6h"));
        notificationModels.add(new NotificationModel(R.drawable.profile_image, "Uppercuts", "6h"));
        notificationModels.add(new NotificationModel(R.drawable.profile_image, "Figure Eight Circles", "9h"));

        NotificationAdapter notificationAdapter = new NotificationAdapter(notificationModels, mContext);
        LinearLayoutManager lm = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        notificatiorclv.setLayoutManager(lm);
        notificatiorclv.setItemAnimator(new DefaultItemAnimator());
        notificatiorclv.setAdapter(notificationAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}
