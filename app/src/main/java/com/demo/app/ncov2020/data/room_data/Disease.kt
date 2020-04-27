package com.demo.app.ncov2020.data.room_data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "diseases")
data class Disease (@PrimaryKey(autoGenerate = true) val id: Int? = 0,
                    val playerGUID : String?,
                    var diseaseName: String?,
                    var infectivity: Double? = 0.0,
                    var severity: Long? = 0,
                    var lethality: Double? = 0.0,
                    var symptomsIds: MutableList<String?>?,
                    var transmissionsIds: MutableList<String?>? ,
                    var abilitiesIds: MutableList<String?>?
                    ) {
}