package com.example.purva.ubereats.ui.ordersummary

import android.content.Context
import android.util.Log
import com.android.volley.Response

import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

class OrderSummaryPresenter(internal var context: Context, internal var iOrderSummaryView: IOrderSummaryView) : IOrderSummaryPresenter {
    internal var dates: ArrayList<String>
    internal var total: ArrayList<String>
    internal var length: Int = 0

    init {
        dates = ArrayList()
        total = ArrayList()
    }


    override fun callApi() {

        val confirmationrequest = JsonObjectRequest("http://rjtmobile.com/ansari/fos/fosapp/order_recent.php?user_phone=6174879092", null, com.android.volley.Response.Listener { response ->
            try {
                val result = response.getJSONArray("Order Detail")
                length = result.length()
                for (i in 0 until result.length()) {
                    val confirmedorder = result.get(i) as JSONObject
                    val orderId = confirmedorder.getString("OrderId")
                    val ordername = confirmedorder.getString("OrderName")
                    Log.i("test", "order name:$ordername")
                    val orderquantity = confirmedorder.getString("OrderQuantity")
                    val orderTotal = confirmedorder.getString("TotalOrder")
                    val orderStatus = confirmedorder.getString("OrderStatus")
                    val orderAddr = confirmedorder.getString("OrderDeliverAdd")
                    val orderDate = confirmedorder.getString("OrderDate")
                    Log.i("test", "order date$orderDate")
                    dates.add(orderDate)
                    total.add(orderTotal)

                }
                iOrderSummaryView.displayChart()

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { })
        Volley.newRequestQueue(context).add(confirmationrequest)


    }

    override fun getXAxisValue(): ArrayList<String> {

        val xAxis = ArrayList<String>()
        for (i in 0 until length) {
            xAxis.add(dates[i])
        }

        return xAxis
    }

    override fun getDataset(): ArrayList<BarDataSet> {

        var dataSets: ArrayList<BarDataSet>? = null
        val valueSet1 = ArrayList<BarEntry>()
        for (i in 0 until length) {
            valueSet1.add(BarEntry(Integer.parseInt(total[i]).toFloat(), i))
        }
        val barDataSet1 = BarDataSet(valueSet1, "Orders")
        barDataSet1.setColors(ColorTemplate.JOYFUL_COLORS)


        dataSets = ArrayList()
        dataSets.add(barDataSet1)

        return dataSets
    }
}
