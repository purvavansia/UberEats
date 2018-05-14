package com.example.purva.ubereats.ui.foodlist;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.purva.ubereats.base.BasePresenter;
import com.example.purva.ubereats.base.BaseView;
import com.example.purva.ubereats.data.model.Food;

public interface FoodListContract {

    interface IView extends BaseView<IPresenter> {
        void initRecyclerView();
    }


    interface IPresenter extends BasePresenter {
//        List<Food>  getFoodList();

        void rvItemClick(View v, int position, Food food, FragmentActivity activity);
    }
}
