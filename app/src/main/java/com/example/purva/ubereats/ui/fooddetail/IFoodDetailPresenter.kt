package com.example.purva.ubereats.ui.fooddetail

interface IFoodDetailPresenter {

    fun addTocart(id: String, name: String, price: String, flavor: String, quantity: String, image: String)
}
