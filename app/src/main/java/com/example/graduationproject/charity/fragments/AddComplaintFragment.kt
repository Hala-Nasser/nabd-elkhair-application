package com.example.graduationproject.charity.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.example.graduationproject.R
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.fragment_add_complaint.view.*
import kotlinx.android.synthetic.main.fragment_add_complaint.view.back
import kotlinx.android.synthetic.main.fragment_add_complaint.view.expandable_layout_1
import kotlinx.android.synthetic.main.fragment_add_complaint.view.image1
import kotlinx.android.synthetic.main.fragment_add_complaint.view.indicator1
import kotlinx.android.synthetic.main.fragment_add_complaint.view.title1
import net.cachapa.expandablelayout.ExpandableLayout
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddComplaintFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_add_complaint, container, false)

        val cal = Calendar.getInstance()
        val month_date = SimpleDateFormat("MMMM")
        val month_name = month_date.format(cal.time)
        val year = cal.get(Calendar.YEAR)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        root.monthAndYear.text = "$month_name $year"
        root.day.text = day.toString()

        var complaint_reasons : Array<String> = emptyArray()
        var reasons = ArrayList<String>()

        val b = arguments
        val from = b!!.getString("from")
        if (from=="Charity"){
            requireActivity().charity_nav_bottom.visibility=View.GONE
        }else{
            //requireActivity().nav_bottom.visibility=View.GONE
            complaint_reasons = resources.getStringArray(R.array.donor_complaint_reason_array)
        }

        for (reason in complaint_reasons.indices){
            val cb = CheckBox(activity)
            cb.id = reason
            cb.text = complaint_reasons[reason]
            cb.isChecked = false
            root.cb_layout.addView(cb)

            cb.setOnCheckedChangeListener { _, _ ->
                if (cb.isChecked){
                    Log.e("cb", "true")
                    reasons.add(cb.text.toString())
                    Log.e("reasons check", reasons.toString())
                }else{
                    Log.e("cb", "false")
                    if (reasons.contains(cb.text.toString())){
                        reasons.remove(cb.text.toString())
                        Log.e("reasons uncheck", reasons.toString())
                    }
                }
                Log.e("reasons after", reasons.toString())

            }
//            cb.setOnClickListener {
//                if (cb.isChecked){
//                    if (reasons.contains(cb.text.toString())){
//                        reasons.dropWhile {
//                            it == cb.text.toString()
//                        }
//                    }
//
//                }else{
//                    reasons.plus(cb.text.toString())
//                }
//                for (reas in reasons){
//                    Log.e("reasons", reas)
//                }
//
//            }
        }
        
        

        root.complaint_type_layout.isActivated = true
        root.complaint_type_layout.setOnClickListener {
            if (root.complaint_type_layout.isActivated){
                root.expandable_layout_1.isExpanded = !root.expandable_layout_1.isExpanded
            }
        }

        root.expandable_layout_1.setOnExpansionUpdateListener { _, _ ->
            layoutStyle(root.indicator1, root.expandable_layout_1)
        }



//        root.el_add_complaint.setOnClickListener {
//            if (root.el_add_complaint.state == ExpandableLayout.State.COLLAPSED) {
//                root.add_complaint_arrow.setImageResource(R.drawable.ic_arrow_up)
//                root.el_add_complaint.isExpanded = true
//            } else if (root.el_add_complaint.state == ExpandableLayout.State.EXPANDED) {
//                root.add_complaint_arrow.setImageResource(R.drawable.ic_arrow_down)
//                root.el_add_complaint.isExpanded = false
//            }
//        }

        root.back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return root
    }

    fun layoutStyle(indicator: ImageView, layout: ExpandableLayout){

        if (layout.isExpanded){
            indicator.setImageResource(R.drawable.ic_arrow_down)
        }else{
            indicator.setImageResource(R.drawable.ic_arrow_up)
        }

    }

}