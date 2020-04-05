package com.demo.app.ncov2020.data.dao

import androidx.room.*
import com.demo.app.ncov2020.data.room_data.GameCountry
import com.demo.app.ncov2020.data.room_data.GameState
import com.demo.app.ncov2020.data.room_data.UserProfile

@Dao
interface UserProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userProfile : UserProfile)

    @Delete
    fun delete(userProfile: UserProfile)

    @Query("SELECT * FROM user_profiles WHERE playerGUID = :playerGUID")
    fun getUserProfile(playerGUID : String): UserProfile?

    @Query("SELECT * FROM user_profiles WHERE username = :username")
    fun getUserProfileByName(username : String): UserProfile?
}