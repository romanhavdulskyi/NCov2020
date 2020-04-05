package com.demo.app.ncov2020.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.demo.app.ncov2020.data.room_data.CommonCountry

@Dao
interface CommonCountryDao {
    @Query("SELECT * FROM common_countries")
    fun getAll() : List<CommonCountry>
}