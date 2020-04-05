package com.demo.app.ncov2020.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.app.ncov2020.data.dao.*
import com.demo.app.ncov2020.data.room_data.*
import com.demo.app.ncov2020.userprofile.login.UserProfileAuthenticatorImpl
import timber.log.Timber

@Database(
    entities = [CommonCountry::class],
    version = 1
)
@TypeConverters(ListConverter::class)
abstract class AssetsAppDatabase : RoomDatabase(){
    abstract fun CommonCountryDao(): CommonCountryDao

    private object HOLDER {
        var INSTANCE : AssetsAppDatabase? = null
    }

    companion object {
        fun create(context: Context?): AssetsAppDatabase? {
            if(HOLDER.INSTANCE == null) {
                Timber.d("OnCreateDatabase")
                val dbName = "nCovCommon.db"
                HOLDER.INSTANCE = Room.databaseBuilder(context!!, AssetsAppDatabase::class.java, dbName).createFromAsset("common.db")
                .build()
            }
            return HOLDER.INSTANCE
        }

        fun getInstance() :AssetsAppDatabase?
        {
            return HOLDER.INSTANCE
        }
    }
}