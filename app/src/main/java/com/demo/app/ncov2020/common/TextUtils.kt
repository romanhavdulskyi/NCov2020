package com.demo.app.ncov2020.common

object TextUtils {

    fun floatToText(value : Float) : String
    {
        return if(value < 0.1)
            "0.0";
        else
            value.toString()
    }
}