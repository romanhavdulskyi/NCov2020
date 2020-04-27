package com.demo.app.ncov2020.data.room_data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Will be imported from local storage
@Entity(tableName = "common_countries")
data class CommonCountry(@PrimaryKey(autoGenerate = true)
                         val id:Int? = 0,
                         val countryUUID : String?,
                         val name: String?,
                         val amountOfPeople: Long,
                         var rich: Boolean,
                         var pathsAir: List<String>?,
                         var pathsSea: List<String>?,
                         var pathsGround: List<String>?,
                         var urls : List<String>?,
                         var medicineLevel: Int,
                         var climate : Int) {
}