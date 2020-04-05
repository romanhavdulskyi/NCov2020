package com.demo.app.ncov2020.data.room_data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_user_profiles")
data class CurrentUserProfile (
        @PrimaryKey(autoGenerate = true) val id : Int? = 0,
        val playerGUID: String?,
        var username : String?) {
}