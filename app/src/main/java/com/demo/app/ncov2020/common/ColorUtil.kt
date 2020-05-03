package com.demo.app.ncov2020.common

import android.graphics.Color
import android.graphics.Color.HSVToColor
import androidx.core.graphics.ColorUtils

object ColorUtil {

    fun genColor(intensively : Double) : Int
    {
        val colors = floatArrayOf(0.0f, 1f,Math.max(Math.min(1-intensively.toFloat(),0.8f),0.1f));
        return ColorUtils.HSLToColor(colors)
    }
}