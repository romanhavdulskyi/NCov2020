package com.demo.app.ncov2020.data.dao

import androidx.room.*
import com.demo.app.ncov2020.data.room_data.CurrentUserProfile
import com.demo.app.ncov2020.data.room_data.GameCountry
import com.demo.app.ncov2020.data.room_data.GameState
import com.demo.app.ncov2020.data.room_data.UserProfile

@Dao
interface CurrentUserProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userProfile : CurrentUserProfile)

    @Delete
    fun delete(userProfile: CurrentUserProfile)

    @Query("DELETE FROM current_user_profiles")
    fun deleteAll()

    @Query("SELECT * FROM current_user_profiles")
    fun getCurrentUser(): CurrentUserProfile?
}