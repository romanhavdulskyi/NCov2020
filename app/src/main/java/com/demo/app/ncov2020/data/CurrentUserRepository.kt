package com.demo.app.ncov2020.data

import com.demo.app.ncov2020.data.dao.CurrentUserProfileDao
import com.demo.app.ncov2020.data.room_data.CurrentUserProfile
import com.demo.app.ncov2020.data.room_data.UserProfile
import java.util.*

class CurrentUserRepository(private val currentUserProfileDao: CurrentUserProfileDao) {

    fun clearProfile() {
         currentUserProfileDao.deleteAll()
    }

    fun getProfile(): CurrentUserProfile? {
        return currentUserProfileDao.getCurrentUser()
    }

    fun saveProfile(currentUserProfile: CurrentUserProfile) {
        currentUserProfileDao.deleteAll()
        currentUserProfileDao.insert(currentUserProfile)
    }

    fun createProfile(userProfile: UserProfile): UserProfile {
        val currentUserProfile = CurrentUserProfile(username = userProfile.username, playerGUID = userProfile.playerGUID)
        currentUserProfileDao.deleteAll()
        currentUserProfileDao.insert(currentUserProfile)
        return userProfile
    }

    private object HOLDER {
        val INSTANCE = CurrentUserRepository(AppDatabase.getInstance()!!.CurrentUserProfileDao())
    }

    companion object {
        val instance: CurrentUserRepository by lazy { HOLDER.INSTANCE }
    }
}