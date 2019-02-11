package technology.infobite.com.sportsforsports.modal.all_user_list_modal;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllUserMainModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user")
    @Expose
    private List<AllUserList> user = new ArrayList<AllUserList>();
    public final static Parcelable.Creator<AllUserMainModal> CREATOR = new Creator<AllUserMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AllUserMainModal createFromParcel(Parcel in) {
            return new AllUserMainModal(in);
        }

        public AllUserMainModal[] newArray(int size) {
            return (new AllUserMainModal[size]);
        }

    };

    protected AllUserMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.user, (AllUserList.class.getClassLoader()));
    }

    public AllUserMainModal() {
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

    public List<AllUserList> getUser() {
        return user;
    }

    public void setUser(List<AllUserList> user) {
        this.user = user;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(user);
    }

    public int describeContents() {
        return 0;
    }

}