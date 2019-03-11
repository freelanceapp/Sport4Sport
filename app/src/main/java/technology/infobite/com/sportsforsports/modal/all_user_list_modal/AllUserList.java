package technology.infobite.com.sportsforsports.modal.all_user_list_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllUserList implements Parcelable {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("avtar_img")
    @Expose
    private String avtarImg;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("main_sport")
    @Expose
    private String mainSport;
    public final static Parcelable.Creator<AllUserList> CREATOR = new Creator<AllUserList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AllUserList createFromParcel(Parcel in) {
            return new AllUserList(in);
        }

        public AllUserList[] newArray(int size) {
            return (new AllUserList[size]);
        }

    };

    protected AllUserList(Parcel in) {
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.userName = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.avtarImg = ((String) in.readValue((String.class.getClassLoader())));
        this.bio = ((String) in.readValue((String.class.getClassLoader())));
        this.mainSport = ((String) in.readValue((String.class.getClassLoader())));
    }

    public AllUserList() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvtarImg() {
        return avtarImg;
    }

    public void setAvtarImg(String avtarImg) {
        this.avtarImg = avtarImg;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getMainSport() {
        return mainSport;
    }

    public void setMainSport(String mainSport) {
        this.mainSport = mainSport;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(userName);
        dest.writeValue(email);
        dest.writeValue(avtarImg);
        dest.writeValue(bio);
        dest.writeValue(mainSport);
    }

    public int describeContents() {
        return 0;
    }

}