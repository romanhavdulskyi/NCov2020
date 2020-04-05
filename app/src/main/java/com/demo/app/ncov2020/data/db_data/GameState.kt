package com.demo.app.ncov2020.data.db_data

import com.demo.app.ncov2020.logic.Country
import com.demo.app.ncov2020.logic.Disease.Disease
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import java.util.ArrayList

data class GameState(@PrimaryKey var id : Int,
                var userId : String,
                var countries: RealmList<Country>,
                var disease: Disease): RealmObject(){
    var amountOfPeople: Long = 0;
    var deadPeople: Long = 0;
    var infectedPeople: Long = 0;
    var healthyPeople: Long = 0;

    init {
        for(country : Country in countries){
            amountOfPeople+=country.amountOfPeople;
            deadPeople+=country.deadPeople;
            infectedPeople+=country.infectedPeople;
            healthyPeople+=country.healthyPeople;
        }
    }

}