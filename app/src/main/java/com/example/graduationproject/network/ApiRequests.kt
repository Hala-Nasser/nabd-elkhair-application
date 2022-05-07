package com.example.graduationproject.network

import android.database.Observable
import com.example.graduationproject.api.donorApi.campaignAccordingToDonationType.CampaignsDonationTypeJson
import com.example.graduationproject.api.donorApi.changePassword.ChangePasswordJson
import com.example.graduationproject.api.donorApi.charities.CharitiesJson
import com.example.graduationproject.api.donorApi.donationType.DonationTypeJson
import com.example.graduationproject.api.donorApi.fcm.FCMJson
import com.example.graduationproject.api.donorApi.forgotPassword.ForgotPasswordJson
import com.example.graduationproject.api.donorApi.login.LoginJson
import com.example.graduationproject.api.donorApi.register.RegisterJson
import com.example.graduationproject.api.donorApi.resetPassword.ResetPasswordJson
import com.example.graduationproject.api.donorApi.staticPages.StaticPagesJson
import retrofit2.Call
import retrofit2.http.*
import okhttp3.RequestBody
import retrofit2.Response

import retrofit2.http.POST


interface ApiRequests {

    @POST("donor/register")
    fun donorRegister(@Body body: RequestBody): Call<RegisterJson>

    @POST("donor/login")
    fun logIn(@Body body: RequestBody): Call<LoginJson>

    @POST("donor/store/fcm")
    fun fcmToken(@Query("user_id") userId: Int,
        @Query("fcm") fcm: String): Call<FCMJson>

    @POST("donor/forgotPassword")
    fun forgotPassword(@Query("email") email: String): Call<ForgotPasswordJson>

    @POST("donor/resetPassword")
    fun resetPassword(@Query("token") token: String, @Query("password") password: String, @Query("password_confirmation") password_confirmation: String): Call<ResetPasswordJson>

    @POST("donor/changePassword")
    fun changePassword(@Header("Authorization") token: String, @Query("password") password: String,  @Query("new_password") new_password: String, @Query("new_password_confirmation") new_password_confirmation: String): Call<ChangePasswordJson>

    @GET("donor/donationtype")
    fun getDonationType(): Call<DonationTypeJson>

    @GET("donor/CampaignsAccordingToDonationType/{donation_type}")
    fun getCampaignsAccordingToDonationType(@Path("donation_type") donation_type: Int): Call<CampaignsDonationTypeJson>

    @GET("donor/static/{id}")
    fun getStaticPages(@Path("id") id: Int): Call<StaticPagesJson>

    @GET("donor/charities")
    fun getCharities(): Call<CharitiesJson>

//    @FormUrlEncoded
//    @POST("/WS/editProfile")
//    fun editProfile(
//        @Field("id") id: Int,
//        @Field("name") name: String,
//        @Field("email") email: String,
//        @Field("username") username: String,
//        @Field("age") age: Int,
//        @Field("gender") gender: String,
//        @Header("token") token: String
//    ): Call<ProfileJson>
//
//    @GET("/WS/Favorite")
//    fun getFavoriteStories(
//        @Query("user_id") userId: Int,
//        @Header("token") token: String
//    ): Call<FavoriteStoriesJson>
//
//    @GET("/WS/getGames")
//    fun getMyStories(
//        @Query("user_id") userId: Int,
//        @Header("token") token: String
//    ): Call<FavoriteStoriesJson>
//
//    @GET("/WS/AddFavorite")
//    fun addFavorite(
//        @Query("user_id") userId: Int,
//        @Query("story_id") storyId: Int,
//        @Header("token") token: String
//    ): Call<AddRemoveFavoriteJson>
//
//    @GET("/WS/RemoveFavorite")
//    fun removeFavorite(
//        @Query("user_id") userId: Int,
//        @Query("story_id") storyId: Int,
//        @Header("token") token: String
//    ): Call<AddRemoveFavoriteJson>
//
//    @GET("/WS/getUserData")
//    fun getUserData(
//        @Query("user_id") userId: Int,
//        @Header("token") token: String
//    ): Call<getUserDetailsJson>
//
//    @GET("/WS/ForgetPassword")
//    fun forgetPassword(
//        @Query("email") email: String,
//    ): Call<forgetResetPasswordJson>
//
//    @GET("/WS/ResetPassword")
//    fun resetPassword(
//        @Query("email") email: String,
//        @Query("code") code: String,
//        @Query("newpass") newpass: String
//    ): Call<forgetResetPasswordJson>
//
//    @GET("/WS/QA/{story_id}")
//    fun getPages(
//        @Path("story_id") storyId: Int,
//        @Header("token") token: String
//    ): Call<StoryContentJson>
//
//    @GET("/WS/UpdateViews/{story_id}")
//    fun updateViews(
//        @Path("story_id") storyId: Int,
//        @Header("token") token: String
//    ): Call<forgetResetPasswordJson>
//
//    @GET("/WS/setStoryEndData")
//    fun setStoryEnd(
//        @Query("user_id") userId: Int,
//        @Query("story_id") storyId: Int,
//        @Query("finish_date") date: String,
//        @Header("token") token: String,
//    ): Call<forgetResetPasswordJson>
//
//    @GET("/WS/setGameEnd")
//    fun setGameEnd(
//        @Query("user_id") userId: Int,
//        @Query("story_id") storyId: Int,
//        @Header("token") token: String,
//    ): Call<forgetResetPasswordJson>
}