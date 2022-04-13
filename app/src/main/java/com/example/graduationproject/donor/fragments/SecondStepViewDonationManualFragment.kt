package com.example.graduationproject.donor.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.graduationproject.R
import com.example.graduationproject.donor.models.DonationType
import com.github.florent37.expansionpanel.ExpansionLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_dialog_item.view.*
import kotlinx.android.synthetic.main.bottom_dialog_item.view.amount
import kotlinx.android.synthetic.main.bottom_dialog_item.view.close
import kotlinx.android.synthetic.main.bottom_dialog_item_manual.view.*
import kotlinx.android.synthetic.main.fragment_second_step_view_donation_manual.*
import kotlinx.android.synthetic.main.fragment_second_step_view_donation_manual.view.*
import net.cachapa.expandablelayout.ExpandableLayout
import net.cachapa.expandablelayout.ExpandableLayout.OnExpansionUpdateListener


class SecondStepViewDonationManualFragment : Fragment() {

    lateinit var dialog :BottomSheetDialog
    lateinit var v :View
    var previous_fragment = ""
    var selected_type : String? = null

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
        val radioGroup=root.radioGroup

        root.residential_layout.isActivated = false
        root.city_layout.isActivated = false
        root.address_layout.isActivated = false
        root.phone_layout.isActivated = false


        root.residential_layout.setOnClickListener {
            if (root.residential_layout.isActivated){
                root.expandable_layout_1.isExpanded = !root.expandable_layout_1.isExpanded
            }
        }

        root.expandable_layout_1.setOnExpansionUpdateListener { _, _ ->
            layoutStyle(root.image1,
                R.drawable.residential_district,
                R.drawable.residential_district_grey,
                root.title1,
                root.sub_title1,
                root.indicator1,
                root.expandable_layout_1,
                root.expandable_layout_2,
                root.expandable_layout_3,
                root.expandable_layout_4,
                root.expandable_layout_5)

        }

        radioGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.north -> {
                    selected_type = "north"
                }
                R.id.south -> {
                    selected_type = "south"
                }
            }
            root.residential_layout.isActivated = true
            root.city_layout.isActivated = true
        }

        root.city_layout.setOnClickListener {
            if (root.city_layout.isActivated){
                root.expandable_layout_2.isExpanded = !root.expandable_layout_2.isExpanded
            }
        }

        root.expandable_layout_2.setOnExpansionUpdateListener { _, _ ->
            layoutStyle(root.image2,
                R.drawable.city,
                R.drawable.city_grey,
                root.title2,
                root.sub_title2,
                root.indicator2,
                root.expandable_layout_2,
                root.expandable_layout_1,
                root.expandable_layout_3,
                root.expandable_layout_4,
                root.expandable_layout_5)

        }

        root.city_edit_text.addTextChangedListener {
            if (root.city_edit_text.text.isNotEmpty() && root.city_edit_text.text.toString() != " "){
                root.address_layout.isActivated = true
            }
        }

        root.address_layout.setOnClickListener {
            if (root.address_layout.isActivated){
                root.expandable_layout_3.isExpanded = !root.expandable_layout_3.isExpanded
            }
        }

        root.expandable_layout_3.setOnExpansionUpdateListener { _, _ ->
            layoutStyle(root.image3,
                R.drawable.location,
                R.drawable.location_grey,
                root.title3,
                root.sub_title3,
                root.indicator3,
                root.expandable_layout_3,
                root.expandable_layout_1,
                root.expandable_layout_2,
                root.expandable_layout_4,
                root.expandable_layout_5)

        }

        root.address.addTextChangedListener {
            if (root.address.text.isNotEmpty() && root.address.text.toString() != " "){
                root.phone_layout.isActivated = true
            }
        }

        root.phone_layout.setOnClickListener {
            if (root.phone_layout.isActivated){
                root.expandable_layout_4.isExpanded = !root.expandable_layout_4.isExpanded
            }
        }

        root.expandable_layout_4.setOnExpansionUpdateListener { _, _ ->
            layoutStyle(root.image4,
                R.drawable.phone,
                R.drawable.phone_grey,
                root.title4,
                root.sub_title4,
                root.indicator4,
                root.expandable_layout_4,
                root.expandable_layout_1,
                root.expandable_layout_2,
                root.expandable_layout_3,
            root.expandable_layout_5)

        }

        root.phone.addTextChangedListener {
            if (root.phone.text.isNotEmpty() && root.phone.text.toString() != " "){
                root.time_layout.isActivated = true
            }
        }

        root.time_layout.setOnClickListener {
            if (root.time_layout.isActivated){
                root.expandable_layout_5.isExpanded = !root.expandable_layout_5.isExpanded
            }
        }

        root.expandable_layout_5.setOnExpansionUpdateListener { _, _ ->
            layoutStyle(root.image5,
                R.drawable.time,
                R.drawable.time_grey,
                root.title5,
                root.sub_title5,
                root.indicator5,
                root.expandable_layout_5,
                root.expandable_layout_1,
                root.expandable_layout_2,
                root.expandable_layout_3,
                root.expandable_layout_4)

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
        dialog = BottomSheetDialog(this.requireContext())
        v= layoutInflater.inflate(R.layout.bottom_dialog_item_manual,null)
        dialog.setContentView(v)
        dialog.setCanceledOnTouchOutside(false)

        if (previous_fragment == "campaignDetails"){
            v.amount_linear.visibility = View.GONE
            v.amount_view.visibility = View.GONE
        }

        dialog.show()

    }

    fun layoutStyle(image:ImageView,
                             trueImageResource:Int,
                             falseImageResource:Int,
                             title:TextView,
                             subTitle:TextView,
                             indicator: ImageView,
                             layout:ExpandableLayout,
                              layout2:ExpandableLayout,
                              layout3:ExpandableLayout,
                              layout4:ExpandableLayout,
    layout5: ExpandableLayout){

        if (layout.isExpanded){
            image.setImageResource(trueImageResource)
            title.setTextColor(resources.getColor(R.color.app_color))
            subTitle.setTextColor(resources.getColor(R.color.black))
            indicator.setImageResource(R.drawable.ic_arrow_down)
            layout2.isExpanded = false
            layout3.isExpanded = false
            layout4.isExpanded = false
            layout5.isExpanded = false
        }else{
            image.setImageResource(falseImageResource)
            title.setTextColor(resources.getColor(R.color.light_grey))
            subTitle.setTextColor(resources.getColor(R.color.light_grey))
            indicator.setImageResource(R.drawable.ic_arrow_up)
        }

    }


}