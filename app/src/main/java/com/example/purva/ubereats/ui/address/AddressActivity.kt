package com.example.purva.ubereats.ui.address

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.purva.ubereats.MyComponent

import com.example.purva.ubereats.R
import com.example.purva.ubereats.SharedPrefencesModule
import com.example.purva.ubereats.ui.location.MapsActivity
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlacePicker
import java.util.*
import javax.inject.Inject

class AddressActivity : AppCompatActivity() {

    lateinit var address: EditText
    val REQUEST_CODE = 7
    /* @Inject
    internal var sharedPreferences: SharedPreferences? = null*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        address= findViewById(R.id.editTextAddress)
        var enter: ImageView = findViewById(R.id.enter)
        var sharedPreferences: SharedPreferences = getSharedPreferences("myfile", Context.MODE_PRIVATE)
        val iAddressPresenter: IAddressPresenter
        iAddressPresenter = AddressPresenter(this)
        /* var myComponent: MyComponent
        myComponent = DaggerMyComponent.builder().sharedPrefModule(SharedPrefencesModule(this)).build()*/

        address.setOnLongClickListener(View.OnLongClickListener {
            val builder = PlacePicker.IntentBuilder()
            try {
                val placeintent = builder.build(this@AddressActivity)
                startActivityForResult(placeintent, REQUEST_CODE)
            } catch (e: GooglePlayServicesRepairableException) {
                e.printStackTrace()
            } catch (e: GooglePlayServicesNotAvailableException) {
                e.printStackTrace()
            }

            true
        })
        enter.setOnClickListener {
            if(address.text.toString().isEmpty()){
                Toast.makeText(this,"Please select an address to deliver",Toast.LENGTH_LONG).show();
            }
            else {
                val deliveryAddress = address.text.toString()
                var editor: SharedPreferences.Editor? = sharedPreferences?.edit()
                editor!!.putString("deliveryaddress", deliveryAddress)
                editor!!.commit()
                iAddressPresenter.onClickEnter()
            }
        }
        val currentLoc: TextView = findViewById(R.id.currentAddr)
        currentLoc.setText("2580 Foxfield Rd # 102, St. Charles, IL 60174")
        currentLoc.setOnClickListener { address.setText(currentLoc.text.toString()) }

    }
    protected override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                val place = PlacePicker.getPlace(data, this)
                val addressnew = String.format("Place: " + place.getAddress())
                address.setText("" + place.getAddress())
                Toast.makeText(this, addressnew, Toast.LENGTH_LONG).show()
            }
        }
    }

   /* override fun onStart() {
        super.onStart()
        val geocoder: Geocoder
        val yourAddresses:List<Address>
        geocoder = Geocoder(this, Locale.getDefault())
        yourAddresses = geocoder.getFromLocation(65.964962, -139.280554, 1)
        if (yourAddresses.size > 0)
        {
            val yourAddress = yourAddresses.get(0).getAddressLine(0)
            val yourCity = yourAddresses.get(0).getAddressLine(1)
            val yourCountry = yourAddresses.get(0).getAddressLine(2)
            Toast.makeText(this, "" + yourAddress +" "+yourCity, Toast.LENGTH_LONG).show()
        }
    }*/
}
