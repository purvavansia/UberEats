package com.example.purva.ubereats.ui.address

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import android.widget.*
import com.example.purva.ubereats.MyComponent

import com.example.purva.ubereats.R
import com.example.purva.ubereats.SharedPrefencesModule
import com.example.purva.ubereats.ui.location.MapsActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderApi
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.ui.PlacePicker
import java.util.*
import javax.inject.Inject

class AddressActivity : AppCompatActivity() , GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    lateinit var googleApiClient:GoogleApiClient
    lateinit var fusedLocationProviderApi: FusedLocationProviderApi
    lateinit var locationRequest: LocationRequest
    lateinit var address: EditText
    val REQUEST_CODE = 7
    lateinit var currentLoc: TextView
    /* @Inject
    internal var sharedPreferences: SharedPreferences? = null*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        address= findViewById(R.id.editTextAddress)
        var enter: ImageView = findViewById(R.id.enter)
        var summary : Button = findViewById(R.id.buttonOrderSummary)
        var sharedPreferences: SharedPreferences = getSharedPreferences("myfile", Context.MODE_PRIVATE)
        val iAddressPresenter: IAddressPresenter
        iAddressPresenter = AddressPresenter(this)
        currentLoc = findViewById(R.id.currentAddr)
        googleApiClient = GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build()
        fusedLocationProviderApi = LocationServices.FusedLocationApi
        locationRequest = LocationRequest()
        locationRequest.setInterval(30000)
        locationRequest.setFastestInterval(5000)
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        //locationRequest.setPriority(locationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
        /* var myComponent: MyComponent
        myComponent = DaggerMyComponent.builder().sharedPrefModule(SharedPrefencesModule(this)).build()*/

        summary.setOnClickListener(View.OnClickListener { iAddressPresenter.onClickSummary() })
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



    }
    protected override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent) {
        try {
            if (data != null) {
                super.onActivityResult(requestCode, resultCode, data)
                if (requestCode == REQUEST_CODE) {
                    if (resultCode == RESULT_OK) {
                        val place = PlacePicker.getPlace(data, this)
                        val addressnew = String.format("Place: " + place.getAddress())
                        address.setText("" + place.getAddress())
                        Toast.makeText(this, addressnew, Toast.LENGTH_LONG).show()
                    }

                }
            } else {
                Log.i("place picker", "no data")
            }
        }catch (e : Exception){
            Log.i("place picker", "no data")
        }
    }

    override fun onConnected(p0: Bundle?) {
        myrequestLocationUpdate()
    }

    private fun myrequestLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !== PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            // ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            // public void onRequestPermissionsResult(int requestCode, String[] permissions,
            // int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
    }
    protected override fun onStart() {
        super.onStart()
        googleApiClient.connect()
    }
    protected override fun onResume() {
        super.onResume()
        if (googleApiClient.isConnected())
        {
            myrequestLocationUpdate()
        }
    }
    protected override fun onPause() {
        super.onPause()
    }
    protected override fun onStop() {
        super.onStop()
        googleApiClient.disconnect()
    }
    protected override fun onDestroy() {
        super.onDestroy()
    }
    override fun onConnectionSuspended(i:Int) {
    }
    override fun onConnectionFailed(@NonNull connectionResult: ConnectionResult) {
    }
    override fun onLocationChanged(location: Location) {
        /*mylat = location.getLatitude()
        mylong = location.getLongitude()
        tvLongitude.setText("Longitude: " + mylong)
        tvLatitude.setText("Lantitude: " + mylat)*/

       Toast.makeText(this, ""+location.latitude+" "+location.longitude, Toast.LENGTH_LONG).show()
        val geocoder: Geocoder
        val yourAddresses:List<Address>
        try {
            geocoder = Geocoder(this, Locale.getDefault())
            yourAddresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

            if (yourAddresses.size > 0) {
                val yourAddress = yourAddresses.get(0).getAddressLine(0)
                val yourCity = yourAddresses.get(0).getAddressLine(1)
                val yourCountry = yourAddresses.get(0).getAddressLine(2)
                Toast.makeText(this, "" + yourAddress + " " + yourCity, Toast.LENGTH_LONG).show()

                currentLoc.setText(/*"2580 Foxfield Rd # 102, St. Charles, IL 60174"*/"" + yourAddress + " " + yourCity + location.latitude + " " + location.longitude)
                currentLoc.setOnClickListener { address.setText(currentLoc.text.toString()) }
            }
        }catch (e : Exception){
            Log.i("exception:",""+e)
        }
    }
}
