package com.demo.app.ncov2020.data.db_data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class NewsData(@PrimaryKey var id : String, var newsCaption :String, var newsDetails :String, var memeUrl:String) : RealmObject() {
}