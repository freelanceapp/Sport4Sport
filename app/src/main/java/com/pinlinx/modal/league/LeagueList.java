package com.pinlinx.modal.league;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeagueList implements Parcelable {

    @SerializedName("league_id")
    @Expose
    private String leagueId;
    @SerializedName("league_name")
    @Expose
    private String leagueName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("league_description")
    @Expose
    private String leagueDescription;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Parcelable.Creator<LeagueList> CREATOR = new Creator<LeagueList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LeagueList createFromParcel(Parcel in) {
            return new LeagueList(in);
        }

        public LeagueList[] newArray(int size) {
            return (new LeagueList[size]);
        }

    };

    protected LeagueList(Parcel in) {
        this.leagueId = ((String) in.readValue((String.class.getClassLoader())));
        this.leagueName = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.leagueDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.category = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public LeagueList() {
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLeagueDescription() {
        return leagueDescription;
    }

    public void setLeagueDescription(String leagueDescription) {
        this.leagueDescription = leagueDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(leagueId);
        dest.writeValue(leagueName);
        dest.writeValue(image);
        dest.writeValue(leagueDescription);
        dest.writeValue(category);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }

}