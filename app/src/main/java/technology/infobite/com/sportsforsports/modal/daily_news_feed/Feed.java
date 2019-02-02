
package technology.infobite.com.sportsforsports.modal.daily_news_feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Feed implements Serializable, Parcelable {

    @SerializedName("feed_id")
    @Expose
    private String feedId;
    @SerializedName("post_user_name")
    @Expose
    private String postUserName;
    @SerializedName("athlete_status")
    @Expose
    private String athleteStatus;
    @SerializedName("athlete_video")
    @Expose
    private String athleteVideo;
    @SerializedName("athlete_artice_url")
    @Expose
    private String athleteArticeUrl;
    @SerializedName("athlete_artice_headline")
    @Expose
    private String athleteArticeHeadline;
    @SerializedName("alhlete_images")
    @Expose
    private String alhleteImages;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("comment")
    @Expose
    private List<Comment> comment = new ArrayList<Comment>();
    @SerializedName("likes")
    @Expose
    private String likes;
    public final static Creator<Feed> CREATOR = new Creator<Feed>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        public Feed[] newArray(int size) {
            return (new Feed[size]);
        }

    };
    private final static long serialVersionUID = 5278743063907932546L;

    protected Feed(Parcel in) {
        this.feedId = ((String) in.readValue((String.class.getClassLoader())));
        this.postUserName = ((String) in.readValue((String.class.getClassLoader())));
        this.athleteStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.athleteVideo = ((String) in.readValue((String.class.getClassLoader())));
        this.athleteArticeUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.athleteArticeHeadline = ((String) in.readValue((String.class.getClassLoader())));
        this.alhleteImages = ((String) in.readValue((String.class.getClassLoader())));
        this.entryDate = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.comment, (technology.infobite.com.sportsforsports.modal.daily_news_feed.Comment.class.getClassLoader()));
        this.likes = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Feed() {
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getPostUserName() {
        return postUserName;
    }

    public void setPostUserName(String postUserName) {
        this.postUserName = postUserName;
    }

    public String getAthleteStatus() {
        return athleteStatus;
    }

    public void setAthleteStatus(String athleteStatus) {
        this.athleteStatus = athleteStatus;
    }

    public String getAthleteVideo() {
        return athleteVideo;
    }

    public void setAthleteVideo(String athleteVideo) {
        this.athleteVideo = athleteVideo;
    }

    public String getAthleteArticeUrl() {
        return athleteArticeUrl;
    }

    public void setAthleteArticeUrl(String athleteArticeUrl) {
        this.athleteArticeUrl = athleteArticeUrl;
    }

    public String getAthleteArticeHeadline() {
        return athleteArticeHeadline;
    }

    public void setAthleteArticeHeadline(String athleteArticeHeadline) {
        this.athleteArticeHeadline = athleteArticeHeadline;
    }

    public String getAlhleteImages() {
        return alhleteImages;
    }

    public void setAlhleteImages(String alhleteImages) {
        this.alhleteImages = alhleteImages;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(feedId);
        dest.writeValue(athleteStatus);
        dest.writeValue(postUserName);
        dest.writeValue(athleteVideo);
        dest.writeValue(athleteArticeUrl);
        dest.writeValue(athleteArticeHeadline);
        dest.writeValue(alhleteImages);
        dest.writeValue(entryDate);
        dest.writeList(comment);
        dest.writeValue(likes);
    }

    public int describeContents() {
        return 0;
    }

}
