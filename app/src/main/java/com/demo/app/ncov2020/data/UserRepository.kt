package com.demo.app.ncov2020.data

import com.demo.app.ncov2020.data.db_data.GameState
import com.demo.app.ncov2020.data.db_data.UserProfile
import io.realm.Realm
import java.util.*

class UserRepository {

    fun getProfile(username: String) : UserProfile?
    {
        val realm = Realm.getDefaultInstance()
        try {
            return realm.where(UserProfile::class.java).equalTo("username", username).findFirst()
        }catch (e : Exception)
        {
            realm.close()
        }
        return null
    }

    fun saveProfile(userProfile: UserProfile)
    {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(userProfile)
        realm.commitTransaction()
    }

    fun createProfile(username: String) : UserProfile
    {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val userProfile = realm.createObject(UserProfile::class.java)
        userProfile.username = username
        userProfile.GUID = UUID.randomUUID().toString()
        realm.copyToRealmOrUpdate(userProfile)
        realm.commitTransaction()
        return userProfile
    }
}