package com.example.graduationproject.network

import com.example.graduationproject.api.category.CategoryJson
import com.example.graduationproject.api.donor.DonorJson
import com.example.graduationproject.donor.models.Donor
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import okhttp3.RequestBody

import okhttp3.ResponseBody

import retrofit2.http.POST

import retrofit2.http.Multipart

import android.R.id
import android.database.Observable
import retrofit2.Response


interface ApiRequests {

//    @GET("guest/category2")
//    fun getCategories(): Call<CategoryJson>

//    @Multipart
//    @POST("donor/register")
//    fun donorRegister(@Part ("user_id") RequestBody id,@Part imagePart: MultipartBody.Part): Call<DonorJson>
//


    @POST("donor/register")
    fun donorRegister(@Body body: RequestBody): Call<DonorJson>
//    @POST("/WS/userLogin")
//    fun logIn(@Body user: LoginUser): Call<LoginUserJson>
//
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