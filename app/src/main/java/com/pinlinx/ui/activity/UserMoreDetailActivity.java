package com.pinlinx.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ViewFlipper;

import com.pinlinx.R;
import com.pinlinx.modal.user_data.UserData;
import com.pinlinx.utils.BaseActivity;

public class UserMoreDetailActivity extends BaseActivity implements View.OnClickListener {

    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_more_detail);
        mContext = this;

        init();
        getIntentData();
    }

    private void init() {
        viewFlipper = findViewById(R.id.viewFlipper);

        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnContinue).setOnClickListener(this);
        findViewById(R.id.btnContinueB).setOnClickListener(this);
        findViewById(R.id.imgBack).setOnClickListener(this);
    }

    private void getIntentData() {
        if (getIntent() == null)
            return;
        UserData userData = getIntent().getParcelableExtra("user_detail");

        ((EditText) findViewById(R.id.edtGender)).setText(userData.getGender());
        ((EditText) findViewById(R.id.edtName)).setText(userData.getUserName());
        ((EditText) findViewById(R.id.edtBirthday)).setText(userData.getDob());
        ((EditText) findViewById(R.id.edtCity)).setText(userData.getCity());
        ((EditText) findViewById(R.id.edtCountry)).setText(userData.getCountry());
        ((EditText) findViewById(R.id.edtNickname)).setText(userData.getNickname());
        ((EditText) findViewById(R.id.edtHeight)).setText(userData.getHeight());
        ((EditText) findViewById(R.id.edtWeight)).setText(userData.getWeight());
        ((EditText) findViewById(R.id.edtMainSport)).setText(userData.getMainSport());

        ((EditText) findViewById(R.id.edtProfession)).setText(userData.getProfession());
        ((EditText) findViewById(R.id.edtMobile)).setText(userData.getContact());
        ((EditText) findViewById(R.id.edtHealthInsurance)).setText(userData.getInsurance());

        ((EditText) findViewById(R.id.edtPosition)).setText(userData.getPosition());
        ((EditText) findViewById(R.id.edtRituals)).setText(userData.getPreGameRituals());
        ((EditText) findViewById(R.id.edtCoach)).setText(userData.getCoach());
        ((EditText) findViewById(R.id.edtClub)).setText(userData.getClub());
        ((EditText) findViewById(R.id.edtDiscipline)).setText(userData.getDiscipline());
        ((EditText) findViewById(R.id.edtOtherSport)).setText(userData.getCollege());
        ((EditText) findViewById(R.id.edtCollege)).setText(userData.getOtherSport());
        ((EditText) findViewById(R.id.edtBio)).setText(userData.getBio());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContinue:
                clickContinue();
                break;
            case R.id.imgBack:
            case R.id.btnContinueB:
                finish();
                break;
            case R.id.btnBack:
                clickBack();
                break;
        }
    }

    private void clickContinue() {
        findViewById(R.id.llCreateProfile).setBackground(
                getResources().getDrawable(R.drawable.textview_back_a));
        viewFlipper.showNext();
    }

    private void clickBack() {
        findViewById(R.id.llCreateProfile).setBackground(
                getResources().getDrawable(R.drawable.layout_back_a));
        viewFlipper.showPrevious();
    }
}
