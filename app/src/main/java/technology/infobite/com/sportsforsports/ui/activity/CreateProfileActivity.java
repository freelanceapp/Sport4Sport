package technology.infobite.com.sportsforsports.ui.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Response;
import technology.infobite.com.sportsforsports.R;
import technology.infobite.com.sportsforsports.retrofit_provider.RetrofitService;
import technology.infobite.com.sportsforsports.retrofit_provider.WebResponse;
import technology.infobite.com.sportsforsports.utils.Alerts;
import technology.infobite.com.sportsforsports.utils.BaseActivity;

public class CreateProfileActivity extends BaseActivity implements View.OnClickListener {

    private int mYear = 0, mMonth = 0, mDay = 0;
    private String strDob = "";
    private ViewFlipper viewFlipper;
    private EditText edtBirthday;
    private CheckBox checkBoxAthlete;
    private String strIsAthlete = "0";

    private String strUserName = "";
    private String strUserId = "";

    private Spinner spinnerCountryList;
    private String[] countryList = {"Select country", "India", "USA", "South Africa", "Russia"};
    private String strCountryName = "";

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        init();
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent() == null)
            return;
        strUserId = getIntent().getStringExtra("user_id");
        strUserName = getIntent().getStringExtra("name");
        ((EditText) findViewById(R.id.edtName)).setText(strUserName);
    }

    private void init() {
        spinnerCountryList = findViewById(R.id.spinnerCountryList);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countryList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerCountryList.setAdapter(adapter);
        spinnerCountryList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strCountryName = parent.getItemAtPosition(position).toString();
                if (strCountryName.equals("Select country")) {
                    ((TextView) findViewById(R.id.txtCountryName)).setText("");
                } else {
                    ((TextView) findViewById(R.id.txtCountryName)).setText(strCountryName);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewFlipper = findViewById(R.id.viewFlipper);
        edtBirthday = findViewById(R.id.edtBirthday);
        checkBoxAthlete = findViewById(R.id.checkBoxAthlete);
        checkBoxAthlete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strIsAthlete = "1";
                } else {
                    strIsAthlete = "0";
                }
            }
        });

        edtBirthday.setOnClickListener(this);
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnNoThanks).setOnClickListener(this);
        findViewById(R.id.btnContinue).setOnClickListener(this);
        findViewById(R.id.btnContinueB).setOnClickListener(this);
    }

    private void dateDialogue() {
        Calendar calendar = Calendar.getInstance();
        if (mYear == 0 || mMonth == 0 || mDay == 0) {
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog dialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                strDob = dayOfMonth + "-" + (month + 1) + "-" + year;
                edtBirthday.setText(strDob);
            }
        }, mYear, mMonth, mDay);
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtBirthday:
                dateDialogue();
                break;
            case R.id.btnNoThanks:
                clickNoThanks();
                break;
            case R.id.btnContinue:
                clickContinue();
                break;
            case R.id.btnContinueB:
                clickContinueB();
                break;
            case R.id.btnBack:
                clickBack();
                break;
        }
    }

    private void clickNoThanks() {
        String strName = ((EditText) findViewById(R.id.edtName)).getText().toString();
        String strDateOfBirth = edtBirthday.getText().toString();

        if (strName.isEmpty()) {
            Alerts.show(mContext, "Please enter name first !!!");
        } else if (strDateOfBirth.isEmpty()) {
            Alerts.show(mContext, "Please select date of birth !!!");
        } else {
            clickContinueB();
        }
    }

    private void clickContinue() {
        String strName = ((EditText) findViewById(R.id.edtName)).getText().toString();
        String strDateOfBirth = edtBirthday.getText().toString();

        if (strName.isEmpty()) {
            Alerts.show(mContext, "Please enter name first !!!");
        } else if (strDateOfBirth.isEmpty()) {
            Alerts.show(mContext, "Please select date of birth !!!");
        } else {
            // Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
            ((LinearLayout) findViewById(R.id.llCreateProfile)).setBackground(
                    getResources().getDrawable(R.drawable.textview_back_a));
            // viewFlipper.setAnimation(in);
            viewFlipper.showNext();
        }
    }

    private void clickBack() {
        // Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        ((LinearLayout) findViewById(R.id.llCreateProfile)).setBackground(
                getResources().getDrawable(R.drawable.layout_back_a));
        //viewFlipper.setAnimation(out);
        viewFlipper.showPrevious();
    }

    private void clickContinueB() {
        String strName = ((EditText) findViewById(R.id.edtName)).getText().toString();
        String strDateOfBirth = edtBirthday.getText().toString();
        String strMainSport = ((EditText) findViewById(R.id.edtMainSport)).getText().toString();
        String strCoach = ((EditText) findViewById(R.id.edtCoach)).getText().toString();
        String strClub = ((EditText) findViewById(R.id.edtClub)).getText().toString();
        String strBio = ((EditText) findViewById(R.id.edtBio)).getText().toString();

        if (strCountryName.equals("Select country")) {
            Alerts.show(mContext, "Please select country");
        } else {
            if (cd.isNetworkAvailable()) {
                RetrofitService.getContentData(new Dialog(mContext), retrofitApiClient.updateProfile(strUserId, strName, strIsAthlete,
                        strCountryName, strMainSport, strClub, strBio, strDateOfBirth, strCoach), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        ResponseBody responseBody = (ResponseBody) result.body();
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody.string());
                            //Alerts.show(mContext, jsonObject + "");
                            Intent intent = new Intent(mContext, HomeActivity.class);
                            intent.putExtra("user_id", strUserId);
                            intent.putExtra("create_profile", true);
                            startActivity(intent);
                            finish();
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
    }
}
