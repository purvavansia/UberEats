package com.example.purva.ubereats.ui.address

import android.content.Context
import android.content.Intent

import com.example.purva.ubereats.ui.location.MapsActivity

class AddressPresenter(internal var context: Context) : IAddressPresenter {

    override fun onClickEnter() {
        val intent = Intent(context, MapsActivity::class.java)
        context.startActivity(intent)
    }

}
