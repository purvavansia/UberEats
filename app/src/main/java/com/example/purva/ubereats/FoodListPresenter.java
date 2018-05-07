package com.example.purva.ubereats;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.purva.ubereats.model.Food;

import java.util.List;

public class FoodListPresenter implements FoodListContract.IPresenter{
    @Override
    public List<Food> getFoodList() {
        return null;
    }

    @Override
    public void rvItemClick(View v, int position, Food food, FragmentActivity activity) {

    }

    @Override
    public void start() {

    }
}
