package technology.infobite.com.sportsforsports.modal.notification_list_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationList implements Parcelable
{

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("avtar_img")
    @Expose
    private String avtarImg;
    @SerializedName("fan_user_id")
    @Expose
    private String fanUserId;
    @SerializedName("fan_user_name")
    @Expose
    private String fanUserName;
    @SerializedName("fan_avtar_img")
    @Expose
    private String fanAvtarImg;
    @SerializedName("notifaction_type")
    @Expose
    private String notifactionType;
    @SerializedName("post_id")
    @Expose
    private String postId;
    public final static Parcelable.Creator<NotificationList> CREATOR = new Creator<NotificationList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public NotificationList createFromParcel(Parcel in) {
            return new NotificationList(in);
        }

        public NotificationList[] newArray(int size) {
            return (new NotificationList[size]);
        }

    }
            ;

    protected NotificationList(Parcel in) {
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.userName = ((String) in.readValue((String.class.getClassLoader())));
        this.avtarImg = ((String) in.readValue((String.class.getClassLoader())));
        this.fanUserId = ((String) in.readValue((String.class.getClassLoader())));
        this.fanUserName = ((String) in.readValue((String.class.getClassLoader())));
        this.fanAvtarImg = ((String) in.readValue((String.class.getClassLoader())));
        this.notifactionType = ((String) in.readValue((String.class.getClassLoader())));
        this.postId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public NotificationList() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvtarImg() {
        return avtarImg;
    }

    public void setAvtarImg(String avtarImg) {
        this.avtarImg = avtarImg;
    }

    public String getFanUserId() {
        return fanUserId;
    }

    public void setFanUserId(String fanUserId) {
        this.fanUserId = fanUserId;
    }

    public String getFanUserName() {
        return fanUserName;
    }

    public void setFanUserName(String fanUserName) {
        this.fanUserName = fanUserName;
    }

    public String getFanAvtarImg() {
        return fanAvtarImg;
    }

    public void setFanAvtarImg(String fanAvtarImg) {
        this.fanAvtarImg = fanAvtarImg;
    }

    public String getNotifactionType() {
        return notifactionType;
    }

    public void setNotifactionType(String notifactionType) {
        this.notifactionType = notifactionType;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(userName);
        dest.writeValue(avtarImg);
        dest.writeValue(fanUserId);
        dest.writeValue(fanUserName);
        dest.writeValue(fanAvtarImg);
        dest.writeValue(notifactionType);
        dest.writeValue(postId);
    }

    public int describeContents() {
        return 0;
    }

}