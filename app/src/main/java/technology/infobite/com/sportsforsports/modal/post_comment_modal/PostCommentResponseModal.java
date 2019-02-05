package technology.infobite.com.sportsforsports.modal.post_comment_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import technology.infobite.com.sportsforsports.modal.daily_news_feed.Comment;

public class PostCommentResponseModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("comment")
    @Expose
    private List<Comment> comment = new ArrayList<Comment>();
    public final static Creator<PostCommentResponseModal> CREATOR = new Creator<PostCommentResponseModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PostCommentResponseModal createFromParcel(Parcel in) {
            return new PostCommentResponseModal(in);
        }

        public PostCommentResponseModal[] newArray(int size) {
            return (new PostCommentResponseModal[size]);
        }

    };

    protected PostCommentResponseModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.comment, (Comment.class.getClassLoader()));
    }

    public PostCommentResponseModal() {
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

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(comment);
    }

    public int describeContents() {
        return 0;
    }

}
