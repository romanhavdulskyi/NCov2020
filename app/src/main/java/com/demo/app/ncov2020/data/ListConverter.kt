package com.demo.app.ncov2020.data

import android.os.Parcelable
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {
    @TypeConverter
    fun fromStringList(countryLang: MutableList<String?>?): String? {
        if (countryLang == null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<List<String?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toStringList(data: String?): List<String>? {
        if (data == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Any?>?>() {}.type
        return gson.fromJson<List<String>>(data, type)
    }
}