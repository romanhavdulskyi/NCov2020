package com.demo.app.ncov2020.data

import android.util.Base64
import androidx.room.TypeConverter
import com.demo.app.ncov2020.logic.MainPart.UpgradePointsCalc
import com.demo.app.ncov2020.logic.cure.GlobalCure
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*


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

    @TypeConverter
    fun toGlobalCure(data: String?): GlobalCure? {
        if (data == null) {
            return null
        }
        var globalCure : GlobalCure ? = null
        try {
            val base64: ByteArray = Base64.decode(data, Base64.DEFAULT)
            val ois = ObjectInputStream(ByteArrayInputStream(base64))
            globalCure = ois.readObject() as GlobalCure
            ois.close()
        } catch (e: ClassNotFoundException) {
            print("ClassNotFoundException occurred.")
        } catch (e: IOException) {
            print("IOException occurred.")
        }
        return globalCure
    }

    @TypeConverter
    fun toString(globalCure: GlobalCure?): String? {
        if (globalCure == null) {
            return null
        }
        try {
            val baos = ByteArrayOutputStream()
            val oos = ObjectOutputStream(baos)
            oos.writeObject(globalCure)
            oos.close()
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)

        } catch (e: ClassNotFoundException) {
            print("ClassNotFoundException occurred.")
        } catch (e: IOException) {
            print("IOException occurred.")
        }
        return null
    }

    @TypeConverter
    fun toUpgradePointsCalc(data: String?): UpgradePointsCalc? {
        if (data == null) {
            return null
        }
        var toUpgradePointsCalc : UpgradePointsCalc ? = null
        try {
            val base64: ByteArray = Base64.decode(data, Base64.DEFAULT)
            val ois = ObjectInputStream(ByteArrayInputStream(base64))
            toUpgradePointsCalc = ois.readObject() as UpgradePointsCalc
            ois.close()
        } catch (e: ClassNotFoundException) {
            print("ClassNotFoundException occurred.")
        } catch (e: IOException) {
            print("IOException occurred.")
        }
        return toUpgradePointsCalc
    }

    @TypeConverter
    fun toString(upgradePointsCalc: UpgradePointsCalc?): String? {
        if (upgradePointsCalc == null) {
            return null
        }
        try {
            val baos = ByteArrayOutputStream()
            val oos = ObjectOutputStream(baos)
            oos.writeObject(upgradePointsCalc)
            oos.close()
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)

        } catch (e: ClassNotFoundException) {
            print("ClassNotFoundException occurred.")
        } catch (e: IOException) {
            print("IOException occurred.")
        }
        return null
    }

}