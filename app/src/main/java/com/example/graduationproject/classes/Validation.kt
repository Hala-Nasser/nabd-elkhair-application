package com.example.graduationproject.classes

import android.R.attr
import com.google.android.material.textfield.TextInputEditText
import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.example.graduationproject.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import java.lang.NumberFormatException
import android.R.attr.password
import android.R.attr.phoneNumber








class Validation {

    fun validateUsername(usernameEt: TextInputEditText, usernameTL: TextInputLayout, view: View): Boolean {
        val username = usernameEt.text.toString()
        if (username.trim().isNullOrEmpty()) {
            showSnackBar(view, "اسم المستخدم مطلوب")
            return false
        } else if (username.length > 25) {
            showSnackBar(view, "اسم المستخدم طويل جدا")
            return false
        }
        try {
            var intValue = username.toInt()
            showSnackBar(view, "يجب أن يحتوي اسم المستخدم على حروف")
            return false
        } catch (e: NumberFormatException) {
            Log.i("username", " is not number")
        }
        return true

    }

    fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun validateEmail(emailEt: TextInputEditText, emailTL:TextInputLayout, view: View): Boolean {
        val email = emailEt.text.toString()
        return if (email.trim().isEmpty()) {
            showSnackBar(view, "البريد الالكتروني مطلوب")
            false
        } else if (!email.isValidEmail()) {
            showSnackBar(view, "البريد الالكتروني غير صحيح")
            false
        } else {
            true
        }
    }


    fun validatePhoneNumber(phoneEt: TextInputEditText, passwordTL:TextInputLayout, view: View): Boolean {
        val phone = phoneEt.text.toString()
        val checkspaces = "Aw{1,10}z"
        return if (phone.trim().isEmpty()) {
            showSnackBar(view, "رقم الهاتف مطلوب")
            false
        } else if (!phone.matches(checkspaces.toRegex())) {
            showSnackBar(view, "رقم الهاتف غير صحيح")
            false
        } else {
            true
        }
    }

    fun validatePassword(passwordEt: TextInputEditText, passwordTL:TextInputLayout, view: View): Boolean {
        val password = passwordEt.text.toString()

        return if (password.trim().isEmpty()) {
            showSnackBar(view, "كلمة المرور مطلوبة")
            false
        } else if (password.length < 8) {
            showSnackBar(view, "يجب أن تكون كلمة المرور 8 خانات على الأقل")
            false
        } else {
            true
        }
    }

    fun validateConfirmPassword(c_passwordEt: TextInputEditText, c_passwordTL:TextInputLayout, view: View): Boolean {
        val password = passwordEt.text.toString()

        return if (password.trim().isEmpty()) {
            showSnackBar(view, "كلمة المرور مطلوبة")
            false
        } else if (password.length < 8) {
            showSnackBar(view, "يجب أن تكون كلمة المرور 8 خانات على الأقل")
            false
        } else {
            true
        }
    }

    fun showSnackBar(view: View, message:String){
        val snackBarView = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val view = snackBarView.view
        view.setBackgroundColor(Color.argb(180,255,0,46))
        val snackBarTextView = view.findViewById<TextView>(R.id.snackbar_text)
        snackBarTextView.textSize = 20f
        snackBarTextView.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        snackBarView.setTextColor(Color.WHITE)
        snackBarTextView.maxLines = 2
        val marginTextParam = snackBarTextView.layoutParams as ViewGroup.MarginLayoutParams
        marginTextParam.setMargins(10,10,10,10)
        snackBarTextView.layoutParams = marginTextParam

        val marginSnackParam = view.layoutParams as ViewGroup.MarginLayoutParams
        marginSnackParam.setMargins(0,0,0,0)
        view.layoutParams = marginSnackParam

        val params = view.layoutParams as FrameLayout.LayoutParams
        params.topMargin = 70
        params.gravity = Gravity.TOP
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        view.layoutParams = params

        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()
    }

}