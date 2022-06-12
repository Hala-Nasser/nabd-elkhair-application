package com.example.graduationproject.classes

import android.app.Activity
import android.app.ActivityOptions
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Parcelable
import android.transition.TransitionInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.example.graduationproject.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class GeneralChanges {

    fun setStatusBarTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.rgb(16, 177, 177)
        }
    }

    fun fadeTransition(activity: Activity) {
        var transition =
            TransitionInflater.from(activity).inflateTransition(R.transition.fade_transition)
        activity.window.enterTransition = transition
    }

    fun prepareFadeTransition(currentActivity: Activity, activity: Activity) {
        var option = ActivityOptions.makeSceneTransitionAnimation(currentActivity)
        var intent = Intent(currentActivity, activity::class.java)
        currentActivity.startActivity(intent, option.toBundle())
    }

    fun prepareFadeTransitionSignIn(currentActivity: Activity, activity: Activity) {
        var option = ActivityOptions.makeSceneTransitionAnimation(currentActivity)
        var intent = Intent(currentActivity, activity::class.java)
        intent.putExtra("previous", "complete sign up")
        currentActivity.startActivity(intent, option.toBundle())
    }

    fun showDialog(progressDialog: ProgressDialog, message: String) {
        progressDialog.setMessage(message)
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    fun hideDialog(progressDialog: ProgressDialog) {
        if (progressDialog.isShowing)
            progressDialog.dismiss()
    }

    fun getFromCalendar(myFormat: String, strDate: String, field: Int): Int {
        var result = -1
        try {
            val locale = Locale("ar", "SA")

            val formatter = SimpleDateFormat(myFormat, locale)
            val date = formatter.parse(strDate) //convert to date
            val cal = Calendar.getInstance() // get calendar instance
            cal.time = date //set the calendar date to your date
            result = cal[field] // get the required field
            return result //return the result.
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return result
    }
}