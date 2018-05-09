package com.example.purva.ubereats.ui.fooddetail;

import android.content.Context;

import com.example.purva.ubereats.database.DbHelper;
import com.example.purva.ubereats.database.IDbHelper;
import com.example.purva.ubereats.model.Food;
import com.example.purva.ubereats.ui.foodlist.FoodListContract;

public class FoodDetailPresenter implements IFoodDetailPresenter {
    IFoodDetailView iFoodDetailView;
    Context context;


    public FoodDetailPresenter(Context context,IFoodDetailView iView){
        this.context = context;
        this.iFoodDetailView = iView;
        iFoodDetailView.setNumberPicker();
        iFoodDetailView.showFoodDetail();

     }

    @Override
    public void addTocart(String foodid, String foodname, String foodprice, String flavor, String quantity,String image) {

        IDbHelper iDbHelper = new DbHelper(context);
        iDbHelper.insertCartRecord(foodid,foodname, foodprice,flavor,quantity,image);

    }

}
