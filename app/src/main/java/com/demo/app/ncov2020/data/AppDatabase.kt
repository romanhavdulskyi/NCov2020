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
    entities = [Disease::class, GameCountry::class, GameState::class, UserProfile::class, CurrentUserProfile::class],
    version = 1
)
@TypeConverters(ListConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun DiseaseDao(): DiseaseDao
    abstract fun GameCountryDao(): GameCountryDao
    abstract fun GameStateDao(): GameStateDao
    abstract fun UserProfileDao(): UserProfileDao
    abstract fun CurrentUserProfileDao(): CurrentUserProfileDao

    private object HOLDER {
        var INSTANCE : AppDatabase? = null
    }

    companion object {
        fun create(context: Context?): AppDatabase? {
            if(HOLDER.INSTANCE == null) {
                Timber.d("OnCreateDatabase")
                val dbName = "nCov.db"
                HOLDER.INSTANCE = Room.databaseBuilder(context!!, AppDatabase::class.java, dbName).build()
            }
            return HOLDER.INSTANCE
        }

        fun getInstance() :AppDatabase?
        {
            return HOLDER.INSTANCE
        }
    }
}