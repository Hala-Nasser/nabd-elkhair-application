package com.example.graduationproject.charity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.R
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.fragment_add_complaint.view.*
import kotlinx.android.synthetic.main.fragment_charity_edit_profile.view.*
import kotlinx.android.synthetic.main.fragment_charity_edit_profile.view.edit_location
import net.cachapa.expandablelayout.ExpandableLayout


class AddComplaintFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_add_complaint, container, false)

        val b = arguments
        val from = b!!.getString("from")
        if (from=="Charity"){
            requireActivity().nav_bottom.visibility=View.GONE
        }else requireActivity().charity_nav_bottom.visibility=View.GONE


        root.el_add_complaint.setOnClickListener {
            if (root.el_add_complaint.state == ExpandableLayout.State.COLLAPSED) {
                root.add_complaint_arrow.setImageResource(R.drawable.ic_arrow_up)
                root.el_add_complaint.isExpanded = true
            } else if (root.el_add_complaint.state == ExpandableLayout.State.EXPANDED) {
                root.add_complaint_arrow.setImageResource(R.drawable.ic_arrow_down)
                root.el_add_complaint.isExpanded = false
            }
        }
        return root
    }

}