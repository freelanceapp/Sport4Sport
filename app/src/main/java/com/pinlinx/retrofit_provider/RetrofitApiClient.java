package com.pinlinx.retrofit_provider;

import com.pinlinx.constant.Constant;
import com.pinlinx.modal.all_user_list_modal.AllUserMainModal;
import com.pinlinx.modal.daily_news_feed.DailyNewsFeedMainModal;
import com.pinlinx.modal.league.LeagueMainModal;
import com.pinlinx.modal.notification_list_modal.NotificationMainModal;
import com.pinlinx.modal.post_comment_modal.PostCommentResponseModal;
import com.pinlinx.modal.user_data.UserDataModal;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitApiClient {

    @FormUrlEncoded
    @POST(Constant.LOGIN_API)
    Call<UserDataModal> signIn(@Field("email") String email, @Field("password") String password,
                               @Field("token") String token);

    @FormUrlEncoded
    @POST(Constant.FB_API)
    Call<UserDataModal> fbLogin(@Field("name") String name, @Field("email") String email,
                                @Field("profile_pic") String profile_pic, @Field("social_type") String social_type,
                                @Field("social_id") String social_id, @Field("token") String token);

    @FormUrlEncoded
    @POST(Constant.REGISTRATION_API)
    Call<UserDataModal> signUp(@Field("name") String name, @Field("email") String email,
                               @Field("password") String password, @Field("token") String token);

    @FormUrlEncoded
    @POST(Constant.UPDATE_PROFILE_API)
    Call<ResponseBody> updateProfile(@Field("user_id") String user_id, @Field("name") String name,
                                     @Field("is_athlete") String is_athlete, @Field("country") String country,
                                     @Field("main_sport") String main_sport, @Field("club") String club,
                                     @Field("bio") String bio, @Field("dob") String dob,
                                     @Field("coach") String coach, @Field("nickname") String nickname,
                                     @Field("height") String height, @Field("weight") String weight,
                                     @Field("position") String position, @Field("pre_game_rituals") String pre_game_rituals,
                                     @Field("college") String college, @Field("other_sport") String other_sport,
                                     @Field("city") String city, @Field("discipline") String discipline,
                                     @Field("gender") String gender, @Field("surname") String surname,
                                     @Field("profession") String profession, @Field("contact") String contact,
                                     @Field("insurance") String insurance);

    @Multipart
    @POST(Constant.UPDATE_PROFILE_IMAGE)
    Call<ResponseBody> updateProfileImage(@Part("user_id") RequestBody user_id, @Part MultipartBody.Part aimage);

    @FormUrlEncoded
    @POST(Constant.USER_PROFILE_API)
    Call<UserDataModal> userProfile(@Field("user_id") String user_id);

    @Multipart
    @POST(Constant.NEWPOST_API)
    Call<ResponseBody> newPostFeed(@Part("user_id") RequestBody userid, @Part("athlete_status") RequestBody astatus,
                                   @Part MultipartBody.Part avideo, @Part("athlete_artice_url") RequestBody aurl,
                                   @Part("athlete_artice_headline") RequestBody aheadline,
                                   @Part MultipartBody.Part aimage, @Part MultipartBody.Part thumbnail);

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

    @FormUrlEncoded
    @POST(Constant.USER_LIST)
    Call<AllUserMainModal> getAllUserList(@Field("user_id") String useId);

    @FormUrlEncoded
    @POST(Constant.MY_FANS)
    Call<AllUserMainModal> myFans(@Field("user_id") String useId);

    @FormUrlEncoded
    @POST(Constant.FOLLOW_API)
    Call<ResponseBody> followUser(@Field("fan_user_id") String fan_user_id, @Field("user_id") String useId,
                                  @Field("following_resuest") String following_request);

    @FormUrlEncoded
    @POST(Constant.CHECK_FOLLOW)
    Call<ResponseBody> checkFollow(@Field("fan_user_id") String fan_user_id, @Field("user_id") String useId);

    @FormUrlEncoded
    @POST(Constant.NOTIFICATION_LIST)
    Call<NotificationMainModal> notificationList(@Field("user_id") String useId);

    @FormUrlEncoded
    @POST(Constant.UPDATE_TOKEN)
    Call<ResponseBody> updateToken(@Field("user_id") String useId, @Field("token") String token);

    @FormUrlEncoded
    @POST(Constant.SINGLE_POST)
    Call<DailyNewsFeedMainModal> postDetail(@Field("user_id") String useId, @Field("post_id") String post_id);

    @FormUrlEncoded
    @POST(Constant.DELETE_POST)
    Call<ResponseBody> deletePost(@Field("post_id") String post_id);

    @FormUrlEncoded
    @POST(Constant.LEAGUE_LIST)
    Call<LeagueMainModal> leagueList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(Constant.LEAGUE_FOLLOWING_LIST)
    Call<LeagueMainModal> leagueFollowingList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(Constant.LEAGUE_FOLLOW)
    Call<ResponseBody> leagueFollow(@Field("user_id") String user_id, @Field("league_id") String league_id,
                                    @Field("status") String status);

    @FormUrlEncoded
    @POST(Constant.EMAIL_OTP)
    Call<ResponseBody> emailOtp(@Field("email") String email);

    @FormUrlEncoded
    @POST(Constant.VERIFY_OTP)
    Call<ResponseBody> emailVerify(@Field("email") String email, @Field("otp") String otp);

    @FormUrlEncoded
    @POST(Constant.VERIFY_EMAIL)
    Call<ResponseBody> emailVerification(@Field("email") String email, @Field("otp") String otp);

    @FormUrlEncoded
    @POST(Constant.NEW_PASSWORD)
    Call<ResponseBody> newPassword(@Field("email") String email, @Field("password") String password);
}