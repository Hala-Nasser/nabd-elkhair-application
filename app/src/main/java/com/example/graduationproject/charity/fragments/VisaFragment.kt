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
import kotlinx.android.synthetic.main.fragment_pay_pal.view.*
import kotlinx.android.synthetic.main.fragment_visa.view.*


class VisaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_visa, container, false)

        var sharedPref = requireActivity().getSharedPreferences("linksPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("clicked", false)

        var clicked = sharedPref.getBoolean("clicked",false)

        if (clicked) {
            editor.putString("visa", root.visa_txt.text.toString())
            editor.apply()
        }

        root.visaCopyPin.setOnClickListener {
            val clipboard =
                requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = ClipData.newPlainText("تم نسخ الرسالة", root.visa_txt.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(),"تم نسخ النص", Toast.LENGTH_SHORT).show()
        }
        return root
    }

}