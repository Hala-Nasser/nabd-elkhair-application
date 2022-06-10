package com.example.graduationproject.charity.models

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CounterViewModel: ViewModel() {
    val ONE_SECOND = 1000L
    val COUNTDOWN_TIME = System.currentTimeMillis() + 86400000
    var nowSeconds = System.currentTimeMillis()
    lateinit var timer : CountDownTimer
    val currentTime = MutableLiveData<String>()

    init {
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                nowSeconds -= 1
                var secondsRemaining =  (millisUntilFinished - nowSeconds) / ONE_SECOND
                val hoursLeft = String.format("%d", secondsRemaining % 86400 / 3600)
                val minutesLeft = String.format("%d", secondsRemaining % 86400 % 3600 / 60)
                val secondsLeft = String.format("%d", secondsRemaining % 86400 % 3600 % 60)
                currentTime.value = "$hoursLeft:$minutesLeft:$secondsLeft"
            }

            override fun onFinish() {
                currentTime.value = "DONE"
            }
        }

        timer.start()
    }

}