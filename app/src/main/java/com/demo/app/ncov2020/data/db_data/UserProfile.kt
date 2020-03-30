package com.demo.app.ncov2020.data.db_data

import io.realm.RealmObject

class UserProfile(var username : String, var GUID : String) : RealmObject() {
}