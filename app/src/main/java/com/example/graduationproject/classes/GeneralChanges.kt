package com.example.graduationproject.classes

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Parcelable
import android.transition.TransitionInflater
import android.view.View
import android.view.WindowManager
import com.example.graduationproject.R

class GeneralChanges {

    fun setStatusBarTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.rgb(16,177,177)
        }
    }

    fun fadeTransition(activity: Activity){
        var transition = TransitionInflater.from(activity).inflateTransition(R.transition.fade_transition)
        activity.window.enterTransition = transition
    }

    fun prepareFadeTransitionWithData(currentActivity: Activity, activity: Activity,key:String?,value:String?){
        var option = ActivityOptions.makeSceneTransitionAnimation(currentActivity)
        var intent = Intent(currentActivity, activity::class.java)
        intent.putExtra(key,value)
        currentActivity.startActivity(intent,option.toBundle())
    }

    fun prepareFadeTransition(currentActivity: Activity, activity: Activity){
        var option = ActivityOptions.makeSceneTransitionAnimation(currentActivity)
        var intent = Intent(currentActivity, activity::class.java)
        currentActivity.startActivity(intent,option.toBundle())
    }
}