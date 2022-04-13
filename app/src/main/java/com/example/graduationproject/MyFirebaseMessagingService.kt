package com.example.graduationproject

import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import androidx.annotation.NonNull




class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("TAG", "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.

        //sendRegistrationToServer(token)
    }

    fun retrieveToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("hala", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            var token = task.result
            Log.e("hala", "Token: $token")
            // api بيخزن التوكن في بيانات اليوزر بناء على الايدي الي حاخدو من الشيرد بريفرينس الي بالرجستر او اللوقن
        })
    }


}