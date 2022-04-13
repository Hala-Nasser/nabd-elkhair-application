package com.example.graduationproject.donor

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import com.example.graduationproject.R
import com.example.graduationproject.classes.GeneralChanges

class CompleteSignUpActivity : AppCompatActivity() {
    val PICK_IMAGE_REQUEST = 111
    var imageURI: Uri? = null
    lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_sign_up)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        image = findViewById(R.id.image)

        findViewById<AppCompatButton>(R.id.sign_up).setOnClickListener {
            // api لفحص الرجستر و التخزين
            // بدي اخد منها ايدي اليوزر
            // بدي اخزن الايدي بالشيرد بريفيرنس
            GeneralChanges().prepareFadeTransition(this, DonorMainActivity())
            finish()
        }

        findViewById<CardView>(R.id.choose_image).setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
            Thread.sleep(1000)
            image.layoutParams.height = MATCH_PARENT
            image.layoutParams.width = MATCH_PARENT
            val param = image.layoutParams as ViewGroup.MarginLayoutParams
            image.layoutParams = param
        }

        findViewById<ImageView>(R.id.back).setOnClickListener {
            onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageURI = data!!.data
            Log.e("TAG", imageURI.toString())
            image.setImageURI(imageURI)
        }
    }
}