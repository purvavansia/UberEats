package com.example.purva.ubereats.ui.fooddetail

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

import com.example.purva.ubereats.R
import com.example.purva.ubereats.database.DbHelper
import com.example.purva.ubereats.database.IDbHelper
import com.example.purva.ubereats.ui.checkout.CheckoutActivity
import com.squareup.picasso.Picasso

class FoodDetailActivity : AppCompatActivity(), IFoodDetailView {

    internal var totalnum: Int = 0
    internal var quantity: Int = 0
    internal var foodpri: Int = 0
    internal var recepiee: String? = null
    internal var image: String? = null
    internal var price: String? = null
    internal var foodid: String? = null
    internal var foodname: String? = null
    lateinit var radioGroup: RadioGroup
    lateinit var imageView: ImageView
    lateinit var recepieeTxt: TextView
    lateinit var totalTxt: TextView

    lateinit var sharedPreferences: SharedPreferences
    lateinit var addtocartBtn: Button
    internal var iPresenter: IFoodDetailPresenter = FoodDetailPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        var numberPicker: NumberPicker = findViewById(R.id.numberPicker)

        imageView = findViewById(R.id.foodImage)
        recepieeTxt = findViewById(R.id.receipieedetail)

        totalTxt = findViewById(R.id.total)
        addtocartBtn = findViewById(R.id.addtocart_btn)
        radioGroup = findViewById(R.id.flavor)


        sharedPreferences = getSharedPreferences("myfile", Context.MODE_PRIVATE)
        recepiee = sharedPreferences.getString("foodrecepiee", "")
        image = sharedPreferences.getString("foodimage", "")
        price = sharedPreferences.getString("foodprice", "")
        foodid = sharedPreferences.getString("foodid", "")
        foodname = sharedPreferences.getString("foodname", "")
        foodpri = Integer.valueOf(price)

        numberPicker.minValue = 1
        numberPicker.maxValue = 20
        val onValueChangeListener = NumberPicker.OnValueChangeListener { numberPicker, i, i1 ->
            quantity = numberPicker.value
            totalnum = quantity * foodpri
            totalTxt.text = "Total: $totalnum"
        }
        numberPicker.setOnValueChangedListener(onValueChangeListener)

        Picasso.with(this).load(image).into(imageView)
        recepieeTxt.text = recepiee


        addtocartBtn.setOnClickListener {
            val rb = findViewById<View>(radioGroup.checkedRadioButtonId) as RadioButton

            val flavor = rb.text.toString()

            iPresenter.addTocart(foodid.toString(), foodname.toString(), foodpri.toString(), flavor, quantity.toString(), image.toString())

            val intent = Intent(this@FoodDetailActivity, CheckoutActivity::class.java)
            startActivity(intent)
        }

    }


    override fun showFoodDetail() {


    }

    override fun setNumberPicker() {




    }
}
