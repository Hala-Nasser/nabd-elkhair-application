package com.example.graduationproject.donor.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.graduationproject.R
import com.example.graduationproject.donor.models.DonationType
import com.github.florent37.expansionpanel.ExpansionLayout
import kotlinx.android.synthetic.main.bottom_dialog_item.view.*
import kotlinx.android.synthetic.main.bottom_dialog_item.view.amount
import kotlinx.android.synthetic.main.bottom_dialog_item.view.close
import kotlinx.android.synthetic.main.bottom_dialog_item.view.confirm
import kotlinx.android.synthetic.main.bottom_dialog_item_manual.view.*
import kotlinx.android.synthetic.main.fragment_second_step_view_donation_manual.view.*

class SecondStepViewDonationManualFragment : Fragment() {

    lateinit var dialog :Dialog
    lateinit var v :View
    var previous_fragment = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_second_step_view_donation_manual, container, false)

        val b = arguments
        if (b != null) {
            previous_fragment = b.getString("previous_fragment")!!
        }
        Log.e("previous fragment", previous_fragment)

        if (previous_fragment == "campaignDetails"){
            root.progress_linear.visibility = View.GONE
        }

        root.expansion_layout_one.addListener { expansionLayout, expanded ->
            if (expanded){
                root.image1.setImageResource(R.drawable.residential_district)
                root.tv1.setTextColor(resources.getColor(R.color.app_color))
                root.tv1_2.setTextColor(resources.getColor(R.color.black))
            }else{
                root.image1.setImageResource(R.drawable.residential_district_grey)
                root.tv1.setTextColor(resources.getColor(R.color.light_grey))
                root.tv1_2.setTextColor(resources.getColor(R.color.light_grey))

            }
        }

        root.expansion_layout_two.addListener { expansionLayout, expanded ->
            if (expanded){
                root.image2.setImageResource(R.drawable.city)
                root.tv2.setTextColor(resources.getColor(R.color.app_color))
                root.tv2_2.setTextColor(resources.getColor(R.color.black))
            }else{
                root.image2.setImageResource(R.drawable.city_grey)
                root.tv2.setTextColor(resources.getColor(R.color.light_grey))
                root.tv2_2.setTextColor(resources.getColor(R.color.light_grey))

            }
        }

        root.expansion_layout_three.addListener { expansionLayout, expanded ->
            if (expanded){
                root.image3.setImageResource(R.drawable.location)
                root.tv3.setTextColor(resources.getColor(R.color.app_color))
                root.tv2_3.setTextColor(resources.getColor(R.color.black))
            }else{
                root.image3.setImageResource(R.drawable.location_grey)
                root.tv3.setTextColor(resources.getColor(R.color.light_grey))
                root.tv2_3.setTextColor(resources.getColor(R.color.light_grey))

            }
        }

        root.expansion_layout_four.addListener { expansionLayout, expanded ->
            if (expanded){
                root.image4.setImageResource(R.drawable.phone)
                root.tv4.setTextColor(resources.getColor(R.color.app_color))
                root.tv2_4.setTextColor(resources.getColor(R.color.black))
            }else{
                root.image4.setImageResource(R.drawable.phone_grey)
                root.tv4.setTextColor(resources.getColor(R.color.light_grey))
                root.tv2_4.setTextColor(resources.getColor(R.color.light_grey))

            }
        }

        root.confirm_donation.setOnClickListener {
            showDialog()
            v.close.setOnClickListener {
                dialog.dismiss()
            }
            v.confirm.setOnClickListener {
                dialog.dismiss()
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer,ConfirmationFragment()).addToBackStack(null).commit()
            }
        }

        return root
    }

    private fun showDialog() {
        dialog = Dialog(this.requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        v= layoutInflater.inflate(R.layout.bottom_dialog_item_manual,null)
        dialog.setContentView(v)
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setGravity(Gravity.BOTTOM)
        dialog.setCancelable(false)

        if (previous_fragment == "campaignDetails"){
            v.amount_linear.visibility = View.GONE
            v.amount_view.visibility = View.GONE
        }

        dialog.show()

    }


}