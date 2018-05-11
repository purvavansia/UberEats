package com.example.purva.ubereats.ui.fooddetail

import android.content.Context

import com.example.purva.ubereats.database.DbHelper
import com.example.purva.ubereats.database.IDbHelper
import com.example.purva.ubereats.model.Food
import com.example.purva.ubereats.ui.foodlist.FoodListContract

class FoodDetailPresenter(internal var context: Context, internal var iFoodDetailView: IFoodDetailView) : IFoodDetailPresenter {


    init {
        iFoodDetailView.setNumberPicker()
        iFoodDetailView.showFoodDetail()

    }

    override fun addTocart(foodid: String, foodname: String, foodprice: String, flavor: String, quantity: String, image: String) {

        val iDbHelper = DbHelper(context)
        iDbHelper.insertCartRecord(foodid, foodname, foodprice, flavor, quantity, image)

    }

}
