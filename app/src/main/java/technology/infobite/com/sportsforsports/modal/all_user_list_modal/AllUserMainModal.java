package technology.infobite.com.sportsforsports.modal.all_user_list_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AllUserMainModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user")
    @Expose
    private List<AllUserList> allUserList = new ArrayList<AllUserList>();
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
        in.readList(this.allUserList, (technology.infobite.com.sportsforsports.modal.all_user_list_modal.AllUserList.class.getClassLoader()));
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

    public List<AllUserList> getAllUserList() {
        return allUserList;
    }

    public void setAllUserList(List<AllUserList> allUserList) {
        this.allUserList = allUserList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(allUserList);
    }

    public int describeContents() {
        return 0;
    }

}
