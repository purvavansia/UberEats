package com.example.purva.ubereats.ui.reorder

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.RelativeLayout
import android.widget.TextView

import com.example.purva.ubereats.R
import com.example.purva.ubereats.ui.checkout.CheckoutActivity
import com.example.purva.ubereats.ui.foodlist.FoodListActivity
import com.example.purva.ubereats.ui.orderhistory.OrderHistoryActivity
import com.example.purva.ubereats.ui.track.TrackOrderActivity
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu

class ReorderActivity : AppCompatActivity() {
    lateinit var numberPicker: NumberPicker
    lateinit var foodname_tv: TextView
    lateinit var address_et: EditText
    lateinit var sharedPreferences: SharedPreferences

    lateinit var materialDesignFAM: FloatingActionMenu
    lateinit var floatingActionButton1: FloatingActionButton
    lateinit var floatingActionButton2: FloatingActionButton
    lateinit var floatingActionButton3: FloatingActionButton
    lateinit var btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reorder)
        numberPicker = findViewById(R.id.numberPicker)
        foodname_tv = findViewById(R.id.foodname_tv)
        address_et = findViewById(R.id.address_et)
        btn = findViewById(R.id.btn)

        sharedPreferences = getSharedPreferences("myfile", Context.MODE_PRIVATE)
        val foodname = sharedPreferences.getString("item_name", "")
        foodname_tv.text = foodname!! + " reorder"


        numberPicker.minValue = 1
        numberPicker.maxValue = 20

        // int num = numberPicker.getValue();
        btn.setOnClickListener {
            val num = numberPicker.value
            val quantity = num.toString()
            sharedPreferences.edit().putString("item_quantity", quantity)

            val price = sharedPreferences.getString("item_price", "")
            val intprice = Integer.valueOf(price)
            val total = intprice * num
            val address = address_et.text.toString()

            sharedPreferences.edit().putString("item_total", total.toString()).commit()
            sharedPreferences.edit().putString("item_address", address).commit()

            val intent = Intent(this@ReorderActivity, ReorderConfirmationActivity::class.java)
            startActivity(intent)
        }

        materialDesignFAM = findViewById<View>(R.id.material_design_android_floating_action_menu) as FloatingActionMenu
        floatingActionButton1 = findViewById<View>(R.id.material_design_floating_action_menu_item1) as FloatingActionButton
        floatingActionButton2 = findViewById<View>(R.id.material_design_floating_action_menu_item2) as FloatingActionButton
        floatingActionButton3 = findViewById<View>(R.id.material_design_floating_action_menu_item3) as FloatingActionButton

        floatingActionButton1.setOnClickListener {
            //TODO something when floating action menu first item clicked
            val intent = Intent(this@ReorderActivity, FoodListActivity::class.java)
            startActivity(intent)
        }
        floatingActionButton2.setOnClickListener {
            //TODO something when floating action menu second item clicked
            val intent = Intent(this@ReorderActivity, CheckoutActivity::class.java)
            startActivity(intent)
        }
        floatingActionButton3.setOnClickListener {
            //TODO something when floating action menu third item clicked

            val intent = Intent(this@ReorderActivity, OrderHistoryActivity::class.java)
            startActivity(intent)
        }

    }


}
