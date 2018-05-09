package com.example.purva.ubereats.ui.address

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

import com.example.purva.ubereats.R
import com.example.purva.ubereats.ui.location.MapsActivity

class AddressActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        var address: EditText = findViewById(R.id.editTextAddress)
         var enter: ImageView = findViewById(R.id.enter)
         var sharedPreferences: SharedPreferences = getSharedPreferences("myfile", Context.MODE_PRIVATE)
        val iAddressPresenter:IAddressPresenter
        iAddressPresenter = AddressPresenter(this)


        enter.setOnClickListener {
            val deliveryAddress = address.text.toString()
            var editor: SharedPreferences.Editor? = sharedPreferences.edit()
            editor!!.putString("deliveryaddress", deliveryAddress)
            editor!!.commit()
            iAddressPresenter.onClickEnter()
        }

    }
}
