package com.demo.app.ncov2020.data.db_data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class UserProfile(@PrimaryKey var id : Int, var username : String, var GUID : String) : RealmObject() {
}