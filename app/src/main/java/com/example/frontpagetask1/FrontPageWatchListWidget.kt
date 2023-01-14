package com.example.frontpagetask1

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Color
import android.util.Log.d
import android.widget.RemoteViews
import com.github.mikephil.charting.data.Entry
import com.google.gson.Gson


class FrontPageWatchListWidget : AppWidgetProvider() {
    private val handler = android.os.Handler()
    private var currentIndex = 0
    private var stocks = listOf<Stock>()
    val previousValues = mutableListOf<Entry>()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            stocks = fetchData(context)
            val updateData = object : Runnable {
                override fun run() {
                    for(i in 0..3){
                        stocks[i].tick = stocks[i].ticks[currentIndex]
                        currentIndex++
                        if(currentIndex == stocks.first().ticks.size) currentIndex = 0
                    }
                    updateAppWidget(context, appWidgetManager, appWidgetId, stocks)

                    handler.postDelayed(this, 500)

                }
            }
            handler.post(updateData)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
fun fetchData(context: Context): List<Stock> {
    val jsonString = context.resources.openRawResource(R.raw.sample_stocks)
        .bufferedReader().use { it.readText() }
    val gson = Gson()
    val stockWrapper = gson.fromJson(jsonString, StockWrapper::class.java)
    val stocks = stockWrapper.stocks
    return stocks.toList()
}
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    stocks: List<Stock>
) {

    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.stock_widget_item)
    views.setTextViewText(R.id.stock_name1,  stocks[0].name)
    views.setTextViewText(R.id.stock_name2, stocks[1].name)
    views.setTextViewText(R.id.stock_name3, stocks[2].name)
    views.setTextViewText(R.id.stock_name3, stocks[3].name)
    views.setTextViewText(R.id.stock_label1, stocks[0].label)
    views.setTextViewText(R.id.stock_label2, stocks[1].label)
    views.setTextViewText(R.id.stock_label3, stocks[2].label)
    views.setTextViewText(R.id.stock_label4, stocks[3].label)
    views.setTextViewText(R.id.stock_price1, stocks[0].tick.p.toString())
    views.setTextViewText(R.id.stock_price2, stocks[1].tick.p.toString())
    views.setTextViewText(R.id.stock_price3, stocks[2].tick.p.toString())
    views.setTextViewText(R.id.stock_price4, stocks[3].tick.p.toString())

    if(stocks[0].tick.dp < 0){
        views.setTextColor(R.id.stock_dip1, Color.parseColor("#fc3d38"))
        views.setTextViewText(R.id.stock_dip1, stocks[0].tick.dp.toString() + "%")
    }else{
        views.setTextColor(R.id.stock_dip1, Color.parseColor("#5dd56e"))
        views.setTextViewText(R.id.stock_dip1, "+" + stocks[0].tick.dp.toString() + "%")
    }

    if(stocks[1].tick.dp < 0){
        views.setTextColor(R.id.stock_dip2, Color.parseColor("#fc3d38"))
        views.setTextViewText(R.id.stock_dip2, stocks[1].tick.dp.toString() + "%")
    }else{
        views.setTextColor(R.id.stock_dip2, Color.parseColor("#5dd56e"))
        views.setTextViewText(R.id.stock_dip2, "+" + stocks[1].tick.dp.toString() + "%")
    }

    if(stocks[2].tick.dp < 0){
        views.setTextColor(R.id.stock_dip3, Color.parseColor("#fc3d38"))
        views.setTextViewText(R.id.stock_dip3, stocks[2].tick.dp.toString() + "%")
    }else{
        views.setTextColor(R.id.stock_dip3, Color.parseColor("#5dd56e"))
        views.setTextViewText(R.id.stock_dip3, "+" + stocks[2].tick.dp.toString() + "%")
    }

    if(stocks[3].tick.dp < 0){
        views.setTextColor(R.id.stock_dip4, Color.parseColor("#fc3d38"))
        views.setTextViewText(R.id.stock_dip4, stocks[3].tick.dp.toString() + "%")
    }else{
        views.setTextColor(R.id.stock_dip4, Color.parseColor("#5dd56e"))
        views.setTextViewText(R.id.stock_dip4, "+" + stocks[3].tick.dp.toString() + "%")
    }
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}