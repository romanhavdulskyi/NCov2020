package com.demo.app.ncov2020.common

import android.graphics.Color

object ColorUtils {

    fun genColor(intensively : Double) : Int
    {
        val colors = floatArrayOf(0.0f, 100f, 102 - intensively.toFloat() * 100)
        return Color.HSVToColor(colors)
    }
}