package com.demo.app.ncov2020.data

import com.demo.app.ncov2020.data.room_data.UserProfile
import java.util.*

interface UserRepository {

    fun getProfileByName(username: String): UserProfile?

    fun getProfile(GUID: String): UserProfile?

    fun saveProfile(userProfile: UserProfile)

    fun createProfile(username: String): UserProfile
}