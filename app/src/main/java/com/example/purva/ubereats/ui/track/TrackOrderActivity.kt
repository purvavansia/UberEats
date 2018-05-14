package com.example.purva.ubereats.ui.track

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.purva.ubereats.R
import com.example.purva.ubereats.ui.checkout.CheckoutActivity
import com.example.purva.ubereats.ui.foodlist.FoodListActivity
import com.example.purva.ubereats.ui.orderhistory.OrderHistoryActivity
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu
import com.squareup.picasso.Picasso

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class TrackOrderActivity : AppCompatActivity() {
    lateinit var orderstatus: String
    internal var orderId: String? = null
    lateinit var statusTxt: TextView
    lateinit var statuspic: ImageView
    lateinit var materialDesignFAM: FloatingActionMenu
    lateinit var floatingActionButton1: FloatingActionButton
    lateinit var floatingActionButton2: FloatingActionButton
    lateinit var floatingActionButton3: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_order)

        orderId = intent.extras!!.getString("orderid")

        statusTxt = findViewById(R.id.statusTxt)
        statuspic = findViewById(R.id.statuspic)

        getOrderStatus()
        materialDesignFAM = findViewById<View>(R.id.material_design_android_floating_action_menu) as FloatingActionMenu
        floatingActionButton1 = findViewById<View>(R.id.material_design_floating_action_menu_item1) as FloatingActionButton
        floatingActionButton2 = findViewById<View>(R.id.material_design_floating_action_menu_item2) as FloatingActionButton
        floatingActionButton3 = findViewById<View>(R.id.material_design_floating_action_menu_item3) as FloatingActionButton

        floatingActionButton1.setOnClickListener {
            //TODO something when floating action menu first item clicked
            val intent = Intent(this@TrackOrderActivity, FoodListActivity::class.java)
            startActivity(intent)
        }
        floatingActionButton2.setOnClickListener {
            //TODO something when floating action menu second item clicked
            val intent = Intent(this@TrackOrderActivity, CheckoutActivity::class.java)
            startActivity(intent)
        }
        floatingActionButton3.setOnClickListener {
            //TODO something when floating action menu third item clicked

            val intent = Intent(this@TrackOrderActivity, OrderHistoryActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getOrderStatus() {
        val request = JsonObjectRequest("http://rjtmobile.com/ansari/fos/fosapp/order_track.php?&order_id=" + orderId!!, null, Response.Listener { response ->
            try {
                val resarray = response.getJSONArray("Order Detail")
                val orderdetail = resarray.getJSONObject(0)
                orderstatus = orderdetail.getString("OrderStatus")


            } catch (e: JSONException) {
                e.printStackTrace()
            }



            if (orderstatus == "1") {
                statusTxt.text = "We are Packing your food"
                Picasso.with(this@TrackOrderActivity).load(R.drawable.foodpacking).into(statuspic)
            } else if (orderstatus == "2") {
                statusTxt.text = "Your food is on the way"
                Picasso.with(this@TrackOrderActivity).load(R.drawable.deliverfood).into(statuspic)

            } else if (orderstatus == "2") {
                statusTxt.text = "Your food is delivered"
                Picasso.with(this@TrackOrderActivity).load(R.drawable.icon_delivery).into(statuspic)
            }
        }, Response.ErrorListener { })

        Volley.newRequestQueue(this@TrackOrderActivity).add(request)
    }
}
