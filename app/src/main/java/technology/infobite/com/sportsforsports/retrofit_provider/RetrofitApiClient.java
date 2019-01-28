package technology.infobite.com.sportsforsports.retrofit_provider;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.Comment;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.DailyNewsFeedMainModal;
import technology.infobite.com.sportsforsports.modal.user_data.UserDataModal;

public interface RetrofitApiClient {

    @FormUrlEncoded
    @POST(Constant.LOGIN_API)
    Call<UserDataModal> signIn(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST(Constant.REGISTRATION_API)
    Call<UserDataModal> signUp(@Field("name") String name, @Field("email") String email,
                               @Field("password") String password);

    @FormUrlEncoded
    @POST(Constant.UPDATE_PROFILE_API)
    Call<ResponseBody> updateProfile(@Field("user_id") String user_id, @Field("name") String name,
                                     @Field("is_athlete") String is_athlete, @Field("country") String country,
                                     @Field("main_sport") String main_sport, @Field("club") String club,
                                     @Field("bio") String bio, @Field("dob") String dob,
                                     @Field("coach") String coach);

    @FormUrlEncoded
    @POST(Constant.USER_PROFILE_API)
    Call<UserDataModal> userProfile(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(Constant.NEWPOST_API)
    Call<ResponseBody> newPostFeed(@Field("user_id") String userid,@Field("athlete_status") String astatus,
                                    @Field("athlete_video") String avideo,@Field("athlete_artice_url") String aurl,
                                    @Field("athlete_artice_headline") String aheadline,@Field("alhlete_images") String aimage);

    @FormUrlEncoded
    @POST(Constant.TIMELINE_API)
    Call<DailyNewsFeedMainModal> showPostTimeLine(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST(Constant.POST_COMMENT_API)
    Call<Comment> newPostComment(@Field("post_id") String postId, @Field("user_id") String useId
                                                    , @Field("comment") String comment);

}