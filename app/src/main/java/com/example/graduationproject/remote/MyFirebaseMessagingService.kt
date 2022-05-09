package com.example.graduationproject.remote

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.example.graduationproject.api.donorApi.fcm.FCMJson
import com.example.graduationproject.api.charityApi.fcm.FCMJson as CharityFCMJSon
import com.example.graduationproject.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import com.example.graduationproject.R
import android.app.PendingIntent
import android.graphics.Color
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.graduationproject.donor.DonorMainActivity
import com.google.firebase.messaging.RemoteMessage
import java.text.SimpleDateFormat
import java.util.*
import android.text.format.DateUtils


const val channelId = "notification_channel"
const val channelName = "com.example.graduationproject"
class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.i("DEBUG_TAG", "New token: $s")
    }


    fun retrieveToken(activity: Activity){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("hala", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            var token = task.result
            Log.e("hala", "Token: $token")
            val ref = activity.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
            if (ref.getString("from","") == "donor"){
                val user_id = ref.getInt("user_id",0)
                addFcm(user_id, token)
            }else{
                val user_id = ref.getInt("charity_id",0)
                addFcmToCharity(user_id,token)
            }

        })
    }

    fun addFcmToCharity(user_id:Int, fcm:String) {

        val retrofitInstance =
            RetrofitInstance.create()
        Log.e("charity user_id", user_id.toString())
        Log.e("charity fcm", fcm)
        val response = retrofitInstance.charityFcmToken(user_id, fcm)

        response.enqueue(object : Callback<CharityFCMJSon> {
            override fun onResponse(call: Call<CharityFCMJSon>, response: Response<CharityFCMJSon>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("TAG", data!!.status.toString())
                }
                else{
                    Log.e("errorBody", response.message())
                }

            }

            override fun onFailure(call: Call<CharityFCMJSon>, t: Throwable) {
                Log.e("TAG", t.message!!)
            }
        })

    }

    fun addFcm(user_id:Int, fcm:String) {

        Log.e("hala", "enter")

        val retrofitInstance =
            RetrofitInstance.create()
        Log.e("hala user_id", user_id.toString())
        Log.e("hala fcm", fcm)
        val response = retrofitInstance.fcmToken(user_id, fcm)

        response.enqueue(object : Callback<FCMJson> {
            override fun onResponse(call: Call<FCMJson>, response: Response<FCMJson>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("TAG", data!!.status.toString())
                }
                else{
                    Log.e("errorBody", response.message())
                }

            }

            override fun onFailure(call: Call<FCMJson>, t: Throwable) {
                Log.e("TAG", t.message!!)
            }
        })

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null){
            Log.e("title", remoteMessage.notification!!.title!!)
            generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
        }

    }

    fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView = RemoteViews("com.example.graduationproject", R.layout.notification)
        remoteView.setTextColor(R.id.notification_title, Color.BLACK)
        remoteView.setTextViewText(R.id.notification_title, title)
        remoteView.setTextViewText(R.id.notification_message, message)
        remoteView.setImageViewResource(R.id.notification_app_logo, R.drawable.logo)

        return remoteView
    }

    fun generateNotification(title:String, message:String){
        val intent = Intent(this, DonorMainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)

        }

        notificationManager.notify(0, builder.build())

    }

}