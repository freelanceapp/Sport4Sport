package com.pinlinx.modal.user_data;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.pinlinx.modal.daily_news_feed.UserFeed;

public class UserDataModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user")
    @Expose
    private UserData user;
    @SerializedName("feed")
    @Expose
    private List<UserFeed> feed = new ArrayList<UserFeed>();
    @SerializedName("fan")
    @Expose
    private List<UserFan> fan = new ArrayList<UserFan>();
    public final static Parcelable.Creator<UserDataModal> CREATOR = new Creator<UserDataModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserDataModal createFromParcel(Parcel in) {
            return new UserDataModal(in);
        }

        public UserDataModal[] newArray(int size) {
            return (new UserDataModal[size]);
        }

    };

    protected UserDataModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.user = ((UserData) in.readValue((UserData.class.getClassLoader())));
        in.readList(this.feed, (UserFeed.class.getClassLoader()));
        in.readList(this.fan, (UserFan.class.getClassLoader()));
    }

    public UserDataModal() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public List<UserFeed> getFeed() {
        return feed;
    }

    public void setFeed(List<UserFeed> feed) {
        this.feed = feed;
    }

    public List<UserFan> getFan() {
        return fan;
    }

    public void setFan(List<UserFan> fan) {
        this.fan = fan;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeValue(user);
        dest.writeList(feed);
        dest.writeList(fan);
    }

    public int describeContents() {
        return 0;
    }

}
