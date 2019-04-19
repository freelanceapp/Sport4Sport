package com.pinlinx.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.pinlinx.R;
import com.pinlinx.constant.Constant;
import com.pinlinx.utils.BaseActivity;

public class TermsPolicyActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_policy);
        init();
    }

    private void init() {
        findViewById(R.id.imgBack).setOnClickListener(this);
        TextView textView = findViewById(R.id.tvDetail);
        String type = getIntent().getStringExtra("type");
        if (type.equals("terms")) {
            textView.setText(Html.fromHtml(Constant.TERMS));
        } else {
            textView.setText(Html.fromHtml(Constant.PRIVACY_POLICY));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
        }
    }
}

