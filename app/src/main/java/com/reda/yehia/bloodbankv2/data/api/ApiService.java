package com.reda.yehia.bloodbankv2.data.api;


import com.reda.yehia.bloodbankv2.data.model.client.Client;
import com.reda.yehia.bloodbankv2.data.model.contactUs.ContactUs;
import com.reda.yehia.bloodbankv2.data.model.donation.createNewDonation.CreateNewDonation;
import com.reda.yehia.bloodbankv2.data.model.donation.donationRequests.DonationRequests;
import com.reda.yehia.bloodbankv2.data.model.generalResponse.GeneralResponse;
import com.reda.yehia.bloodbankv2.data.model.notification.getAllNotification.Notification;
import com.reda.yehia.bloodbankv2.data.model.notification.getNotificationCount.NotificationCount;
import com.reda.yehia.bloodbankv2.data.model.notificationSettings.NotificationSettings;
import com.reda.yehia.bloodbankv2.data.model.post.postToggleFavourite.PostToggleFavourite;
import com.reda.yehia.bloodbankv2.data.model.post.posts.Posts;
import com.reda.yehia.bloodbankv2.data.model.resetpassword.ResetPassword;
import com.reda.yehia.bloodbankv2.data.model.setting.Setting;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("blood-types")
    Call<GeneralResponse> getBloodTypes();

    @GET("governorates")
    Call<GeneralResponse> getGovernorates();

    @GET("cities")
    Call<GeneralResponse> getCities(@Query("governorate_id") int governorateId);

    @GET("categories")
    Call<GeneralResponse> getCategories();

    @POST("login")
    @FormUrlEncoded
    Call<Client> onLogin(@Field("phone") String phone,
                         @Field("password") String password);

    @POST("signup")
    @FormUrlEncoded
    Call<Client> onSignUp(@Field("name") String name,
                          @Field("email") String email,
                          @Field("birth_date") String birth_date,
                          @Field("city_id") int cityId,
                          @Field("phone") String phone,
                          @Field("donation_last_date") String donationLastDate,
                          @Field("password") String password,
                          @Field("password_confirmation") String passwordConfirmation,
                          @Field("blood_type_id") int bloodTypeId);

    @POST("profile")
    @FormUrlEncoded
    Call<Client> editClientData(@Field("name") String name,
                                @Field("email") String email,
                                @Field("birth_date") String birth_date,
                                @Field("city_id") int cityId,
                                @Field("phone") String phone,
                                @Field("donation_last_date") String donationLastDate,
                                @Field("password") String password,
                                @Field("password_confirmation") String passwordConfirmation,
                                @Field("blood_type_id") int bloodTypeId,
                                @Field("api_token") String apiToken);

    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPassword> forgetPassword(@Field("phone") String phone);

    @POST("new-password")
    @FormUrlEncoded
    Call<GeneralResponse> newPassword(@Field("pin_code") String pin_code,
                                      @Field("password") String password,
                                      @Field("password_confirmation") String password_confirmation,
                                      @Field("phone") String phone);

    @GET("donation-requests")
    Call<DonationRequests> getDonationRequests(@Query("api_token") String apiToken,
                                               @Query("page") int page);

    @GET("donation-requests")
    Call<DonationRequests> getDonationRequests(@Query("api_token") String apiToken,
                                               @Query("page") int page,
                                               @Query("blood_type_id") int bloodTypeId,
                                               @Query("city_id") int cityId);

    @POST("profile")
    @FormUrlEncoded
    Call<CreateNewDonation> createNewDonation(@Field("api_token") String apiToken,
                                              @Field("patient_name") String patientName,
                                              @Field("patient_age") String patientAge,
                                              @Field("blood_type_id") int bloodTypeId,
                                              @Field("bags_num") String bagsNum,
                                              @Field("hospital_name") String hospitalName,
                                              @Field("hospital_address") String hospitalAddress,
                                              @Field("city_id") int cityId,
                                              @Field("phone") String phone,
                                              @Field("notes") String notes,
                                              @Field("latitude") double latitude,
                                              @Field("longitude") double longitude);

    @GET("donation-request")
    Call<CreateNewDonation> getDonationRequestData(@Query("api_token") String apiToken,
                                                  @Query("donation_id") int donationId);

    @GET("notifications")
    Call<Notification> getNotifications(@Query("api_token") String apiToken,
                                        @Query("page") int page);

    @GET("notifications-count")
    Call<NotificationCount> getNotificationsCounter(@Query("api_token") String apiToken);

    @GET("posts")
    Call<Posts> getPosts(@Query("api_token") String api_token,
                         @Query("page") int page);

    @GET("posts")
    Call<Posts> getPostFilter(@Query("api_token") String api_token,
                              @Query("keyword") String keyword,
                              @Query("page") int page,
                              @Query("category_id") int category_id);

    @GET("my-favourites")
    Call<Posts> getFavouritesList(@Query("api_token") String api_token,
                                  @Query("page") int page);

    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<PostToggleFavourite> getPostToggleFavourite(@Field("post_id") int post_id,
                                                     @Field("api_token") String api_token);

    @POST("contact")
    @FormUrlEncoded
    Call<ContactUs> contactUs(@Field("title") String title,
                              @Field("message") String message,
                              @Field("api_token") String api_token);

    @GET("settings")
    Call<Setting> getSettings(@Query("api_token") String api_token);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationSettings> getNotificationSettings(@Field("api_token") String api_tokens);
    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationSettings> setNotificationSettings(@Field("api_token") String api_token,
                                                       @Field("governorates[]") List<Integer> governorates,
                                                       @Field("blood_types[]") List<Integer> blood_types);
}
