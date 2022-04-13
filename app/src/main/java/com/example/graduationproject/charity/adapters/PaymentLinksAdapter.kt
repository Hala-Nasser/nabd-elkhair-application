package com.example.graduationproject.charity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R

import android.app.Activity
import com.example.graduationproject.charity.models.PaymentLink
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_manually.view.*
import kotlinx.android.synthetic.main.payment_item.view.*


class PaymentLinksAdapter(
    var activity: Context?, var data: List<PaymentLink>, var from: String
) : RecyclerView.Adapter<PaymentLinksAdapter.PaymentLinksViewHolder>(){

    class PaymentLinksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image  =itemView.payment_link_img

        fun initialize(data: PaymentLink) {
            image.setImageResource(R.drawable.visa)
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaymentLinksViewHolder{
            var view: View = LayoutInflater.from(activity).inflate(R.layout.payment_item, parent, false)
            return PaymentLinksViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PaymentLinksViewHolder, position: Int) {

        holder.initialize(data[position])
         if (from=="PaymentSettings"){
             holder.itemView.setOnClickListener {
                 bottomSheet(activity as Activity, position)
             }
         }else{

         }


    }


    fun bottomSheet(activity: Activity, position: Int){
        var view = activity.layoutInflater.inflate(R.layout.bd_payment_item, null)
        var bottomSheetDialog = BottomSheetDialog(activity)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.setCanceledOnTouchOutside(false)
        view.close.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }
}