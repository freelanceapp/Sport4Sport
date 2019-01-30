
package technology.infobite.com.sportsforsports.modal.daily_news_feed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyNewsFeedMainModal implements Serializable, Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("feed")
    @Expose
    private List<Feed> feed = new ArrayList<Feed>();
    public final static Creator<DailyNewsFeedMainModal> CREATOR = new Creator<DailyNewsFeedMainModal>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DailyNewsFeedMainModal createFromParcel(Parcel in) {
            return new DailyNewsFeedMainModal(in);
        }

        public DailyNewsFeedMainModal[] newArray(int size) {
            return (new DailyNewsFeedMainModal[size]);
        }

    }
    ;
    private final static long serialVersionUID = -881494313319940097L;

    protected DailyNewsFeedMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.feed, (Feed.class.getClassLoader()));
    }

    public DailyNewsFeedMainModal() {
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

    public List<Feed> getFeed() {
        return feed;
    }

    public void setFeed(List<Feed> feed) {
        this.feed = feed;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(feed);
    }

    public int describeContents() {
        return  0;
    }

}
