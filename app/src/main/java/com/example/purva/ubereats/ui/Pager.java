package com.example.purva.ubereats.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class Pager extends FragmentStatePagerAdapter {
    int tabCount;


    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount  = tabCount;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                TabNonVegFoodList tab1 = new TabNonVegFoodList();
                return tab1;
            case 1:
                TabVegFoodList tab2 = new TabVegFoodList();
                return tab2;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
