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
import android.widget.AutoCompleteTextView


class Validation {

    fun validateUsername(usernameEt: TextInputEditText, usernameTL: TextInputLayout, view: View): Boolean {
        val username = usernameEt.text.toString()
        if (username.trim().isNullOrEmpty()) {
            //showSnackBar(view, "اسم المستخدم مطلوب")
            usernameTL.error = "اسم المستخدم مطلوب"
            usernameTL.errorIconDrawable = null
            usernameTL.isFocusable = true
            return false
        } else if (username.length > 25) {
            //showSnackBar(view, "اسم المستخدم طويل جدا")
            usernameTL.error = "اسم المستخدم طويل جدا"
            usernameTL.errorIconDrawable = null
            usernameTL.isFocusable = true
            return false
        }
        try {
            var intValue = username.toInt()
            //showSnackBar(view, "يجب أن يحتوي اسم المستخدم على حروف")
            usernameTL.error = "يجب أن يحتوي اسم المستخدم على حروف"
            usernameTL.errorIconDrawable = null
            usernameTL.isFocusable = true
            return false
        } catch (e: NumberFormatException) {
            Log.i("username", " is not number")
        }
        usernameTL.error = null
        usernameTL.isFocusable = false
        usernameTL.isErrorEnabled = false
        return true

    }

    fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun validateEmail(emailEt: TextInputEditText, emailTL:TextInputLayout, view: View): Boolean {
        val email = emailEt.text.toString()
        return if (email.trim().isEmpty()) {
            //showSnackBar(view, "البريد الالكتروني مطلوب")
            emailTL.error = "البريد الالكتروني مطلوب"
            emailTL.errorIconDrawable = null
            emailTL.isFocusable = true
            false
        } else if (!email.isValidEmail()) {
            //showSnackBar(view, "البريد الالكتروني غير صحيح")
            emailTL.error = "البريد الالكتروني غير صحيح"
            emailTL.errorIconDrawable = null
            emailTL.isFocusable = true
            false
        } else {
            emailTL.error = null
            emailTL.isFocusable = false
            emailTL.isErrorEnabled = false
            true
        }
    }

//    fun validCellPhone(number: String?): Boolean {
//        return Patterns.PHONE.matcher(number).matches()
//    }

    fun CharSequence?.isValidPhone() = !isNullOrEmpty() && Patterns.PHONE.matcher(this).matches()

    fun validatePhoneNumber(phoneEt: TextInputEditText, phoneTL:TextInputLayout, view: View): Boolean {
        val phone = phoneEt.text.toString()
       // val checkPhone = "^\\+[0-9]{10,13}\$"
        return if (phone.trim().isEmpty()) {
            //showSnackBar(view, "رقم الهاتف مطلوب")
            phoneTL.error = "رقم الهاتف مطلوب"
            phoneTL.errorIconDrawable = null
            phoneTL.isFocusable = true
            false
        } else if (phone.length > 13 || phone.length < 10) {
            //showSnackBar(view, "رقم الهاتف غير صحيح")
            phoneTL.error = "رقم الهاتف غير صحيح"
            phoneTL.errorIconDrawable = null
            phoneTL.isFocusable = true
            false
        } else {
            phoneTL.error = null
            phoneTL.isFocusable = false
            phoneTL.isErrorEnabled = false
            true
        }
    }

    fun validatePassword(passwordEt: TextInputEditText, passwordTL:TextInputLayout, view: View): Boolean {
        val password = passwordEt.text.toString()

        return if (password.trim().isEmpty()) {
            //showSnackBar(view, "كلمة المرور مطلوبة")
            passwordTL.error = "كلمة المرور مطلوبة"
            passwordTL.errorIconDrawable = null
            passwordTL.isFocusable = true
            false
        } else if (password.length < 8) {
            //showSnackBar(view, "يجب أن تكون كلمة المرور 8 خانات على الأقل")
            passwordTL.error = "يجب أن تكون كلمة المرور 8 خانات على الأقل"
            passwordTL.errorIconDrawable = null
            passwordTL.isFocusable = true
            false
        } else {
            passwordTL.error = null
            passwordTL.isFocusable = false
            passwordTL.isErrorEnabled = false
            true
        }
    }

    fun validateConfirmPassword(c_passwordEt: TextInputEditText, c_passwordTL:TextInputLayout, view: View, password:String): Boolean {
        val c_password = c_passwordEt.text.toString()

        return if (c_password.trim().isEmpty()) {
            //showSnackBar(view, "تأكيد كلمة المرور مطلوبة")
            c_passwordTL.error = "تأكيد كلمة المرور مطلوبة"
            c_passwordTL.errorIconDrawable = null
            c_passwordTL.isFocusable = true
            false
        } else if (c_password != password) {
            //showSnackBar(view, "كلمتا المرور غير متطابقتين")
            c_passwordTL.error = "كلمتا المرور غير متطابقتين"
            c_passwordTL.errorIconDrawable = null
            c_passwordTL.isFocusable = true
            false
        } else {
            c_passwordTL.error = null
            c_passwordTL.errorIconDrawable = null
            c_passwordTL.isFocusable = false
            true
        }
    }

    fun validateLocation(auto_complete: AutoCompleteTextView, locationTL:TextInputLayout): Boolean {
        val location = auto_complete.text.toString()

        return if (location.trim().isEmpty()) {
            locationTL.error = "ٌقم باختيار موقعك"
            locationTL.errorIconDrawable = null
            locationTL.isFocusable = true
            false
        } else {
            locationTL.error = null
            locationTL.errorIconDrawable = null
            locationTL.isFocusable = false
            true
        }
    }

    fun showSnackBar(view: View, message:String){
        val snackBarView = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val view = snackBarView.view
        view.setBackgroundColor(Color.argb(180,255,0,46))
        val snackBarTextView = view.findViewById<TextView>(R.id.snackbar_text)
        snackBarTextView.textSize = 20f
        snackBarTextView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
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