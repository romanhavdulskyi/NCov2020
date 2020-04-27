package com.demo.app.ncov2020.data.room_data

import androidx.room.*
import com.demo.app.ncov2020.data.ListConverter

//Will be generated in runtime for everyone user only one time
@Entity(tableName = "game_countries")
data class GameCountry(@PrimaryKey(autoGenerate = true)
                       val id:Int? = 0,
                       val playerUUID : String?,
                       val countryUUID : String?,
                       val name: String?,
                       val amountOfPeople: Long?,
                       var deadPeople: Long? = 0,
                       var infectedPeople: Long? = 0,
                       var healthyPeople : Long?,
                       var rich: Boolean?,
                       var openAirport: Boolean? = true,
                       var openSeaport: Boolean? = true,
                       var openGround: Boolean? = true,
                       var openSchool : Boolean? = true,
                       var infected : Boolean? = false,
                       var cureKoef : Double? = 0.0,
                       var slowInfect: Double? = 0.0,
                       var knowAboutVirus: Boolean,
                       var pathsAir: List<String>?,
                       var pathsSea: List<String>?,
                       var pathsGround: List<String>?,
                       var urls : List<String>?,
                       var medicineLevel: Int,
                       var climate : Int,
                       var state : Int) {

}