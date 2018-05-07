package com.example.purva.ubereats;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.purva.ubereats.model.Food;

import java.util.List;

public interface FoodListContract {

    interface IView extends BaseView<FoodListContract.IPresenter>{
        void initRecyclerView();
    }


    interface IPresenter extends BasePresenter{
        List<Food>  getFoodList();

        void rvItemClick(View v, int position, Food food, FragmentActivity activity);
    }
}
