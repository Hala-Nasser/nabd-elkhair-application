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

class WebViewFragment : Fragment()
    //, IOnBackPressed
{
    var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_web_view, container, false)

        val b = arguments
        if (b != null){
            var link = b.getString("link")
            // WebViewClient allows you to handle
            // onPageFinished and override Url loading.
            root.webView.webViewClient = WebViewClient()

            // this will load the url of the website
            root.webView.loadUrl(link!!)

            // this will enable the javascript settings
            root.webView.settings.javaScriptEnabled = true

            // if you want to enable zoom feature
            root.webView.settings.setSupportZoom(true)
        }

        return root
    }

    // Overriding WebViewClient functions
    inner class WebViewClient : android.webkit.WebViewClient() {

        // Load the URL
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            Log.e("should override", "url loading")
            view.loadUrl(url)
            return false
        }

        override fun onLoadResource(view: WebView?, url: String?) {
            if (progressDialog == null) {
                // in standard case YourActivity.this
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