package com.demo.app.ncov2020.userprofile.login

import androidx.lifecycle.MutableLiveData

interface UserProfileAuthenticator {

    fun login(user: MutableLiveData<Login>?)

    fun loginWithRememberMe(user: MutableLiveData<Login>?)

    fun logout()
}