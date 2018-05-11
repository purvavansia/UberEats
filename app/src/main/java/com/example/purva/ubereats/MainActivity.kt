package com.example.purva.ubereats

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    // SharedPreferences sharedPreferences;
   /* @Inject
    internal var sharedPreferences: SharedPreferences? = null*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var myComponent: MyComponent
        /*myComponent = DaggerMyComponent.builder().sharedPrefModule(SharedPrefencesModule(this)).build()
        myComponent.inject(this)*/
       /* sharedPreferences?.getString("deliveryaddress", "")*/

    }
}
