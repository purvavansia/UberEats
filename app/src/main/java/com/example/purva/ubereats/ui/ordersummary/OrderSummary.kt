package com.example.purva.ubereats.ui.ordersummary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.example.purva.ubereats.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData

class OrderSummary : AppCompatActivity(), IOrderSummaryView {


    internal lateinit var iOrderSummaryPresenter: IOrderSummaryPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)

        iOrderSummaryPresenter = OrderSummaryPresenter(this, this)
        iOrderSummaryPresenter.callApi()


    }

    override fun displayChart() {
        val chart = findViewById<View>(R.id.chart) as BarChart

        val data = BarData(iOrderSummaryPresenter.getXAxisValue(), iOrderSummaryPresenter.getDataset())
        chart.data = data
        chart.setDescription("Order Summary Chart")
        chart.animateXY(2000, 2000)
        chart.invalidate()
    }

}

