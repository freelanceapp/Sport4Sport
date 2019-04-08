package com.pinlinx.modal.user_data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserFan implements Parcelable {

    @SerializedName("fan")
    @Expose
    private String fan;
    public final static Parcelable.Creator<UserFan> CREATOR = new Creator<UserFan>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserFan createFromParcel(Parcel in) {
            return new UserFan(in);
        }

        public UserFan[] newArray(int size) {
            return (new UserFan[size]);
        }

    };

    protected UserFan(Parcel in) {
        this.fan = ((String) in.readValue((String.class.getClassLoader())));
    }

    public UserFan() {
    }

    public String getFan() {
        return fan;
    }

    public void setFan(String fan) {
        this.fan = fan;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(fan);
    }

    public int describeContents() {
        return 0;
    }

}