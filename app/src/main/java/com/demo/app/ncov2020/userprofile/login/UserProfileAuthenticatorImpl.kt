package com.demo.app.ncov2020.userprofile.login

import androidx.lifecycle.MutableLiveData
import com.demo.app.ncov2020.data.CurrentUserRepo
import com.demo.app.ncov2020.data.GameRepositoryFacade
import com.demo.app.ncov2020.data.UserRepo
import com.demo.app.ncov2020.userprofile.CurrentSession
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserProfileAuthenticatorImpl(private val facade: GameRepositoryFacade) : UserProfileAuthenticator {
  private val executor : ExecutorService = Executors.newSingleThreadExecutor()

    override fun login(user: MutableLiveData<Login>?) {
        executor.execute {
            val login = user?.value
            login?.let { it ->
                try {
                    var userprofile = facade.getProfileByName(it.username)
                    if (userprofile == null)
                        userprofile = facade.createProfile(it.username)
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
                    var userprofile = facade.getProfileByName(it.username)
                    if (userprofile == null)
                        userprofile = facade.createProfile(it.username)
                    login.state = LoginStates.SUCCESS
                    facade.createProfile(userprofile)
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
            facade.clearProfile()
            CurrentSession.instance.setValue("", "")
        }
    }

    private object HOLDER {
        val INSTANCE = UserProfileAuthenticatorImpl(GameRepositoryFacade.getInstance())
    }

    companion object {
        val instance: UserProfileAuthenticatorImpl by lazy { HOLDER.INSTANCE }
    }
}