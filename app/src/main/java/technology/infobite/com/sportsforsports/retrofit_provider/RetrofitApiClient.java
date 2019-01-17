package technology.infobite.com.sportsforsports.retrofit_provider;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import technology.infobite.com.sportsforsports.constant.Constant;

public interface RetrofitApiClient {

    @FormUrlEncoded
    @POST(Constant.LOGIN_API)
    Call<ResponseBody> signIn(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST(Constant.REGISTRATION_API)
    Call<ResponseBody> signUp(@Field("name") String name, @Field("email") String email,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST(Constant.DOWNLOAD_API)
    Call<ResponseBody> downloadCount(@Field("mid") String mid, @Field("sid") String sid);
}