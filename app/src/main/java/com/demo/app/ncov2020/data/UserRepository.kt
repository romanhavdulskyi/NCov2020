package com.demo.app.ncov2020.data


import com.demo.app.ncov2020.data.dao.CurrentUserProfileDao
import com.demo.app.ncov2020.data.dao.UserProfileDao
import com.demo.app.ncov2020.data.room_data.UserProfile
import java.util.*

class UserRepository(private val userProfileDao: UserProfileDao) {

    fun getProfileByName(username: String): UserProfile? {
        return userProfileDao.getUserProfileByName(username)
    }

    fun getProfile(GUID: String): UserProfile? {
        return userProfileDao.getUserProfile(GUID)
    }

    fun saveProfile(userProfile: UserProfile) {
        userProfileDao.insert(userProfile)
    }

    fun createProfile(username: String): UserProfile {
        val userProfile = UserProfile(username = username, playerGUID = UUID.randomUUID().toString())
        userProfileDao.insert(userProfile)
        return userProfile
    }

    private object HOLDER {
        val INSTANCE = UserRepository(AppDatabase.getInstance()!!.UserProfileDao())
    }

    companion object {
        val instance: UserRepository by lazy { HOLDER.INSTANCE }
    }
}