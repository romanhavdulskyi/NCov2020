package com.demo.app.ncov2020.common

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.StringBuilder
import java.util.*

public class CSVConverter {
    fun fromStringList(countryLang: List<String?>?): String? {
        if (countryLang == null) {
            return null
        }
        val builder = StringBuilder()
        for(item in countryLang)
        {
            builder.append(item)
            builder.append(",")
        }
        return builder.toString()
    }

    fun toStringList(data: String?): List<String>? {
        if (data == null) {
            return null
        }
        val array = data.split(",")
        return array.toList()
    }
}