package com.demo.app.ncov2020.data


import com.demo.app.ncov2020.data.dao.UserProfileDao
import com.demo.app.ncov2020.data.room_data.UserProfile
import java.util.*

class UserRepo(private val userProfileDao: UserProfileDao) : UserRepository {

    override fun getProfileByName(username: String): UserProfile? {
        return userProfileDao.getUserProfileByName(username)
    }

    override fun getProfile(GUID: String): UserProfile? {
        return userProfileDao.getUserProfile(GUID)
    }

    override fun saveProfile(userProfile: UserProfile) {
        userProfileDao.insert(userProfile)
    }

    override fun createProfile(username: String): UserProfile {
        val userProfile = UserProfile(username = username, playerGUID = UUID.randomUUID().toString())
        userProfileDao.insert(userProfile)
        return userProfile
    }

    private object HOLDER {
        val INSTANCE = UserRepo(AppDatabase.getInstance()!!.UserProfileDao())
    }

    companion object {
        val INSTANCE: UserRepo by lazy { HOLDER.INSTANCE }
    }
}