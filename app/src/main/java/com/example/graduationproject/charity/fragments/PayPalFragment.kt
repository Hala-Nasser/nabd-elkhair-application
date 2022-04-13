package com.example.graduationproject.charity.fragments

import android.content.ClipData
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.ClipboardManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.graduationproject.R
import kotlinx.android.synthetic.main.fragment_pay_pal.*
import kotlinx.android.synthetic.main.fragment_pay_pal.view.*


class PayPalFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_pay_pal, container, false)

        root.copyPin.setOnClickListener {
            val clipboard =
                requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = ClipData.newPlainText("تم نسخ الرسالة", root.payPal_txt.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(),"تم نسخ النص", Toast.LENGTH_SHORT).show()
        }
        return root
    }

}