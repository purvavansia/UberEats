package com.example.purva.ubereats.ui.foodlist

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.purva.ubereats.R
import com.example.purva.ubereats.data.adapter.Pager

class FoodListActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.pager)

        tabLayout.addTab(tabLayout.newTab().setText("Non-Veg"))
        tabLayout.addTab(tabLayout.newTab().setText("Veg"))


        val adapter = Pager(supportFragmentManager, tabLayout.tabCount)

        viewPager.adapter = adapter

        tabLayout.setOnTabSelectedListener(this)

        //  tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000000"));

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                tabLayout.setScrollPosition(position, 0f, true)
                tabLayout.isSelected = true
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        viewPager.currentItem = tab.position
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {

    }

    override fun onTabReselected(tab: TabLayout.Tab) {

    }
}
