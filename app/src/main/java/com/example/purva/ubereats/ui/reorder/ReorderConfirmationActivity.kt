package com.example.purva.ubereats.ui.reorder

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.purva.ubereats.R
import com.example.purva.ubereats.ui.checkout.CheckoutActivity
import com.example.purva.ubereats.ui.foodlist.FoodListActivity
import com.example.purva.ubereats.ui.orderhistory.OrderHistoryActivity
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.text.SimpleDateFormat
import java.util.Calendar

class ReorderConfirmationActivity : AppCompatActivity() {
    lateinit var foodNameTxt: TextView
    lateinit var foodAddrTxt: TextView
    lateinit var foodTotalTxt: TextView
    lateinit var foodDateTxt: TextView
    lateinit var imageView: ImageView
    lateinit var formattedDate: String
    lateinit var getOrderId: String
    lateinit var sharedPreferences: SharedPreferences

    lateinit var materialDesignFAM: FloatingActionMenu
    lateinit var floatingActionButton1: FloatingActionButton
    lateinit var floatingActionButton2: FloatingActionButton
    lateinit var floatingActionButton3: FloatingActionButton


    internal var item_name: String? = null
    internal var item_addr: String? = null
    internal var item_total: String? = null
    internal var item_price: String? = null
    internal var item_quantity: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reorder_confirmation)


        foodNameTxt = findViewById(R.id.foodName)
        foodAddrTxt = findViewById(R.id.foodAddr)
        foodTotalTxt = findViewById(R.id.foodTotal)
        foodDateTxt = findViewById(R.id.foodDate)
        imageView = findViewById(R.id.iv_qr)
        sharedPreferences = getSharedPreferences("myfile", Context.MODE_PRIVATE)

        item_name = sharedPreferences.getString("item_name", "")
        item_price = sharedPreferences.getString("item_price", "")
        item_addr = sharedPreferences.getString("item_address", "")
        item_total = sharedPreferences.getString("item_total", "")
        item_quantity = sharedPreferences.getString("item_quantity", "")


        //floating action button
        materialDesignFAM = findViewById<View>(R.id.material_design_android_floating_action_menu) as FloatingActionMenu
        floatingActionButton1 = findViewById<View>(R.id.material_design_floating_action_menu_item1) as FloatingActionButton
        floatingActionButton2 = findViewById<View>(R.id.material_design_floating_action_menu_item2) as FloatingActionButton
        floatingActionButton3 = findViewById<View>(R.id.material_design_floating_action_menu_item3) as FloatingActionButton

        floatingActionButton1.setOnClickListener {
            val intent = Intent(this@ReorderConfirmationActivity, FoodListActivity::class.java)
            startActivity(intent)
        }
        floatingActionButton2.setOnClickListener {
            val intent = Intent(this@ReorderConfirmationActivity, CheckoutActivity::class.java)
            startActivity(intent)
        }
        floatingActionButton3.setOnClickListener {
            val intent = Intent(this@ReorderConfirmationActivity, OrderHistoryActivity::class.java)
            startActivity(intent)
        }




        orderRequest()


    }

    private fun orderRequest() {

        val c = Calendar.getInstance()
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        formattedDate = df.format(c.time)

        val request = StringRequest(Request.Method.GET, "http://rjtmobile.com/ansari/fos/fosapp/order_request.php?&order_category=veg_nonveg" +
                "&order_name=" + item_name + "&order_quantity=" + item_quantity + "&total_order=" + item_total + "&order_delivery_add=" + item_addr +
                "&order_date=" + formattedDate + "&user_phone=6174879092",
                Response.Listener { response ->
                    if (response.contains("confirmed")) {
                        Toast.makeText(this@ReorderConfirmationActivity, "Order is Placed!", Toast.LENGTH_LONG).show()

                        val responsesplit = response.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        getOrderId = responsesplit[responsesplit.size - 1]

                        Log.i("test", "order id is $getOrderId")

                        secondCall()
                    }
                }, Response.ErrorListener { error -> Log.i("error1", error.toString()) })

        Volley.newRequestQueue(this@ReorderConfirmationActivity).add(request)


    }


    fun secondCall() {

        val confirmationrequest = JsonObjectRequest("http://rjtmobile.com/ansari/fos/fosapp/order_confirmation.php?&order_id=$getOrderId", null, Response.Listener { response ->
            try {
                val result = response.getJSONArray("Order Detail")
                val confirmedorder = result.get(0) as JSONObject
                val orderId = confirmedorder.getString("OrderId")

                val ordername = confirmedorder.getString("OrderName")
                Log.i("test", "order name:$ordername")
                val orderquantity = confirmedorder.getString("OrderQuantity")
                Log.i("test", "order quantity is$orderquantity")
                val orderTotal = confirmedorder.getString("TotalOrder")
                val orderAddr = confirmedorder.getString("OrderDeliverAdd")
                val orderDate = confirmedorder.getString("OrderDate")

                foodNameTxt.text = ordername
                foodAddrTxt.text = "Address: $orderAddr"
                foodDateTxt.text = "Date: $orderDate"
                foodTotalTxt.text = "Total: $orderTotal"
                val multiFormatWriter = MultiFormatWriter()

                var bitMatrix: BitMatrix? = null
                try {
                    bitMatrix = multiFormatWriter.encode("$formattedDate $getOrderId", BarcodeFormat.QR_CODE, 200, 200)
                } catch (e: WriterException) {
                    e.printStackTrace()
                }

                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.createBitmap(bitMatrix!!)

                imageView.setImageBitmap(bitmap)


            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { })
        Volley.newRequestQueue(this@ReorderConfirmationActivity).add(confirmationrequest)


    }
}
