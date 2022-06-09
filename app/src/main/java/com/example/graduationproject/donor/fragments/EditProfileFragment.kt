package com.example.graduationproject.donor.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.RestrictionEntry.TYPE_NULL
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.profile.ProfileJson
import com.example.graduationproject.api.donorApi.register.RegisterJson
import com.example.graduationproject.api.donorApi.updateProfile.UpdateProfileJson
import com.example.graduationproject.classes.FileUtil
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.Validation
import com.example.graduationproject.donor.DonorMainActivity
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.view.*
import kotlinx.android.synthetic.main.fragment_edit_profile.view.save
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class EditProfileFragment : Fragment() {

    var user_address: String?=null
    var isAllFieldsChecked = false

    lateinit var user_name:String
    lateinit var user_phone:String
    lateinit var user_image:String

    var progressDialog: ProgressDialog? = null

    lateinit var image: ImageView
    var imageURI: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        image = root.edit_profile_image

        val sharedPref= requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val user_id = sharedPref.getInt("user_id", 0)

        val locations = resources.getStringArray(R.array.donor_location_array)
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.dropdown_item, locations)
        root.autoCompleteTextView.setAdapter(arrayAdapter)

        progressDialog = ProgressDialog(activity)
        GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
        profile(user_id)


        root.save.setOnClickListener {
            user_name = root.username.text.toString()
            user_phone = root.phone.text.toString()
            user_address = root.autoCompleteTextView.text.toString()
            Log.e("location", user_address!!)

            isAllFieldsChecked = CheckAllFields()

            if (isAllFieldsChecked) {
                if (imageURI != null){
                    var path = imageURI!!.path
                    if (path != null){
                        progressDialog = ProgressDialog(activity)
                        GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
                        updateProfile(user_id, user_name, user_phone, user_address!!, null)

                    }
                }else{
                    updateProfile(user_id, user_name, user_phone, user_address!!, user_image)
                }
            }

        }

        root.choose_image.setOnClickListener {
            PickImageDialog.build(PickSetup())
                .setOnPickResult { r ->
                    imageURI = r.uri
                    image.setImageBitmap(r.bitmap)
                }
                .setOnPickCancel{
                }.show(requireActivity().supportFragmentManager)
        }

        root.back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return root
    }

    private fun CheckAllFields(): Boolean {
        if (!Validation().validateUsername(username, username_layout)) return false

        if (!Validation().validatePhoneNumber(phone, phone_layout)) return false

        if (!Validation().validateLocation(autoCompleteTextView, location_layout)) return false
        return true
    }

    fun profile(id: Int) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.profile(id)

        response.enqueue(object : Callback<ProfileJson> {
            override fun onResponse(call: Call<ProfileJson>, response: Response<ProfileJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data
                    Picasso.get().load(RetrofitInstance.IMAGE_URL+data.image).into(edit_profile_image)
                    username.setText(data.name)
                    email.setText(data.email)
                    phone.setText(data.phone.toString())

                    user_image = data.image

                    val sharedPref = activity!!.getSharedPreferences(
                        "sharedPref", Context.MODE_PRIVATE
                    )
                    val editor = sharedPref.edit()
                    editor.putString("user_image", data.image)
                    editor.apply()


                    autoCompleteTextView.setText(data.location)
                    GeneralChanges().hideDialog(progressDialog!!)

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<ProfileJson>, t: Throwable) {
                Log.e("failure", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)
            }
        })
    }

    fun updateProfile(id:Int, name:String, phone: String, location:String, user_image:String?) {

        var body: RequestBody
        if (user_image == null){
            body = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("id", id.toString())
                .addFormDataPart("name", name)
                .addFormDataPart("phone", phone)
                .addFormDataPart("location", location)
                .addFormDataPart(
                    "image", File(FileUtil.getPath(imageURI!!, requireActivity())).extension ,
                    RequestBody.create(
                        "application/octet-stream".toMediaTypeOrNull(),
                        File(FileUtil.getPath(imageURI!!, requireActivity()))
                    )
                )
                .build()
        }else{
            body = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("id", id.toString())
                .addFormDataPart("name", name)
                .addFormDataPart("phone", phone)
                .addFormDataPart("location", location)
                .build()
        }


        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.updateProfile(body)

        response.enqueue(object : Callback<UpdateProfileJson> {
            override fun onResponse(call: Call<UpdateProfileJson>, response: Response<UpdateProfileJson>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data!!.status) {

                        GeneralChanges().hideDialog(progressDialog!!)
                        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer,ProfileFragment()).addToBackStack(null).commit()

                    }else{
                        GeneralChanges().hideDialog(progressDialog!!)
                        Validation().showSnackBar(parent_layout, data.message)
                    }

                } else {
                    GeneralChanges().hideDialog(progressDialog!!)
                    Log.e("errorBody", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<UpdateProfileJson>, t: Throwable) {
                GeneralChanges().hideDialog(progressDialog!!)
                Log.e("failure", t.message!!)
            }
        })
    }

}