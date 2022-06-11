package com.example.graduationproject

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.graduationproject.adapters.MyPagerAdapter
import android.content.Intent
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.contentValuesOf
import com.example.graduationproject.api.donorApi.staticPages.StaticPagesJson
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.on_boarding_1.*
import kotlinx.android.synthetic.main.on_boarding_2.*
import kotlinx.android.synthetic.main.on_boarding_3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OnBoardingActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager
    lateinit var btnNext: AppCompatButton
    lateinit var tvSkip: TextView
    lateinit var layouts: IntArray
    lateinit var pagerAdapter: MyPagerAdapter
    lateinit var layoutDot: LinearLayout
    lateinit var dots: Array<ImageView?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)


        viewPager = findViewById(R.id.pager)
        layoutDot = findViewById(R.id.dotLayout)
        btnNext = findViewById(R.id.next)
        tvSkip = findViewById(R.id.skip)

        layouts = intArrayOf(R.layout.on_boarding_1, R.layout.on_boarding_2, R.layout.on_boarding_3)
        pagerAdapter = MyPagerAdapter(applicationContext, layouts)
        viewPager.adapter = pagerAdapter

        viewPager.rotationY = 180F
        getStaticPage(3)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                Log.e("pos", position.toString())
                when (position) {
                    0 -> {
                        getStaticPage(3)
                        btnNext.text = "التالي"
                        tvSkip.visibility = View.VISIBLE
                    }
                    1 -> {
                        getStaticPage(4)
                        btnNext.text = "التالي"
                        tvSkip.visibility = View.VISIBLE
                    }
                    layouts.size - 1 -> {
                        getStaticPage(5)
                        btnNext.text = "هيا بنا"
                        tvSkip.visibility = View.GONE
                    }
                }
                createDote(position)
            }

        })
        createDote(0)
        tvSkip.setOnClickListener {
            GeneralChanges().prepareFadeTransition(this, ChoiceActivity())
        }
        btnNext.setOnClickListener {
            val currentPage = viewPager.currentItem + 1
            if (currentPage < layouts.size) {
                //move to next page
                viewPager.currentItem = currentPage
            } else {
                GeneralChanges().prepareFadeTransition(this, ChoiceActivity())
            }
        }
    }

    fun createDote(currentPosition: Int) {
        layoutDot.removeAllViews()

        dots = arrayOfNulls<ImageView>(layouts.size)

        for (i in dots.indices) {
            dots[i] = ImageView(this)
            if (i == currentPosition) {
                dots[i]!!.setImageResource(R.drawable.active_dots)
            } else {
                dots[i]!!.setImageResource(R.drawable.default_dots)
            }

            var param: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            param.setMargins(4, 0, 4, 0)
            layoutDot.addView(dots[i], param)

        }

    }

    fun getStaticPage(id: Int) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getStaticPages(id)

        response.enqueue(object : Callback<StaticPagesJson> {
            override fun onResponse(
                call: Call<StaticPagesJson>,
                response: Response<StaticPagesJson>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                    when (id) {
                        3 -> {
                            content_1.text = data.content
                            onboarding_title_1.text = data.title
                            Picasso.get().load(RetrofitInstance.IMAGE_URL + data.image)
                                .into(image_1)
                        }
                        4 -> {
                            content_2.text = data.content
                            onboarding_title_2.text = data.title
                            Picasso.get().load(RetrofitInstance.IMAGE_URL + data.image)
                                .into(image_2)
                        }
                        5 -> {
                            content_3.text = data.content
                            onboarding_title_3.text = data.title
                            Picasso.get().load(RetrofitInstance.IMAGE_URL + data.image)
                                .into(image_3)
                        }
                    }
                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }
            }

            override fun onFailure(call: Call<StaticPagesJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }
}
