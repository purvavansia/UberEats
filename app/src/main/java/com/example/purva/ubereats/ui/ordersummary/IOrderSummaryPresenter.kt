package com.example.purva.ubereats.ui.ordersummary

import com.github.mikephil.charting.data.BarDataSet

import java.util.ArrayList

interface IOrderSummaryPresenter {
    fun callApi()
    fun getXAxisValue():ArrayList<String>
    fun getDataset():ArrayList<BarDataSet>

}
