package com.example.frontpagetask1

class StockWrapper(val stocks: List<Stock>)

data class Stock(
    val id: Int,
    val name: String,
    val label: String,
    var tick: Tick,
    val ticks: List<Tick>
)

data class Tick(
    var p: Double,
    var dp: Double,
    var t: Long
)
