package com.example.graduationproject.charity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.R
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.fragment_charity_edit_profile.view.*
import kotlinx.android.synthetic.main.fragment_charity_home.view.*
import net.cachapa.expandablelayout.ExpandableLayout


class CharityEditProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_charity_edit_profile, container, false)

        requireActivity().charity_nav_bottom.visibility=View.GONE

        root.edit_location.setOnClickListener {
            if (root.edit_profile_expandable_layout.state == ExpandableLayout.State.COLLAPSED) {
                root.edit_profile_arrow.setImageResource(R.drawable.ic_arrow_up)
                root.edit_profile_expandable_layout.isExpanded = true
            } else if (root.edit_profile_expandable_layout.state == ExpandableLayout.State.EXPANDED) {
                root.edit_profile_arrow.setImageResource(R.drawable.ic_arrow_down)
                root.edit_profile_expandable_layout.isExpanded = false
            }
        }
        return root
    }

}