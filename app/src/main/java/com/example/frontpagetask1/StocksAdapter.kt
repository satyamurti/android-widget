package com.example.frontpagetask1

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class StockAdapter(
    private val context: Context,
    private val stocks: List<Stock>,
) : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.stock_item, parent, false)
        return StockViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stocks.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = stocks[position]
        holder.stockName.text = stock.name
        holder.stockLabel.text = stock.label
        holder.stockPrice.text = stock.tick.p.toString()
        if(stock.tick.dp < 0){
            holder.stockDip.setTextColor(Color.parseColor("#fc3d38"))
            holder.stockDip.text = stock.tick.dp.toString() + "%"
        }else{
            holder.stockDip.setTextColor(Color.parseColor("#5dd56e"))
            holder.stockDip.text = "+" + stock.tick.dp.toString() + "%"
        }



        val leftAxis = holder.stockChart.axisLeft
        leftAxis.setDrawLabels(false)
        val rightAxis = holder.stockChart.axisRight
        rightAxis.setDrawLabels(false)
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        var entries = mutableListOf<Entry>()
        stock.ticks.forEach {
            val entry = Entry(it.t.toFloat(), it.p.toFloat())
            d("SPD",entry.toString())
            entries.add(entry)
        }
        val xAxis = holder.stockChart.xAxis
        xAxis.setDrawLabels(false)
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return simpleDateFormat.format(Date(value.toLong()))
            }
        }
        val lineDataSet = LineDataSet(entries, "Stock Prices")
        xAxis.gridLineWidth = 0f
        leftAxis.gridLineWidth = 0f
        val colors = ArrayList<Int>()
        for (i in 1 until stock.ticks.size) {
            if (stock.ticks[i].p > stock.ticks[i - 1].p) {
                colors.add(Color.GREEN)
            } else {
                colors.add(Color.RED)
            }
        }
        lineDataSet.colors = colors
        holder.stockChart.description.isEnabled = false
        holder.stockChart.legend.isEnabled = false
        holder.stockChart.setDrawGridBackground(false)
        holder.stockChart.setDrawBorders(false)
        holder.stockChart.invalidate()
        holder.stockChart.setTouchEnabled(false)
        holder.stockChart.setPinchZoom(false)
        holder.stockChart.setScaleEnabled(false)
        holder.stockChart.setDrawBorders(false)
        holder.stockChart.xAxis.setDrawLabels(false)
        holder.stockChart.xAxis.setDrawAxisLine(false)
        holder.stockChart.xAxis.setDrawGridLines(false)
        holder.stockChart.axisLeft.setDrawLabels(false)
        holder.stockChart.axisLeft.setDrawAxisLine(false)
        holder.stockChart.axisLeft.setDrawGridLines(false)
        holder.stockChart.axisRight.setDrawLabels(false)
        holder.stockChart.axisRight.setDrawAxisLine(false)
        holder.stockChart.axisRight.setDrawGridLines(false)
        holder.stockChart.legend.isEnabled = false
        lineDataSet.setDrawCircleHole(false)
        lineDataSet.setDrawCircles(false)
        holder.stockChart.data = LineData(lineDataSet)
    }

    inner class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stockName: TextView = itemView.findViewById(R.id.stock_name1)
        val stockLabel: TextView = itemView.findViewById(R.id.stock_label1)
        val stockPrice: TextView = itemView.findViewById(R.id.stock_price1)
        val stockDip: TextView = itemView.findViewById(R.id.stock_dip1)
        val stockChart: LineChart = itemView.findViewById(R.id.stock_chart)
    }
}
