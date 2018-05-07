package com.example.purva.ubereats.ui;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.purva.ubereats.R;

public class FoodListActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
TabLayout tabLayout;
ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.pager);

        tabLayout.addTab(tabLayout.newTab().setText("Non-Veg"));
        tabLayout.addTab(tabLayout.newTab().setText("Veg"));


        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);

        tabLayout.setOnTabSelectedListener(this);

      //  tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000000"));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabLayout.setScrollPosition(position,0,true);
                tabLayout.setSelected(true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
       viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
