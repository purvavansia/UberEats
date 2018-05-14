package com.example.purva.ubereats.ui.location

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.support.v4.app.FragmentActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast

import com.example.purva.ubereats.R
import com.example.purva.ubereats.ui.address.AddressActivity
import com.example.purva.ubereats.ui.foodlist.FoodListActivity

import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : FragmentActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    lateinit var sharedPreferences: SharedPreferences
    internal var streetAddr: String? = null
    lateinit var change: Button
    lateinit var okay: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        change = findViewById(R.id.buttonChangeAddr)
        okay = findViewById(R.id.buttonOkay)

        sharedPreferences = getSharedPreferences("myfile", Context.MODE_PRIVATE)
        streetAddr = sharedPreferences.getString("deliveryaddress", "")

        okay.setOnClickListener {
            val intent = Intent(this@MapsActivity, FoodListActivity::class.java)
            startActivity(intent)
        }
        change.setOnClickListener {
            val intent = Intent(this@MapsActivity, AddressActivity::class.java)
            startActivity(intent)
        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        /*   LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        try {
            val address = getLocationFromAddress(this, streetAddr)
            val markerOptions = MarkerOptions()
            markerOptions.draggable(true)
            mMap!!.addMarker(markerOptions.position(address).title("Deliver to")).isDraggable = true
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(address))
            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(address, 12.0f))
        } catch (e: Exception) {
            Log.i("exception", "" + e)
        }

    }

    fun getLocationFromAddress(context: Context, strAddress: String?): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var p1: LatLng? = null

        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location = address[0]
            location.latitude
            location.longitude

            p1 = LatLng(location.latitude, location.longitude)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return p1

    }

    companion object {

        val REQUEST_CODE = 7
    }
}
