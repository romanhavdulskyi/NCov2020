package com.demo.app.ncov2020.common

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

object TimeUtils {
    @SuppressLint("SimpleDateFormat")
    private val euLongDateFormatter = SimpleDateFormat("dd MMM, yyyy")

    fun formatDate(date : Date) : String
    {
        return euLongDateFormatter.format(date)
    }

}