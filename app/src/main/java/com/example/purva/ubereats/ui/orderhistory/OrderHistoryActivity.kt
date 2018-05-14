package com.example.purva.ubereats.ui.orderhistory

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View

import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.example.purva.ubereats.R
import com.example.purva.ubereats.data.adapter.OrderHistoryAdapter
import com.example.purva.ubereats.data.model.Order
import com.example.purva.ubereats.ui.checkout.CheckoutActivity
import com.example.purva.ubereats.ui.confirm.OrderConfirmationctivity
import com.example.purva.ubereats.ui.foodlist.FoodListActivity
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu


import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

class OrderHistoryActivity : AppCompatActivity() {
    lateinit var orderList: MutableList<Order>
    lateinit var recyclerView: RecyclerView
    lateinit var sharedPreferences: SharedPreferences
     var streetAddr: String? = null

    lateinit var materialDesignFAM: FloatingActionMenu
    lateinit var floatingActionButton1: FloatingActionButton
    lateinit var floatingActionButton2: FloatingActionButton
    lateinit var floatingActionButton3: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)
        orderList = ArrayList()
        recyclerView = findViewById(R.id.rv_orders)
        sharedPreferences = getSharedPreferences("myfile", Context.MODE_PRIVATE)
        streetAddr = sharedPreferences.getString("deliveryaddress", "")


        initRecyclerView()

        materialDesignFAM = findViewById<View>(R.id.material_design_android_floating_action_menu) as FloatingActionMenu
        floatingActionButton1 = findViewById<View>(R.id.material_design_floating_action_menu_item1) as FloatingActionButton
        floatingActionButton2 = findViewById<View>(R.id.material_design_floating_action_menu_item2) as FloatingActionButton
        floatingActionButton3 = findViewById<View>(R.id.material_design_floating_action_menu_item3) as FloatingActionButton

        floatingActionButton1.setOnClickListener {
            //TODO something when floating action menu first item clicked
            val intent = Intent(this@OrderHistoryActivity, FoodListActivity::class.java)
            startActivity(intent)
        }
        floatingActionButton2.setOnClickListener {
            //TODO something when floating action menu second item clicked
            val intent = Intent(this@OrderHistoryActivity, CheckoutActivity::class.java)
            startActivity(intent)
        }
        floatingActionButton3.setOnClickListener {
            //TODO something when floating action menu third item clicked

            val intent = Intent(this@OrderHistoryActivity, OrderHistoryActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initRecyclerView() {

        val request = JsonObjectRequest(
                "http://rjtmobile.com/ansari/fos/fosapp/order_recent.php?user_phone=6174879092", null,
                Response.Listener { response ->
                    try {
                        val orderArray = response.getJSONArray("Order Detail")
                        for (i in 0 until orderArray.length()) {
                            val getOrder = orderArray.getJSONObject(i)
                            val id = getOrder.getString("OrderId")
                            val name = getOrder.getString("OrderName")
                            val quantity = getOrder.getString("OrderQuantity")
                            val total = getOrder.getString("TotalOrder")
                            val status = getOrder.getString("OrderStatus")
                            val address = getOrder.getString("OrderDeliverAdd")
                            val date = getOrder.getString("OrderDate")

                            val order = Order(id, name, quantity, total, status, address, date)
                            orderList.add(order)

                        }


                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                    val orderHistoryAdapter = OrderHistoryAdapter(this@OrderHistoryActivity, orderList)

                    recyclerView.layoutManager = GridLayoutManager(this@OrderHistoryActivity, 1)

                    orderHistoryAdapter.notifyDataSetChanged()
                    recyclerView.adapter = orderHistoryAdapter
                }, Response.ErrorListener { error -> Log.i("test", error.toString()) })

        Volley.newRequestQueue(this@OrderHistoryActivity).add(request)


    }
}
