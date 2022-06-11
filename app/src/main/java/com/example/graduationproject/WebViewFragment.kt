package com.example.graduationproject

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import kotlinx.android.synthetic.main.fragment_web_view.view.*
import java.lang.Exception

class WebViewFragment : Fragment() {
    var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_web_view, container, false)

        val b = arguments
        if (b != null) {
            var link = b.getString("link")

            root.webView.webViewClient = WebViewClient()
            root.webView.loadUrl(link!!)
            root.webView.settings.javaScriptEnabled = true
            root.webView.settings.setSupportZoom(true)
        }

        return root
    }

    inner class WebViewClient : android.webkit.WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            Log.e("should override", "url loading")
            view.loadUrl(url)
            return false
        }

        override fun onLoadResource(view: WebView?, url: String?) {
            if (progressDialog == null) {
                progressDialog = ProgressDialog(activity)
                progressDialog!!.setMessage("جاري التحميل ...")
                progressDialog!!.show()
            }
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            try {
                if (progressDialog!!.isShowing()) {
                    progressDialog!!.dismiss()
                    progressDialog = null
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

}