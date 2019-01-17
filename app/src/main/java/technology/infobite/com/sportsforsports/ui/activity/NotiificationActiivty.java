package technology.infobite.com.sportsforsports.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import technology.infobite.com.sportsforsports.NotificationAdapter;
import technology.infobite.com.sportsforsports.NotificationModel;
import technology.infobite.com.sportsforsports.R;

public class NotiificationActiivty extends AppCompatActivity {

    private List<NotificationModel> notificationModels = new ArrayList<>();
    RecyclerView notificatiorclv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notiification_actiivty);
        notificatiorclv = findViewById(R.id.notification_rclv);
        notificatiorclv.setHasFixedSize(true);
        notificatiorclv.setLayoutManager(new LinearLayoutManager(this));


        notificationModels.add(
                new NotificationModel(
                        R.drawable.profile_image
                        ,"Alternating Waves"
                        ,"5h"
                ));
        notificationModels.add(
                new NotificationModel(
                        R.drawable.profile_image
                        ,"Double Arm Waves"
                        ,"12h"
                ));
        notificationModels.add(
                new NotificationModel(
                        R.drawable.profile_image
                        ,"Double Arm Slam"
                        ,"15h"
                ));
        notificationModels.add(
                new NotificationModel(
                        R.drawable.profile_image
                        ,"Double Arm Slam Jump"
                        ,"5h"
                ));
        notificationModels.add(
                new NotificationModel(
                        R.drawable.profile_image
                        ,"Snakes"
                        ,"9h"

                ));
        notificationModels.add(
                new NotificationModel(
                        R.drawable.profile_image
                        ,"Claps"
                        ,"8h"
                ));
        notificationModels.add(
                new NotificationModel(
                        R.drawable.profile_image
                        ,"Outside Circles"
                        ,"3h"
                ));
        notificationModels.add(
                new NotificationModel(
                        R.drawable.profile_image
                        ,"Ultimate Warrior"
                        ,"5h"
                ));
        notificationModels.add(
                new NotificationModel(
                        R.drawable.profile_image
                        ,"Grappler HIp-to-Hip Toss"
                        ,"5h"
                ));
        notificationModels.add(
                new NotificationModel(
                        R.drawable.profile_image
                        ,"Alternating Waves + Squats"
                        ,"5h"
                )); notificationModels.add(
                new NotificationModel(
                        R.drawable.profile_image
                        ,"Double Arm Waves + Burpee"
                        ,"6h"
                ));
        notificationModels.add(
                new NotificationModel(
                        R.drawable.profile_image
                        ,"Uppercuts"
                        ,"6h"
                ));
        notificationModels.add(
                new NotificationModel(
                        R.drawable.profile_image
                        ,"Figure Eight Circles"
                        ,"9h"
                ));
        
        NotificationAdapter notificationAdapter = new NotificationAdapter(notificationModels,this);

        LinearLayoutManager lm = new LinearLayoutManager(NotiificationActiivty.this,LinearLayoutManager.VERTICAL,false);
        notificatiorclv.setLayoutManager(lm);
        notificatiorclv.setItemAnimator(new DefaultItemAnimator());
        notificatiorclv.setAdapter(notificationAdapter);


    }
}
