package com.example.graduationproject.classes

import com.google.android.material.textfield.TextInputEditText
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
import android.widget.AutoCompleteTextView


class Validation {

    fun validateUsername(usernameEt: TextInputEditText, usernameTL: TextInputLayout): Boolean {
        val username = usernameEt.text.toString()
        if (username.trim().isNullOrEmpty()) {
            usernameTL.error = "اسم المستخدم مطلوب"
            usernameTL.errorIconDrawable = null
            usernameTL.isFocusable = true
            return false
        } else if (username.length > 25) {
            usernameTL.error = "اسم المستخدم طويل جدا"
            usernameTL.errorIconDrawable = null
            usernameTL.isFocusable = true
            return false
        }
        try {
            var intValue = username.toInt()
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

    fun validateEmail(emailEt: TextInputEditText, emailTL:TextInputLayout): Boolean {
        val email = emailEt.text.toString()
        return if (email.trim().isEmpty()) {
            emailTL.error = "البريد الالكتروني مطلوب"
            emailTL.errorIconDrawable = null
            emailTL.isFocusable = true
            false
        } else if (!email.isValidEmail()) {
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

    fun validatePhoneNumber(phoneEt: TextInputEditText, phoneTL:TextInputLayout): Boolean {
        val phone = phoneEt.text.toString()
        return if (phone.trim().isEmpty()) {
            phoneTL.error = "رقم الهاتف مطلوب"
            phoneTL.errorIconDrawable = null
            phoneTL.isFocusable = true
            false
        } else if (phone.length > 13 || phone.length < 10) {
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

    fun validatePassword(passwordEt: TextInputEditText, passwordTL:TextInputLayout): Boolean {
        val password = passwordEt.text.toString()

        return if (password.trim().isEmpty()) {
            passwordTL.error = "كلمة المرور مطلوبة"
            passwordTL.errorIconDrawable = null
            passwordTL.isFocusable = true
            false
        } else if (password.length < 8) {
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

    fun validateConfirmPassword(c_passwordEt: TextInputEditText, c_passwordTL:TextInputLayout, password:String): Boolean {
        val c_password = c_passwordEt.text.toString()

        return if (c_password.trim().isEmpty()) {
            c_passwordTL.error = "تأكيد كلمة المرور مطلوبة"
            c_passwordTL.errorIconDrawable = null
            c_passwordTL.isFocusable = true
            false
        } else if (c_password != password) {
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

    fun validateCode(codeEt: TextInputEditText, codeTL:TextInputLayout): Boolean {
        val code = codeEt.text.toString()

        return if (code.trim().isEmpty()) {
            codeTL.error = "رمز التحقق مطلوب"
            codeTL.errorIconDrawable = null
            codeTL.isFocusable = true
            false
        } else if (code.length != 10) {
            codeTL.error = "قم بإدخال رمز تحقق صحيح"
            codeTL.errorIconDrawable = null
            codeTL.isFocusable = true
            false
        } else {
            codeTL.error = null
            codeTL.isFocusable = false
            codeTL.isErrorEnabled = false
            true
        }
    }


    fun validateAboutCharity(codeEt: TextInputEditText, codeTL:TextInputLayout): Boolean {
        val code = codeEt.text.toString()

        return if (code.trim().isEmpty()) {
            codeTL.error = "يرجى إدخال تفاصيل الجمعية"
            codeTL.errorIconDrawable = null
            codeTL.isFocusable = true
            false
        } else {
            codeTL.error = null
            codeTL.isFocusable = false
            codeTL.isErrorEnabled = false
            true
        }
    }

    fun validateAboutCampaign(codeEt: TextInputEditText, codeTL:TextInputLayout): Boolean {
        val code = codeEt.text.toString()

        return if (code.trim().isEmpty()) {
            codeTL.error = "يرجى إدخال تفاصيل الحملة"
            codeTL.errorIconDrawable = null
            codeTL.isFocusable = true
            false
        } else {
            codeTL.error = null
            codeTL.isFocusable = false
            codeTL.isErrorEnabled = false
            true
        }
    }

    fun validateCampaignName(codeEt: TextInputEditText, codeTL:TextInputLayout): Boolean {
        val code = codeEt.text.toString()

        return if (code.trim().isEmpty()) {
            codeTL.error = "يرجى إدخال عنوان الحملة"
            codeTL.errorIconDrawable = null
            codeTL.isFocusable = true
            false
        } else {
            codeTL.error = null
            codeTL.isFocusable = false
            codeTL.isErrorEnabled = false
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