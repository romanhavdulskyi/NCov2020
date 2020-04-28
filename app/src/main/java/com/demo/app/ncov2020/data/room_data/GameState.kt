package com.demo.app.ncov2020.data.room_data

import androidx.room.Entity
import androidx.room.Ignore
import com.demo.app.ncov2020.logic.cure.GlobalCure

@Entity(tableName = "game_states")
data class GameState(@androidx.room.PrimaryKey(autoGenerate = true) var id : Int? = 0,
                     var playerGUID : String?,
                     var globalCure: GlobalCure?,
                     @Ignore var countries: MutableList<GameCountry?>?,
                     @Ignore var disease: Disease?) {
    constructor() : this(0,"", null, null,  null)

}