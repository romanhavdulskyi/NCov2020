package com.demo.app.ncov2020.data

import com.demo.app.ncov2020.data.dao.CurrentUserProfileDao
import com.demo.app.ncov2020.data.room_data.CurrentUserProfile
import com.demo.app.ncov2020.data.room_data.UserProfile

class CurrentUserRepo private constructor(private val currentUserProfileDao: CurrentUserProfileDao) : CurrentUserRepository {

    override fun clearProfile() {
         currentUserProfileDao.deleteAll()
    }

    override fun getProfile(): CurrentUserProfile? {
        return currentUserProfileDao.getCurrentUser()
    }

    override fun saveProfile(currentUserProfile: CurrentUserProfile) {
        currentUserProfileDao.deleteAll()
        currentUserProfileDao.insert(currentUserProfile)
    }

    override fun createProfile(userProfile: UserProfile): UserProfile {
        val currentUserProfile = CurrentUserProfile(username = userProfile.username, playerGUID = userProfile.playerGUID)
        currentUserProfileDao.deleteAll()
        currentUserProfileDao.insert(currentUserProfile)
        return userProfile
    }



    companion object {
        var INSTANCE : CurrentUserRepo = CurrentUserRepo(AppDatabase.getInstance()!!.CurrentUserProfileDao())
    }
}