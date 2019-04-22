package com.pinlinx.modal.user_data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData implements Parcelable {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("main_sport")
    @Expose
    private String mainSport;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("club")
    @Expose
    private String club;
    @SerializedName("discipline")
    @Expose
    private String discipline;
    @SerializedName("coach")
    @Expose
    private String coach;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("cover_img")
    @Expose
    private String coverImg;
    @SerializedName("avtar_img")
    @Expose
    private String avtarImg;
    @SerializedName("facabook")
    @Expose
    private String facabook;
    @SerializedName("youtube")
    @Expose
    private String youtube;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("google_plus")
    @Expose
    private String googlePlus;
    @SerializedName("linkedin")
    @Expose
    private String linkedin;
    @SerializedName("blog")
    @Expose
    private String blog;
    @SerializedName("is_athlete")
    @Expose
    private String isAthlete;
    @SerializedName("fans")
    @Expose
    private String fans;
    @SerializedName("social_id")
    @Expose
    private String socialId;
    @SerializedName("social_type")
    @Expose
    private String socialType;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("pre_game_rituals")
    @Expose
    private String preGameRituals;
    @SerializedName("college")
    @Expose
    private String college;
    @SerializedName("other_sport")
    @Expose
    private String otherSport;
    @SerializedName("city")
    @Expose
    private String city;
    public final static Parcelable.Creator<UserData> CREATOR = new Creator<UserData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        public UserData[] newArray(int size) {
            return (new UserData[size]);
        }

    };

    protected UserData(Parcel in) {
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.userName = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.country = ((String) in.readValue((String.class.getClassLoader())));
        this.dob = ((String) in.readValue((String.class.getClassLoader())));
        this.mainSport = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((String) in.readValue((String.class.getClassLoader())));
        this.club = ((String) in.readValue((String.class.getClassLoader())));
        this.discipline = ((String) in.readValue((String.class.getClassLoader())));
        this.coach = ((String) in.readValue((String.class.getClassLoader())));
        this.bio = ((String) in.readValue((String.class.getClassLoader())));
        this.coverImg = ((String) in.readValue((String.class.getClassLoader())));
        this.avtarImg = ((String) in.readValue((String.class.getClassLoader())));
        this.facabook = ((String) in.readValue((String.class.getClassLoader())));
        this.youtube = ((String) in.readValue((String.class.getClassLoader())));
        this.twitter = ((String) in.readValue((String.class.getClassLoader())));
        this.instagram = ((String) in.readValue((String.class.getClassLoader())));
        this.googlePlus = ((String) in.readValue((String.class.getClassLoader())));
        this.linkedin = ((String) in.readValue((String.class.getClassLoader())));
        this.blog = ((String) in.readValue((String.class.getClassLoader())));
        this.isAthlete = ((String) in.readValue((String.class.getClassLoader())));
        this.fans = ((String) in.readValue((String.class.getClassLoader())));
        this.socialId = ((String) in.readValue((String.class.getClassLoader())));
        this.socialType = ((String) in.readValue((String.class.getClassLoader())));
        this.nickname = ((String) in.readValue((String.class.getClassLoader())));
        this.height = ((String) in.readValue((String.class.getClassLoader())));
        this.weight = ((String) in.readValue((String.class.getClassLoader())));
        this.position = ((String) in.readValue((String.class.getClassLoader())));
        this.preGameRituals = ((String) in.readValue((String.class.getClassLoader())));
        this.college = ((String) in.readValue((String.class.getClassLoader())));
        this.otherSport = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
    }

    public UserData() {
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMainSport() {
        return mainSport;
    }

    public void setMainSport(String mainSport) {
        this.mainSport = mainSport;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getAvtarImg() {
        return avtarImg;
    }

    public void setAvtarImg(String avtarImg) {
        this.avtarImg = avtarImg;
    }

    public String getFacabook() {
        return facabook;
    }

    public void setFacabook(String facabook) {
        this.facabook = facabook;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getGooglePlus() {
        return googlePlus;
    }

    public void setGooglePlus(String googlePlus) {
        this.googlePlus = googlePlus;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getIsAthlete() {
        return isAthlete;
    }

    public void setIsAthlete(String isAthlete) {
        this.isAthlete = isAthlete;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getSocialType() {
        return socialType;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPreGameRituals() {
        return preGameRituals;
    }

    public void setPreGameRituals(String preGameRituals) {
        this.preGameRituals = preGameRituals;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getOtherSport() {
        return otherSport;
    }

    public void setOtherSport(String otherSport) {
        this.otherSport = otherSport;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(userName);
        dest.writeValue(email);
        dest.writeValue(country);
        dest.writeValue(dob);
        dest.writeValue(mainSport);
        dest.writeValue(gender);
        dest.writeValue(club);
        dest.writeValue(discipline);
        dest.writeValue(coach);
        dest.writeValue(bio);
        dest.writeValue(coverImg);
        dest.writeValue(avtarImg);
        dest.writeValue(facabook);
        dest.writeValue(youtube);
        dest.writeValue(twitter);
        dest.writeValue(instagram);
        dest.writeValue(googlePlus);
        dest.writeValue(linkedin);
        dest.writeValue(blog);
        dest.writeValue(isAthlete);
        dest.writeValue(fans);
        dest.writeValue(socialId);
        dest.writeValue(socialType);
        dest.writeValue(nickname);
        dest.writeValue(height);
        dest.writeValue(weight);
        dest.writeValue(position);
        dest.writeValue(preGameRituals);
        dest.writeValue(otherSport);
        dest.writeValue(college);
        dest.writeValue(city);
    }

    public int describeContents() {
        return 0;
    }

}