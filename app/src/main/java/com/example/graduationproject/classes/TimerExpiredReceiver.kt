package com.example.graduationproject.classes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.graduationproject.charity.fragments.DonationRequestsFragment

class TimerExpiredReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //TODO: show notification

        PrefUtil.setTimerState(DonationRequestsFragment.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)
    }
}