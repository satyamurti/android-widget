package com.example.frontpagetask1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StockAdapter
    private var stocks = listOf<Stock>()
    private val handler = android.os.Handler()
    private var currentIndex = 0
    val previousValues = mutableListOf<Entry>()

    private val updateData = object : Runnable {
        override fun run() {
            stocks.forEach { stock ->
                stock.tick = stock.ticks[currentIndex]
                val newValue =  Entry(stock.tick.p.toFloat(), stock.tick.t.toFloat())
                previousValues.add(newValue)
                adapter.notifyDataSetChanged()
            }
            currentIndex++
            if(currentIndex == stocks.first().ticks.size) currentIndex = 0
            handler.postDelayed(this, 300)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stocks = fetchData(this)
        recyclerView = findViewById(R.id.stock_recyclerview)
        adapter = StockAdapter(this, stocks)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        handler.post(updateData)
    }
    fun fetchData(context: Context): List<Stock> {
        val jsonString = context.resources.openRawResource(R.raw.sample_stocks)
            .bufferedReader().use { it.readText() }
        val gson = Gson()
        val stockWrapper = gson.fromJson(jsonString, StockWrapper::class.java)
        val stocks = stockWrapper.stocks
        return stocks.toList()
    }

}