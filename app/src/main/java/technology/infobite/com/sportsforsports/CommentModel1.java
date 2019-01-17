package technology.infobite.com.sportsforsports;

public class CommentModel1 {

    private String comment;
    private String timeduartion;

    public CommentModel1(String comment, String timeduartion) {
        this.comment = comment;
        this.timeduartion = timeduartion;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimeduartion() {
        return timeduartion;
    }

    public void setTimeduartion(String timeduartion) {
        this.timeduartion = timeduartion;
    }
}
