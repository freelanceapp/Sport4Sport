package technology.infobite.com.sportsforsports.modal.league;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LeagueMainModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("league")
    @Expose
    private List<LeagueList> leagueList = new ArrayList<LeagueList>();
    public final static Parcelable.Creator<LeagueMainModal> CREATOR = new Creator<LeagueMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LeagueMainModal createFromParcel(Parcel in) {
            return new LeagueMainModal(in);
        }

        public LeagueMainModal[] newArray(int size) {
            return (new LeagueMainModal[size]);
        }

    };

    protected LeagueMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.leagueList, (technology.infobite.com.sportsforsports.modal.league.LeagueList.class.getClassLoader()));
    }

    public LeagueMainModal() {
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

    public List<LeagueList> getLeagueList() {
        return leagueList;
    }

    public void setLeagueList(List<LeagueList> leagueList) {
        this.leagueList = leagueList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(leagueList);
    }

    public int describeContents() {
        return 0;
    }

}
