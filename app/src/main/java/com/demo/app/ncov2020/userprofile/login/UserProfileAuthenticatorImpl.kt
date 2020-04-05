package com.demo.app.ncov2020.userprofile.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.demo.app.ncov2020.data.CurrentUserRepository
import com.demo.app.ncov2020.data.UserRepository
import com.demo.app.ncov2020.userprofile.CurrentSession
import java.lang.Exception
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserProfileAuthenticatorImpl(var currentUserRepository: CurrentUserRepository, var userRepository: UserRepository) : UserProfileAuthenticator {
  private val executor : ExecutorService = Executors.newSingleThreadExecutor()

    override fun login(user: MutableLiveData<Login>?) {
        executor.execute {
            val login = user?.value
            login?.let { it ->
                try {
                    var userprofile = userRepository.getProfileByName(it.username)
                    if (userprofile == null)
                        userprofile = userRepository.createProfile(it.username)
                    CurrentSession.instance.setValue(userprofile.username, userprofile.playerGUID)
                    login.state = LoginStates.SUCCESS
                } catch (e : Exception)
                {
                    login.state = LoginStates.ERROR
                }
                user.postValue(login)
            }
        }
    }

    override fun loginWithRememberMe(user: MutableLiveData<Login>?) {
        executor.execute {
            val login = user?.value
            login?.let { it ->
                try {
                    var userprofile = userRepository.getProfileByName(it.username)
                    if (userprofile == null)
                        userprofile = userRepository.createProfile(it.username)
                    login.state = LoginStates.SUCCESS
                    currentUserRepository.createProfile(userprofile)
                    CurrentSession.instance.setValue(userprofile.username, userprofile.playerGUID)
                }catch (e : Exception) {
                    login.state = LoginStates.ERROR
                }
                user.postValue(login)
            }
        }

    }

    override fun logout() {
        executor.execute {
            currentUserRepository.clearProfile()
            CurrentSession.instance.setValue("", "")
        }
    }

    private object HOLDER {
        val INSTANCE = UserProfileAuthenticatorImpl(CurrentUserRepository.instance, UserRepository.instance)
    }

    companion object {
        val instance: UserProfileAuthenticatorImpl by lazy { HOLDER.INSTANCE }
    }
}