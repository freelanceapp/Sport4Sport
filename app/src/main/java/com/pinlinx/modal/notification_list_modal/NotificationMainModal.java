package com.pinlinx.modal.notification_list_modal;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationMainModal implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("notifaction")
    @Expose
    private List<NotificationList> notifaction = new ArrayList<NotificationList>();
    public final static Parcelable.Creator<NotificationMainModal> CREATOR = new Creator<NotificationMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public NotificationMainModal createFromParcel(Parcel in) {
            return new NotificationMainModal(in);
        }

        public NotificationMainModal[] newArray(int size) {
            return (new NotificationMainModal[size]);
        }

    }
            ;

    protected NotificationMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.notifaction, (NotificationList.class.getClassLoader()));
    }

    public NotificationMainModal() {
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

    public List<NotificationList> getNotifaction() {
        return notifaction;
    }

    public void setNotifaction(List<NotificationList> notifaction) {
        this.notifaction = notifaction;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(notifaction);
    }

    public int describeContents() {
        return 0;
    }

}
