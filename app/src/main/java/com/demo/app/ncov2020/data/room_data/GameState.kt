package com.demo.app.ncov2020.data.room_data

import androidx.room.Entity
import androidx.room.Ignore
import com.demo.app.ncov2020.logic.Country
import com.demo.app.ncov2020.logic.Disease.Disease
import java.lang.IllegalStateException

@Entity(tableName = "game_states")
data class GameState(@androidx.room.PrimaryKey(autoGenerate = true) var id : Int? = 0,
                     var playerGUID : String?,
                     @Ignore var countries: List<GameCountry?>?,
                     @Ignore var disease: com.demo.app.ncov2020.data.room_data.Disease?) {
    constructor() : this(0,"", null, null)

//    fun initialize()
//    {
//        if(disease == null)
//            throw IllegalStateException("Disease should not be null!!!")
//        amountOfPeople = 0
//        deadPeople = 0
//        infectedPeople = 0
//        healthyPeople = 0
//        countries = mutableListOf()
//        countries?.let {
//            if(it.isNotEmpty())
//            {
//                for(item in it)
//                {
//                    item?.amountOfPeople?.let { it1 -> amountOfPeople?.plus(it1) }
//                    item?.deadPeople?.let { it1 -> deadPeople?.plus(it1) }
//                    item?.infectedPeople?.let { it1 -> infectedPeople?.plus(it1) }
//                    item?.healthyPeople?.let { it1 -> healthyPeople?.plus(it1) }
//                }
//            }
//        }
//    }
}