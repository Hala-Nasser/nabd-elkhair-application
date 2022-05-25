package com.example.graduationproject.charity.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.activity.OnBackPressedCallback
import androidx.viewpager.widget.ViewPager
import com.example.graduationproject.R
import com.example.graduationproject.adapters.MyPagerAdapter
import com.example.graduationproject.api.charityApi.campaign.CampaignJson
import com.example.graduationproject.api.charityApi.register.RegisterJson
import com.example.graduationproject.charity.activites.PaymentsMethodActivity
import com.example.graduationproject.classes.FileUtil
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.Validation
import com.example.graduationproject.network.RetrofitInstance
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import kotlinx.android.synthetic.main.activity_charity_complete_signup.*
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.add_campaign_phase_four.*
import kotlinx.android.synthetic.main.add_campaign_phase_one.*
import kotlinx.android.synthetic.main.add_campaign_phase_one.view.*
import kotlinx.android.synthetic.main.add_campaign_phase_three.*
import kotlinx.android.synthetic.main.add_campaign_phase_two.*
import kotlinx.android.synthetic.main.fragment_add_campaign.*
import kotlinx.android.synthetic.main.fragment_add_campaign.view.*
import kotlinx.android.synthetic.main.fragment_charity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_charity_edit_profile.view.*
import kotlinx.android.synthetic.main.fragment_edit_campaign.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AddCampaignFragment : Fragment() {

    lateinit var layouts: IntArray
    lateinit var pagerAdapter: MyPagerAdapter
     var donationType:String? = null
     var name= ""
     var desc = ""
     var imageURI:Uri? = null
     var time=""
     var date=""
     var token=""
    var progressDialog: ProgressDialog? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val languageToLoad = "ar"
//
//        val locale = Locale(languageToLoad)
//        Locale.setDefault(locale)
//        val config = Configuration()
//        config.locale = locale
//        resources.updateConfiguration(
//            config,
//            resources.displayMetrics
//        )
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_campaign, container, false)
        var sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        token = sharedPref.getString("charity_token", "")!!
        donationType = requireArguments().getString("donationType")

        layouts = intArrayOf(R.layout.add_campaign_phase_one, R.layout.add_campaign_phase_two, R.layout.add_campaign_phase_three,R.layout.add_campaign_phase_four)
        pagerAdapter = MyPagerAdapter(requireContext(), layouts)
        root.add_campaign_pager.adapter = pagerAdapter

        root.add_campaign_pager.rotationY = 180F
        requireActivity().charity_nav_bottom.visibility=View.GONE

     var onPageChangeListener: ViewPager.OnPageChangeListener =  object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                //root.progressBar.progress += 25
                when (position) {
                    layouts.size - 1 -> {
                        //LAST PAGE
                        changeProgressData(root,"اضافة حملة",View.GONE,"4 من 4","تاكيد الحملة","")
                        root.progressBar.progress = 100
                        final_campaign_image.setImageURI(imageURI)
                        final_campaign_title.text = name
                        final_campaign_desc.text = desc
                        final_campaign_date.text = date
                        final_campaign_time.text = time
                    }
                    0 -> {
                        changeProgressData(root,"التالي",View.VISIBLE,"1 من 4","معلومات الحملة","التالي : تاريخ انتهاء الحملة")
                       // Log.d("data",campaign_title.text.toString())
                        root.progressBar.progress = 25
                        choose_campaign_image.setOnClickListener {
                            Log.e("clicked","yes")
                            PickImageDialog.build(PickSetup())
                                .setOnPickResult { r ->
                                    imageURI = r.uri
                                    campaign_image.setImageBitmap(r.bitmap)
                                }
                                .setOnPickCancel{
                                }.show(requireActivity().supportFragmentManager)
                        }
                    }
                    1 -> {

                        changeProgressData(root,"التالي",View.VISIBLE,"2 من 4","تاريخ انتهاء الحملة","التالي : إضافة وقت الانتهاء")
                        root.progressBar.progress = 50
                        getDate()
                    }
                    2 -> {

                        changeProgressData(root,"التالي",View.VISIBLE,"3 من 4","إضافة وقت الانتهاء","التالي : تاكيد الحملة")
                        root.progressBar.progress = 75
                        getTime()
                    }
                }

            }

        }
        root.add_campaign_pager.setOnPageChangeListener(onPageChangeListener)
        root.add_campaign_pager.post(Runnable { onPageChangeListener.onPageSelected(root.add_campaign_pager.currentItem) })

        root.add_campaign_next.setOnClickListener {
            val currentPage = root.add_campaign_pager.currentItem + 1

            when (root.add_campaign_pager.currentItem) {
                0 -> {
                   name = campaign_title.text.toString()
                   desc = campaign_desc.text.toString()
                }
            }
            if (currentPage < layouts.size) {
                //move to next page
                root.add_campaign_pager.currentItem = currentPage
                //root.progressBar.progress += 25
            } else {
                progressDialog = ProgressDialog(requireContext())
                GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
                addCampaign(name,desc,date,time,donationType!!,imageURI!!)
            }
        }


        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                var bundle = Bundle()
                bundle.putBoolean("addCampaign",false)
                var fragment = HomeFragment()
                fragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.charityContainer,fragment).commit()
            }
        })

        return root
    }

    private fun changeProgressData(root: View,
                                   btn_txt:String,btn_img:Int,
                                   progress_rate:String,title:String,subtitle:String) {
        root.btn_txt.text = btn_txt
        root.btn_img.visibility = btn_img
        root.progress_rate.text = progress_rate
        root.add_campaign_title.text = title
        root.add_campaign_subtitle.text = subtitle
    }

    override fun onResume() {
        super.onResume()
        requireActivity().charity_nav_bottom.visibility=View.GONE
    }

    fun addCampaign(name:String,desc:String,date:String,time:String,donationType:String,imageURI:Uri) {

        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("name", name)
            .addFormDataPart("description", desc)
            .addFormDataPart("expiry_date", date)
            .addFormDataPart("expiry_time", time)
            .addFormDataPart("donation_type_id", donationType)
            .addFormDataPart(
                "image", File(FileUtil.getPath(imageURI!!, requireContext())).extension ,
                RequestBody.create(
                    MediaType.parse("application/octet-stream"),
                    File(FileUtil.getPath(imageURI!!, requireContext()))
                )
            )
            .build()


        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.addCampaign("Bearer $token",body)

        response.enqueue(object : Callback<CampaignJson> {
            override fun onResponse(call: Call<CampaignJson>, response: Response<CampaignJson>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("ADD Campaign", data.toString())

                    if (data!!.status) {

                        GeneralChanges().hideDialog(progressDialog!!)
                        var bundle = Bundle()
                        bundle.putBoolean("addCampaign",true)
                        var fragment = HomeFragment()
                        fragment.arguments = bundle
                        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.charityContainer,fragment).addToBackStack(null).commit()

                    }else{
                        GeneralChanges().hideDialog(progressDialog!!)
                        Validation().showSnackBar(add_campaign_layout, data.message)
                    }

                } else {
                    GeneralChanges().hideDialog(progressDialog!!)
                    Log.e("errorBody", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<CampaignJson>, t: Throwable) {
                GeneralChanges().hideDialog(progressDialog!!)
                Log.e("failure", t.message!!)
            }
        })
    }


    private fun getDate(){
        val calendar = Calendar.getInstance()

        campaign_date_picker.init(calendar[Calendar.YEAR],
            calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH],
            DatePicker.OnDateChangedListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val locale = Locale("ar", "SA")
               // EEEE، d MMMM y
                val myFormat = "EEEE، d MMMM y"
                val sdf = SimpleDateFormat(myFormat, locale)
                date = sdf.format(calendar.time)
            })

    }

    private fun getTime(){
        campaign_time_picker.setOnTimeChangedListener(TimePicker.OnTimeChangedListener { view, hourOfDay, minute -> //use getFocusedChild() helps partly
                val initialCalendar = Calendar.getInstance()
                initialCalendar[Calendar.HOUR_OF_DAY] = hourOfDay
                initialCalendar[Calendar.MINUTE] = minute
                initialCalendar[Calendar.SECOND] = 0

                val locale = Locale("ar", "SA")

                val myFormat = "h:mm a"
                val sdf = SimpleDateFormat(myFormat, locale)
                 time = sdf.format(initialCalendar.time)
        })
    }
}