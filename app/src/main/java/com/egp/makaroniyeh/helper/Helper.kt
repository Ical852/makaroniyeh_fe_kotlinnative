package com.egp.makaroniyeh.helper

import java.text.DecimalFormat

object Helpers {

    fun getCurrencyIDR(price : Double) : String {
        val format = DecimalFormat("#,###,###")
        return "IDR "+format.format(price).replace(",".toRegex(), ".")
    }
}