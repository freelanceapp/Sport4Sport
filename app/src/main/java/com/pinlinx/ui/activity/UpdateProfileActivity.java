package com.pinlinx.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Response;
import com.pinlinx.R;
import com.pinlinx.constant.Constant;
import com.pinlinx.modal.user_data.UserDataModal;
import com.pinlinx.retrofit_provider.RetrofitService;
import com.pinlinx.retrofit_provider.WebResponse;
import com.pinlinx.utils.Alerts;
import com.pinlinx.utils.AppPreference;
import com.pinlinx.utils.AppProgressDialog;
import com.pinlinx.utils.BaseActivity;
import com.pinlinx.utils.GpsTracker;

public class UpdateProfileActivity extends BaseActivity implements View.OnClickListener {

    private UserDataModal userDataModal;

    private int mYear = 0, mMonth = 0, mDay = 0;
    private String strDob = "";
    private ViewFlipper viewFlipper;
    private EditText edtBirthday;
    private CheckBox checkBoxAthlete;
    private String strIsAthlete = "0";

    private String strUserName = "";
    private String strUserId = "";

    private Spinner spinnerCountryList, spinnerHeight, spinnerWeight, spinnerYear;
    private String strCountryName = "", strHeightUnit = "", strWeightUnit = "", strYear = "";
    double latitude; // latitude
    double longitude; // longitude
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        mContext = this;
        dialog = new Dialog(mContext);

        init();
        getLatLong();
        getIntentData();
    }

    private void getLatLong() {
        GpsTracker gpsTracker = new GpsTracker(mContext);
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
        getAddressList();
    }

    private void getAddressList() {
        AppProgressDialog.show(dialog);
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                AppProgressDialog.hide(dialog);
                //address_et.setText(addresses.get(0).getAddressLine(0));
                ((EditText) findViewById(R.id.edtCity)).setText(addresses.get(0).getLocality());
                //((EditText) findViewById(R.id.edtState)).setText(addresses.get(0).getAdminArea());
                //country_et.setText(addresses.get(0).getCountryName());
                //zipcode_et.setText(addresses.get(0).getPostalCode());
            } else {
                AppProgressDialog.show(dialog);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getLatLong();
                    }
                }, 3000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getIntentData() {
        if (getIntent() == null)
            return;

        ((TextView) findViewById(R.id.txtLblA)).setText("Update profile");
        ((Button) findViewById(R.id.btnContinueB)).setText("Update");
        userDataModal = getIntent().getParcelableExtra("userDataModal");
        strUserId = userDataModal.getUser().getUserId();
        strUserName = userDataModal.getUser().getUserName();
        ((EditText) findViewById(R.id.edtName)).setText(strUserName);
        ((EditText) findViewById(R.id.edtBirthday)).setText(userDataModal.getUser().getDob());
        strCountryName = userDataModal.getUser().getCountry();
        ((TextView) findViewById(R.id.txtCountryName)).setText(strCountryName);
        ((EditText) findViewById(R.id.edtMainSport)).setText(userDataModal.getUser().getMainSport());
        ((EditText) findViewById(R.id.edtCoach)).setText(userDataModal.getUser().getCoach());
        ((EditText) findViewById(R.id.edtClub)).setText(userDataModal.getUser().getClub());
        ((EditText) findViewById(R.id.edtBio)).setText(userDataModal.getUser().getBio());

        ((EditText) findViewById(R.id.edtCity)).setText(userDataModal.getUser().getCity());
        ((EditText) findViewById(R.id.edtNickname)).setText(userDataModal.getUser().getNickname());
        ((EditText) findViewById(R.id.edtHeight)).setText(userDataModal.getUser().getHeight());
        ((EditText) findViewById(R.id.edtWeight)).setText(userDataModal.getUser().getWeight());
        ((EditText) findViewById(R.id.edtPosition)).setText(userDataModal.getUser().getPosition());
        ((EditText) findViewById(R.id.edtRituals)).setText(userDataModal.getUser().getPreGameRituals());
        ((EditText) findViewById(R.id.edtOtherSport)).setText(userDataModal.getUser().getCollege());
        ((EditText) findViewById(R.id.edtCollege)).setText(userDataModal.getUser().getOtherSport());
    }

    private void init() {
        spinnerCountryList = findViewById(R.id.spinnerCountryList);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Constant.countries);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerCountryList.setAdapter(adapter);
        spinnerCountryList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strCountryName = parent.getItemAtPosition(position).toString();
                if (strCountryName.equals("Select country")) {
                    strCountryName = userDataModal.getUser().getCountry();
                    ((TextView) findViewById(R.id.txtCountryName)).setText(strCountryName);
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

        setSpinnerData();
    }

    private void setSpinnerData() {
        spinnerHeight = findViewById(R.id.spinnerHeight);
        spinnerWeight = findViewById(R.id.spinnerWeight);
        spinnerYear = findViewById(R.id.spinnerYear);

        ArrayAdapter adapterHeight = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Constant.heightUnit);
        adapterHeight.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerHeight.setAdapter(adapterHeight);
        spinnerHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strHeightUnit = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter adapterWeight = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Constant.weightUnit);
        adapterWeight.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerWeight.setAdapter(adapterWeight);
        spinnerWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strWeightUnit = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> yearList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int year = 1930;
            year = year + i;
            String strYear = String.valueOf(year);
            yearList.add(strYear);
        }

        ArrayAdapter adapterYear = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yearList);
        adapterWeight.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerYear.setAdapter(adapterYear);
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strYear = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    }

    private void clickContinue() {
        String strName = ((EditText) findViewById(R.id.edtName)).getText().toString();
        String strDateOfBirth = edtBirthday.getText().toString();
        String strCity = ((EditText) findViewById(R.id.edtCity)).getText().toString();

        String regex = "(.)*(\\d)(.)*";
        Pattern pattern = Pattern.compile(regex);
        String msg = strName;
        boolean containsNumber = pattern.matcher(msg).matches();

        if (strName.isEmpty()) {
            Alerts.show(mContext, "Please enter name first !!!");
        } else if (containsNumber) {
            Alerts.show(mContext, "Name contains only alphabets");
        } else if (strDateOfBirth.isEmpty()) {
            Alerts.show(mContext, "Please select date of birth !!!");
        } else if (strCity.isEmpty()) {
            Alerts.show(mContext, "Please enter city");
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

        String strNickname = ((EditText) findViewById(R.id.edtNickname)).getText().toString();
        String strHeight = ((EditText) findViewById(R.id.edtHeight)).getText().toString();
        String strWeight = ((EditText) findViewById(R.id.edtWeight)).getText().toString();
        String strPosition = ((EditText) findViewById(R.id.edtPosition)).getText().toString();
        String strRituals = ((EditText) findViewById(R.id.edtRituals)).getText().toString();
        String strCollege = ((EditText) findViewById(R.id.edtCollege)).getText().toString();
        String strOtherSport = ((EditText) findViewById(R.id.edtOtherSport)).getText().toString();
        String strCity = ((EditText) findViewById(R.id.edtCity)).getText().toString();
        String strCountry = ((TextView) findViewById(R.id.txtCountryName)).getText().toString();

        strHeight = strHeight + " " + strHeightUnit;
        strWeight = strWeight + " " + strWeightUnit;
        strCollege = strCollege + " , " + strYear;

        String regex = "(.)*(\\d)(.)*";
        Pattern pattern = Pattern.compile(regex);
        String msg = strMainSport;
        boolean containsNumber = pattern.matcher(msg).matches();

        if (strCountryName.equals("Select country")) {
            Alerts.show(mContext, "Please select country");
        } else if (containsNumber) {
            Alerts.show(mContext, "Main sport contains only alphabets");
        } else if (strCountry.isEmpty()) {
            Alerts.show(mContext, "Please select country");
        } else {
            if (cd.isNetworkAvailable()) {
                RetrofitService.getContentData(new Dialog(mContext), retrofitApiClient.updateProfile(strUserId, strName, strIsAthlete,
                        strCountry, strMainSport, strClub, strBio, strDateOfBirth, strCoach, strNickname, strHeight,
                        strWeight, strPosition, strRituals, strCollege, strOtherSport, strCity), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        ResponseBody responseBody = (ResponseBody) result.body();
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody.string());
                            if (!jsonObject.getBoolean("error")) {
                                Alerts.show(mContext, jsonObject.getString("message"));
                                AppPreference.setBooleanPreference(mContext, "UpdateProfile", true);
                                finish();
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
    }
}
