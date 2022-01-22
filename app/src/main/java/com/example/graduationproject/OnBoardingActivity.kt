package com.example.graduationproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.graduationproject.adapters.MyPagerAdapter
import android.content.Intent
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.graduationproject.classes.GeneralChanges


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

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (position == layouts.size - 1) {
                    //LAST PAGE
                    btnNext.text = "هيا بنا"
                    tvSkip.visibility = View.GONE
                } else {
                    btnNext.text = "التالي"
                    tvSkip.visibility = View.VISIBLE
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

    fun createDote(currentPosition: Int){
        layoutDot.removeAllViews()

        dots = arrayOfNulls<ImageView>(layouts.size)

        for (i in dots.indices){
            dots[i] = ImageView(this)
            if(i == currentPosition){
                dots[i]!!.setImageResource(R.drawable.active_dots)
            }else{
                dots[i]!!.setImageResource(R.drawable.default_dots)
            }

            var param: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            param.setMargins(4,0,4,0)
            layoutDot.addView(dots[i], param)

        }

    }
}
