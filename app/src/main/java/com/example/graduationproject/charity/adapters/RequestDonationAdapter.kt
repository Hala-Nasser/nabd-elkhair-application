package com.example.graduationproject.charity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import android.app.Activity
import android.os.Build
import android.util.Log
import com.example.graduationproject.api.charityApi.donation.Donor
import com.example.graduationproject.api.donorApi.forgotPassword.ForgotPasswordJson
import com.example.graduationproject.network.RetrofitInstance
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bottom_donation_with_campaign.view.*
import kotlinx.android.synthetic.main.bottom_sheet_manually.view.*
import kotlinx.android.synthetic.main.donation_requests_item.view.*
import kotlinx.android.synthetic.main.donors_item.view.donor_image
import kotlinx.android.synthetic.main.donors_item.view.donor_name
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.graduationproject.api.charityApi.donation.Data
import com.example.graduationproject.charity.fragments.DonationRequestsFragment
import com.example.graduationproject.charity.models.CounterViewModel
import com.example.graduationproject.classes.PrefUtil
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import kotlin.collections.ArrayList


class RequestDonationAdapter(
    var activity: Context?, var data: ArrayList<Data>?,
    var fragment: DonationRequestsFragment
) : RecyclerView.Adapter<RequestDonationAdapter.DonationRequestsViewHolder>() {

    enum class TimerState {
        Stopped, Paused, Running
    }

    private var timerState = TimerState.Stopped
    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 0
    private var secondsRemaining: Long = 0
    var nowSeconds: Long = System.currentTimeMillis()

    class DonationRequestsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var context = itemView.context
        lateinit var counterViewModel: CounterViewModel

        var viewLifecycleOwner: LifecycleOwner

        init {
            viewLifecycleOwner = context as LifecycleOwner
        }

        val image = itemView.donor_image
        val name = itemView.donor_name
        val donation_accept = itemView.donation_accept
        val donation_declined = itemView.donation_declined
        val txt_countdown = itemView.textView_countdown

        fun initialize(data: Donor, fragment: DonationRequestsFragment) {
            counterViewModel = ViewModelProvider(fragment).get(CounterViewModel::class.java)
            Picasso.get().load(RetrofitInstance.IMAGE_URL + data.image).into(image)
            name.text = data.name
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DonationRequestsViewHolder {
        var view: View =
            LayoutInflater.from(activity).inflate(R.layout.donation_requests_item, parent, false)
        return DonationRequestsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DonationRequestsViewHolder, position: Int) {
        holder.initialize(data!![position].donor!!, fragment)
        holder.itemView.setOnClickListener {
            bottomSheet(activity as Activity, position)
        }

        val now: DateTime = DateTime.now()
        var formatter =
            DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withLocale(java.util.Locale.ENGLISH)
        var target = formatter.parseDateTime(data!![position].date)

        val twentyFourHoursLater = target.plusHours(24)
        val expired = now.isAfter(twentyFourHoursLater)

        val countNumber = twentyFourHoursLater.millis - nowSeconds
        if (!expired) {
            Log.e("TimerPosition", "التايمر بزيد$position")
            timer = object : CountDownTimer(countNumber, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    secondsRemaining = (millisUntilFinished) / 1000

                    // now you repalce txtViewHours,  txtViewMinutes, txtViewSeconds by your textView.
                    val hoursLeft = String.format("%d", secondsRemaining % 86400 / 3600)

                    Log.d("hoursLeft", hoursLeft)
                    val minutesLeft = String.format("%d", secondsRemaining % 86400 % 3600 / 60)
                    Log.d("minutesLeft", minutesLeft)
                    val secondsLeft = String.format("%d", secondsRemaining % 86400 % 3600 % 60)
                    Log.d("secondsLeft", secondsLeft)
                    holder.txt_countdown.text = "$hoursLeft:$minutesLeft:$secondsLeft"
                }

                override fun onFinish() {
                    holder.txt_countdown.text = "done!"
                    setDonationAcceptance(data!![position].id, 0, holder.adapterPosition)
                    Log.i("Timer", "Timer finished")
                }
            }

            timer.start()
        } else {
            Log.e("TimerPosition", "الوقت فات$position")
            setDonationAcceptance(data!![position].id, 0, holder.adapterPosition)
        }


        holder.donation_accept.setOnClickListener {
            setDonationAcceptance(data!![position].id, 1, holder.adapterPosition)
        }

        holder.donation_declined.setOnClickListener {
            setDonationAcceptance(data!![position].id, 0, holder.adapterPosition)
        }


    }

    fun bottomSheet(activity: Activity, position: Int) {
        var view = activity.layoutInflater.inflate(R.layout.bottom_sheet_manually, null)
        var bottomSheetDialog = BottomSheetDialog(activity)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.setCanceledOnTouchOutside(false)
        if (data!![position].campaign != null) {
            view.bs_campaign_card.visibility = View.VISIBLE
            Picasso.get().load(RetrofitInstance.IMAGE_URL + data!![position].campaign!!.image)
                .into(view.bs_campaign_image)
            view.bs_campaign_name.text = data!![position].campaign!!.name
        } else {
            view.bs_campaign_card.visibility = View.GONE
        }

        view.close.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        val donation = data!![position]
        view.bs_donor_name.text = donation.donor!!.name
        view.bs_received_date.text = donation.date_time
        view.bs_donor_prefecture.text = donation.donor_district
        view.bs_donor_city.text = donation.donor_city
        view.description.text = donation.description
        view.bs_donor_address.text = donation.donor_address
        view.bs_donor_phone.text = donation.donor_phone
        bottomSheetDialog.show()
    }

    fun setDonationAcceptance(donation_id: Int, acceptance: Int, position: Int) {

        var sharedPref = activity!!.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        var token = sharedPref.getString("charity_token", "")!!

        val retrofitInstance =
            RetrofitInstance.create()
        val response =
            retrofitInstance.setDonationAcceptance("Bearer $token", donation_id, acceptance)

        response.enqueue(object : Callback<ForgotPasswordJson> {
            override fun onResponse(
                call: Call<ForgotPasswordJson>,
                response: Response<ForgotPasswordJson>
            ) {
                val body = response.body()
                if (response.isSuccessful) {
                    data!!.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, data!!.size)

                    Log.e("DonationAcceptance", body!!.message)
                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<ForgotPasswordJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }


    fun registerActivityState() = object : OnActivityStateChanged {

        override fun onPaused() {
            if (timerState == TimerState.Running) {
                timer.cancel()
                val wakeUpTime = DonationRequestsFragment.setAlarm(
                    activity!!,
                    nowSeconds,
                    secondsRemaining
                )
            }

            PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, activity!!)
            PrefUtil.setSecondsRemaining(secondsRemaining, activity!!)
            PrefUtil.setTimerState(timerState, activity!!)
        }


    }


    interface OnActivityStateChanged {
        fun onPaused()
    }


}
