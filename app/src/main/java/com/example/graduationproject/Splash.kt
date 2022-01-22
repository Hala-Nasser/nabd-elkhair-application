package com.example.graduationproject

import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.LuncherManager
import java.lang.Exception
import android.widget.Toast
import androidx.annotation.MainThread
import kotlin.system.exitProcess


class Splash : AppCompatActivity() {

    lateinit var progressBar: ProgressBar
    lateinit var luncherManager: LuncherManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        luncherManager = LuncherManager(this)

        progressBar = findViewById(R.id.progress_bar)

        // Start lengthy operation in a background thread

        Thread {
            doWork()
           // startApp()
            finish()
        }.start()
    }

    private fun doWork() {
        var progress = 0
        while (progress < 92) {
            try {
                Thread.sleep(100)
                progressBar.progress = progress
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("splash_progress", e.message!!)
            }
            progress += 1

            if (progress == 91){
                runOnUiThread {
                    startApp()                }
            }
        }
    }

    private fun startApp() {
        if (luncherManager.isFirstTime()) {
            luncherManager.setFirstLunch(false)
        runOnUiThread(Runnable {
        runOnUiThread {
            GeneralChanges().prepareFadeTransition(this, OnBoardingActivity())
        }
        })
        } else {
            runOnUiThread(Runnable {
            GeneralChanges().prepareFadeTransition(this, ChoiceActivity())
        })
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        exitProcess(1)
    }
}