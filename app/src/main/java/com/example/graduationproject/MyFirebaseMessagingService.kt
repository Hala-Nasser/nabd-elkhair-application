package com.example.graduationproject

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import androidx.annotation.NonNull
import com.example.graduationproject.api.fcm.FCM
import com.example.graduationproject.api.fcm.fcmJson
import com.example.graduationproject.network.RetrofitInstance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("TAG", "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.

        //sendRegistrationToServer(token)
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
            val user_id = ref.getInt("user_id",0)

            addFcm(user_id, token)
        // api بيخزن التوكن في بيانات اليوزر بناء على الايدي الي حاخدو من الشيرد بريفرينس الي بالرجستر او اللوقن

        })
    }


    fun addFcm(user_id:Int, fcm:String) {

        Log.e("hala", "enter")

        val retrofitInstance =
            RetrofitInstance.create()
        Log.e("hala user_id", user_id.toString())
        Log.e("hala fcm", fcm)
        val response = retrofitInstance.fcmToken(user_id, fcm)

        response.enqueue(object : Callback<fcmJson> {
            override fun onResponse(call: Call<fcmJson>, response: Response<fcmJson>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("TAG", data.toString())
                }
                else{
                    Log.e("errorBody", response.message())
                }

            }

            override fun onFailure(call: Call<fcmJson>, t: Throwable) {
                Log.e("TAG", t.message!!)
            }
        })

    }


}