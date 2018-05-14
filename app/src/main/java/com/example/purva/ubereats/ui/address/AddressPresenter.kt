package com.example.purva.ubereats.ui.address

import android.content.Context
import android.content.Intent

import com.example.purva.ubereats.ui.location.MapsActivity
import com.example.purva.ubereats.ui.ordersummary.OrderSummary

class AddressPresenter(internal var context: Context) : IAddressPresenter {
    override fun onClickSummary() {
        val intent = Intent(context, OrderSummary::class.java)
        context.startActivity(intent)
    }

    override fun onClickEnter() {
        val intent = Intent(context, MapsActivity::class.java)
        context.startActivity(intent)
    }

}
