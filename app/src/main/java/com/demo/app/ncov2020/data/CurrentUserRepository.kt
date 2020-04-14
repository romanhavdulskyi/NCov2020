package com.demo.app.ncov2020.data

import com.demo.app.ncov2020.data.room_data.CurrentUserProfile
import com.demo.app.ncov2020.data.room_data.UserProfile

interface CurrentUserRepository{

    fun clearProfile()

    fun getProfile(): CurrentUserProfile?

    fun saveProfile(currentUserProfile: CurrentUserProfile)

    fun createProfile(userProfile: UserProfile): UserProfile

}