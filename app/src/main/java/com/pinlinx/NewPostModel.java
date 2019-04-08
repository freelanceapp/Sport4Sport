package com.pinlinx;

public class NewPostModel {

    private int image1;
    private String name;
    private int post;
    private String like;
    private String coment;
    private String timeDuration;
    private String text1;
    private  String allcomment;

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }
    public String getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(String timeDuration) {
        this.timeDuration = timeDuration;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getAllcomment() {
        return allcomment;
    }

    public void setAllcomment(String allcomment) {
        this.allcomment = allcomment;
    }


    public NewPostModel(int image1, String name, int post, String like, String coment, String timeDuration, String text1, String allcomment) {

        this.image1 = image1;
        this.name = name;
        this.post = post;
        this.like = like;
        this.coment = coment;
        this.timeDuration = timeDuration;
        this.text1 = text1;
        this.allcomment = allcomment;
    }

}
