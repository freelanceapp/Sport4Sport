package technology.infobite.com.sportsforsports.modal.user_data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserComment implements Parcelable {

    @SerializedName("comment_id")
    @Expose
    private String commentId;
    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("date")
    @Expose
    private String date;
    public final static Parcelable.Creator<UserComment> CREATOR = new Creator<UserComment>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserComment createFromParcel(Parcel in) {
            return new UserComment(in);
        }

        public UserComment[] newArray(int size) {
            return (new UserComment[size]);
        }

    };

    protected UserComment(Parcel in) {
        this.commentId = ((String) in.readValue((String.class.getClassLoader())));
        this.postId = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.userName = ((String) in.readValue((String.class.getClassLoader())));
        this.comment = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
    }

    public UserComment() {
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(commentId);
        dest.writeValue(postId);
        dest.writeValue(userId);
        dest.writeValue(userName);
        dest.writeValue(comment);
        dest.writeValue(date);
    }

    public int describeContents() {
        return 0;
    }

}