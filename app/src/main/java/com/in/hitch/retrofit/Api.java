package com.in.hitch.retrofit;

import com.in.hitch.Model.ChatDashboardModel;
import com.in.hitch.Model.ChatModel;
import com.in.hitch.Model.CommonModel;
import com.in.hitch.Model.GetUserFilterModel;
import com.in.hitch.Model.GetUserImageModel;
import com.in.hitch.Model.MembershipPlaneModel;
import com.in.hitch.Model.PurchasePlaneModel;
import com.in.hitch.Model.TransactionModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    @FormUrlEncoded
    @POST("social_login.php")
    Call<CommonModel> socialLogin(

            @Field("token") String token,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("email") String email,
            @Field("profile") String profile,
            @Field("platform") String platform);

    @FormUrlEncoded
    @POST("login.php")
    Call<CommonModel> login(
            @Field("token") String token,
            @Field("country_code") String country_code,
            @Field("mobile_no") String mobile_no

    );

    @FormUrlEncoded
    @POST("profile_completion.php")
    Call<CommonModel> ProfileCompletion(

            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("email") String email,
            @Field("birthday") String birthday,
            @Field("gender_id") String gender_id,
            @Field("interest_id") String interest_id,
            @Field("religion_id") String religion_id,
            @Field("sexual_id[]") ArrayList<String> sexual_id);

    @Multipart
    @POST("add_image.php")
    Call<Responsee> abc(

            @Part("token") RequestBody token,
            @Part("user_id") RequestBody user_id,
            @Part MultipartBody.Part image

    );

    @FormUrlEncoded
    @POST("get_user_image.php")
    Call<GetUserImageModel> GetUserImage(

            @Field("token") String token,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("update_filter.php")
    Call<CommonModel> UpdateFilter(

            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("interest_id") String interest_id,
            @Field("religion_id") String religion_id,
            @Field("sexual_id") List<String> sexual_id,
            @Field("start_age") String start_age,
            @Field("end_age") String end_age,
            @Field("start_distance") String start_distance,
            @Field("end_distance") String end_distance);

    @FormUrlEncoded
    @POST("get_user_filter.php")
    Call<GetUserFilterModel> GetUserFilter(

            @Field("token") String token,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("update_profile.php")
    Call<CommonModel> updateProfile(
            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("job_title") String job_title,
            @Field("company_name") String company_name,
            @Field("school_name") String school_name,
            @Field("current_location") String current_location,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("gender_id") String gender_id,
            @Field("sexual_id") List<String> sexual_id,
            @Field("show_my_age") String show_my_age,
            @Field("show_my_distance") String show_my_distance);


    @FormUrlEncoded
    @POST("get_chat_list.php")
    Call<ChatModel> getChatList(

            @Field("token") String token,
            @Field("userid") String user_id,
            @Field("search_keyword") String search_keyword);

    @FormUrlEncoded
    @POST("get_chat.php")
    Call<ChatDashboardModel> getChat(

            @Field("token") String token,
            @Field("chat_id") String chat_id,
            @Field("userid") String userid);

    @FormUrlEncoded
    @POST("send_text_message.php")
    Call<CommonModel> sendTextMessage(
            @Field("token") String token,
            @Field("from_user_id") String from_user_id,
            @Field("to_user_id") String to_user_id,
            @Field("message") String message);


    @FormUrlEncoded
    @POST("delete_message.php")
    Call<CommonModel> deleteMessage(
            @Field("token") String token,
            @Field("msg_id") String msg_id);

    @FormUrlEncoded
    @POST("payment_history.php")
    Call<TransactionModel> paymentHistory(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("search_keyword") String search_keyword);

    @FormUrlEncoded
    @POST("get_plans.php")
    Call<MembershipPlaneModel> getPlans(
            @Field("token") String token);

    @FormUrlEncoded
    @POST("purchase_membership_plan.php")
    Call<PurchasePlaneModel> purchasePlans(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("plan_id") String plan_id);

    @FormUrlEncoded
    @POST("delete_account.php")
    Call<CommonModel> deleteAccount(
            @Field("token") String token,
            @Field("user_id") String userid);

    @FormUrlEncoded
    @POST("report_user.php")
    Call<CommonModel> reportUser(
            @Field("token") String token,
            @Field("login_user_id") String login_user_id,
            @Field("report_user_id") String report_user_id,
            @Field("comment") String comment);

    @FormUrlEncoded
    @POST("block_user.php")
    Call<CommonModel> blockUser(
            @Field("token") String token,
            @Field("login_user_id") String login_user_id,
            @Field("report_user_id") String report_user_id);
}
