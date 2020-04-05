package com.demo.app.ncov2020.data.dao

import androidx.room.*
import com.demo.app.ncov2020.data.room_data.Disease
import com.demo.app.ncov2020.data.room_data.GameCountry
import com.demo.app.ncov2020.data.room_data.GameState

@Dao
interface DiseaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(disease: Disease)

    @Delete
    fun delete(disease: Disease)

    @Query("SELECT * FROM diseases WHERE playerGUID = :playerGUID")
    fun getDisease(playerGUID : String): Disease?

}