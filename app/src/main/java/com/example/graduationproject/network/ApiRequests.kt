package com.example.graduationproject.network


import com.example.graduationproject.api.charityApi.CampaignDonation.CampaignDonationJson
import com.example.graduationproject.api.charityApi.campaign.CampaignJson
import com.example.graduationproject.api.charityApi.donation.DonationJson
import com.example.graduationproject.api.charityApi.donationCount.DonationCountJson
import com.example.graduationproject.api.donorApi.addDonation.AddDonationJson
import com.example.graduationproject.api.donorApi.profile.ProfileJson
import com.example.graduationproject.api.donorApi.campaignAccordingToDonationType.CampaignsDonationTypeJson
import com.example.graduationproject.api.donorApi.campaignsAccordingToCharity.CampaignsCharityJson
import com.example.graduationproject.api.donorApi.changePassword.ChangePasswordJson
import com.example.graduationproject.api.donorApi.charities.CharitiesJson
import com.example.graduationproject.api.donorApi.complaint.ComplaintJson
import com.example.graduationproject.api.donorApi.donationType.DonationTypeJson
import com.example.graduationproject.api.donorApi.fcm.FCMJson
import com.example.graduationproject.api.charityApi.fcm.FCMJson as CharityFCMJSon
import com.example.graduationproject.api.donorApi.forgotPassword.ForgotPasswordJson
import com.example.graduationproject.api.donorApi.login.LoginJson
import com.example.graduationproject.api.donorApi.logout.LogoutJson
import com.example.graduationproject.api.donorApi.mydonations.MyDonationJson
import com.example.graduationproject.api.donorApi.notifications.NotificationJson
import com.example.graduationproject.api.donorApi.paymentLinks.PaymentLinksJson
import com.example.graduationproject.api.charityApi.login.LoginJson as CharityLoginJson
import com.example.graduationproject.api.donorApi.register.RegisterJson
import com.example.graduationproject.api.charityApi.register.RegisterJson as CharityRegisterJson
import com.example.graduationproject.api.donorApi.resetPassword.ResetPasswordJson
import com.example.graduationproject.api.donorApi.staticPages.StaticPagesJson
import com.example.graduationproject.api.donorApi.updateProfile.UpdateProfileJson
import retrofit2.Call
import retrofit2.http.*
import okhttp3.RequestBody

import retrofit2.http.POST


interface ApiRequests {

    @POST("donor/register")
    fun donorRegister(@Body body: RequestBody): Call<RegisterJson>

    @POST("donor/login")
    fun logIn(@Body body: RequestBody): Call<LoginJson>

    @POST("donor/store/fcm")
    fun fcmToken(
        @Query("user_id") userId: Int,
        @Query("fcm") fcm: String
    ): Call<FCMJson>

    @POST("donor/forgotPassword")
    fun forgotPassword(@Query("email") email: String): Call<ForgotPasswordJson>

    @POST("donor/resetPassword")
    fun resetPassword(
        @Query("token") token: String,
        @Query("password") password: String,
        @Query("password_confirmation") password_confirmation: String
    ): Call<ResetPasswordJson>

    @POST("donor/changePassword")
    fun changePassword(
        @Header("Authorization") token: String,
        @Query("password") password: String,
        @Query("new_password") new_password: String,
        @Query("new_password_confirmation") new_password_confirmation: String
    ): Call<ChangePasswordJson>

    @GET("donor/donationtype")
    fun getDonationType(): Call<DonationTypeJson>

    @GET("donor/CampaignsAccordingToDonationType/{donation_type}")
    fun getCampaignsAccordingToDonationType(@Path("donation_type") donation_type: Int): Call<CampaignsDonationTypeJson>

    @GET("donor/static/{id}")
    fun getStaticPages(@Path("id") id: Int): Call<StaticPagesJson>

    @GET("donor/charities")
    fun getCharities(): Call<CharitiesJson>

    @GET("donor/notifications/{reciever_id}")
    fun getNotifications(@Path("reciever_id") reciever_id: Int): Call<NotificationJson>

    @GET("donor/profile/{id}")
    fun profile(@Path("id") id: Int): Call<ProfileJson>

    @POST("donor/profile/update")
    fun updateProfile(@Body body: RequestBody): Call<UpdateProfileJson>

    @GET("donor/CampaignsAccordingToCharity/{charity}")
    fun getCampaignsAccordingToCharity(@Path("charity") charity: Int): Call<CampaignsCharityJson>

    @GET("donor/paymentLink/{charity_id}")
    fun getPaymentLinks(@Path("charity_id") charity_id: Int): Call<PaymentLinksJson>

    @POST("donor/enable/{id}")
    fun enableNotification(@Path("id") id: Int): Call<ProfileJson>

    @POST("donor/disable/{id}")
    fun disableNotification(@Path("id") id: Int): Call<ProfileJson>

    @GET("donor/logout")
    fun logout(@Header("Authorization") token: String): Call<LogoutJson>

    @POST("donor/addcomplaint")
    fun addComplaint(
        @Body body: RequestBody,
        @Header("Authorization") token: String
    ): Call<ForgotPasswordJson>

    @POST("donor/addDonation")
    fun addDonation(
        @Body body: RequestBody,
        @Header("Authorization") token: String
    ): Call<AddDonationJson>

    @GET("donor/mydonation/{id}/{donation_type}")
    fun getMyDonations(
        @Path("id") id: Int,
        @Path("donation_type") donation_type: Int
    ): Call<MyDonationJson>

    @GET("donor/charity/search/{keyword}")
    fun searchCharity(@Path("keyword") keyword: String): Call<CharitiesJson>

    // --------------------------------------------------------------------------------------------------

    @POST("charity/register")
    fun charityRegister(
        @Body body: RequestBody
    ): Call<CharityRegisterJson>

    @POST("charity/login")
    fun charityLogIn(@Body body: RequestBody): Call<CharityLoginJson>

    @POST("charity/store/fcm")
    fun charityFcmToken(
        @Query("user_id") userId: Int,
        @Query("fcm") fcm: String
    ): Call<CharityFCMJSon>

    @POST("charity/forgotPassword")
    fun charityForgotPassword(@Query("email") email: String): Call<ForgotPasswordJson>

    @POST("charity/resetPassword")
    fun charityResetPassword(
        @Query("token") token: String,
        @Query("password") password: String,
        @Query("password_confirmation") password_confirmation: String
    ): Call<ResetPasswordJson>

    @POST("charity/changePassword")
    fun charityChangePassword(
        @Header("Authorization") token: String,
        @Query("password") password: String,
        @Query("new_password") new_password: String,
        @Query("new_password_confirmation") new_password_confirmation: String
    ): Call<ChangePasswordJson>


    @GET("charity/getCharity")
    fun charityProfile(@Header("Authorization") token: String): Call<CharityFCMJSon>

    @GET("charity/getDonationTypes")
    fun getGeneralDonationType(): Call<DonationTypeJson>

    @GET("charity/getCharityDonationTypes/{id}")
    fun getCharityDonationTypes(@Path("id") id: Int): Call<DonationTypeJson>

    @POST("charity/updateProfile")
    fun charityUpdateProfile(
        @Header("Authorization") token: String,
        @Body body: RequestBody
    ): Call<CharityFCMJSon>

    @GET("charity/logout")
    fun charityLogout(@Header("Authorization") token: String): Call<LogoutJson>

    @POST("charity/addPaymentLinks")
    fun addPaymentLinks(
        @Query("charity_id") id: Int,
        @Query("paypal_link") paypal_link: String,
        @Query("visa_link") visa_link: String,
        @Query("creditcard_link") creditCard_link: String
    ): Call<PaymentLinksJson>

    @GET("charity/getPaymentLinks")
    fun getPaymentLinks(@Header("Authorization") token: String): Call<PaymentLinksJson>


    @POST("charity/updatePaymentLinks")
    fun updatePaymentLinks(
        @Header("Authorization") token: String,
        @Body body: RequestBody
    ): Call<PaymentLinksJson>

    @POST("charity/addCampaign")
    fun addCampaign(
        @Header("Authorization") token: String,
        @Body body: RequestBody
    ): Call<CampaignJson>

    @GET("charity/getNotifications/{reciever_id}")
    fun getCharityNotifications(@Path("reciever_id") reciever_id: Int): Call<NotificationJson>

    @POST("charity/setNotificationStatus")
    fun setNotificationStatus(
        @Header("Authorization") token: String,
        @Query("notification_status") notification_status: Int
    ): Call<ForgotPasswordJson>


    @GET("charity/getDonationRequests")
    fun getDonationRequests(@Header("Authorization") token: String): Call<DonationJson>

    @GET("charity/getDonationReceived")
    fun getDonationReceived(@Header("Authorization") token: String): Call<DonationJson>

    @GET("charity/getDonationNotReceived")
    fun getDonationNotReceived(@Header("Authorization") token: String): Call<DonationJson>

    @GET("charity/getDonationsCount")
    fun getDonationsCount(@Header("Authorization") token: String): Call<DonationCountJson>

    @GET("charity/getCampaignDonations")
    fun getCampaignDonations(@Header("Authorization") token: String): Call<CampaignDonationJson>

    @GET("charity/getWithoutCampaignDonations")
    fun getWithoutCampaignDonations(@Header("Authorization") token: String): Call<DonationJson>

    @POST("charity/setDonationReceived")
    fun setDonationReceived(
        @Header("Authorization") token: String,
        @Query("donation_id") donation_id: Int,
        @Query("received") received: Int
    ): Call<ForgotPasswordJson>

    @POST("charity/setDonationAcceptance")
    fun setDonationAcceptance(
        @Header("Authorization") token: String,
        @Query("donation_id") donation_id: Int,
        @Query("acceptance") acceptance: Int
    ): Call<ForgotPasswordJson>


    @Headers("Accept: application/json")
    @GET("charity/CampaignsAccordingToDonationType/{donation_type}")
    fun getCharityCampaignsAccordingToDonationType(
        @Header("Authorization") token: String,
        @Path("donation_type") donation_type: Int
    ): Call<CampaignsDonationTypeJson>

    @POST("charity/updateCampaign")
    fun updateCampaign(
        @Header("Authorization") token: String,
        @Query("campaign_id") campaign_id: Int,
        @Query("expiry_date") date: String,
        @Query("expiry_time") time: String
    ): Call<CampaignJson>

    @POST("charity/addComplaint")
    fun charityAddComplaint(
        @Body body: RequestBody,
        @Header("Authorization") token: String
    ): Call<ForgotPasswordJson>

    @GET("charity/getComplaints")
    fun getComplaints(@Header("Authorization") token: String): Call<ComplaintJson>

}