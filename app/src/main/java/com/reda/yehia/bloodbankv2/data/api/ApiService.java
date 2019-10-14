package com.reda.yehia.bloodbankv2.data.api;


import com.reda.yehia.bloodbankv2.data.model.client.Client;
import com.reda.yehia.bloodbankv2.data.model.donationRequests.DonationRequests;
import com.reda.yehia.bloodbankv2.data.model.generalResponse.GeneralResponse;

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

    @POST("")
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

    @POST("")
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

    @GET("donation-requests")
    Call<DonationRequests> getDonationRequests(@Query("api_token") String apiToken,
                                               @Query("page") int page);

    @GET("donation-requests")
    Call<DonationRequests> getDonationRequests(@Query("api_token") String apiToken,
                                               @Query("page") int page,
                                               @Query("blood_type_id") int bloodTypeId,
                                               @Query("city_id") int cityId);

}
