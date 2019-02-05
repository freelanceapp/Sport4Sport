package technology.infobite.com.sportsforsports.retrofit_provider;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import technology.infobite.com.sportsforsports.constant.Constant;
import technology.infobite.com.sportsforsports.modal.daily_news_feed.DailyNewsFeedMainModal;
import technology.infobite.com.sportsforsports.modal.post_comment_modal.PostCommentResponseModal;
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

    @Multipart
    @POST(Constant.NEWPOST_API)
    Call<ResponseBody> newPostFeed(@Part("user_id") RequestBody userid, @Part("athlete_status") RequestBody astatus,
                                   @Part MultipartBody.Part avideo, @Part("athlete_artice_url") RequestBody aurl,
                                   @Part("athlete_artice_headline") RequestBody aheadline,
                                   @Part MultipartBody.Part aimage);

    @FormUrlEncoded
    @POST(Constant.TIMELINE_API)
    Call<DailyNewsFeedMainModal> showPostTimeLine(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST(Constant.POST_COMMENT_API)
    Call<PostCommentResponseModal> newPostComment(@Field("post_id") String postId, @Field("user_id") String useId,

                                                  @Field("comment") String comment);

    @FormUrlEncoded
    @POST(Constant.PostLikeAPI)
    Call<ResponseBody> postLike(@Field("post_id") String postId, @Field("user_id") String useId,
                                @Field("status") String status);

}