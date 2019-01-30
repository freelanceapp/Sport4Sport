package technology.infobite.com.sportsforsports.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


import technology.infobite.com.draggableview.DraggablePanel;
import technology.infobite.com.draggableview.DraggableView;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.ui.fragment.DraggabbleBottomFragment;
import technology.infobite.com.sportsforsports.ui.fragment.DraggabbleTopFragment;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private DraggablePanel draggablePanel;
    private DraggableView draggableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        init();
    }

    private void init() {
       /* draggablePanel = (DraggablePanel) findViewById(R.id.draggable_panel);
        draggablePanel.setVisibility(View.GONE);
        draggablePanel.setFragmentManager(getSupportFragmentManager());
        draggablePanel.setTopFragment(new DraggabbleTopFragment());
        draggablePanel.setBottomFragment(new DraggabbleBottomFragment());
        draggablePanel.setTopViewHeight(300);
        draggablePanel.initializeView();*/


        draggableView = (DraggableView) findViewById(R.id.draggable_view);
        draggableView.setVisibility(View.GONE);
        draggableView.setClickToMaximizeEnabled(true);
        draggableView.setClickToMinimizeEnabled(true);
        draggableView.setTouchEnabled(true);

        ((ImageView) findViewById(R.id.imgA)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgB)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgC)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgD)).setOnClickListener(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                draggableView.closeToLeft();
                //draggablePanel.closeToLeft();
            }
        }, 100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgA:
            case R.id.imgB:
            case R.id.imgC:
            case R.id.imgD:
                /*draggablePanel.setFragmentManager(getSupportFragmentManager());
                draggablePanel.setTopFragment(new DraggabbleTopFragment());
                draggablePanel.setBottomFragment(new DraggabbleBottomFragment());
                draggablePanel.setTopViewHeight(300);
                draggablePanel.initializeView();*/
                /*draggablePanel.setVisibility(View.VISIBLE);
                draggablePanel.maximize();*/
                draggableView.setVisibility(View.VISIBLE);
                draggableView.maximize();
                break;
        }
    }
}
