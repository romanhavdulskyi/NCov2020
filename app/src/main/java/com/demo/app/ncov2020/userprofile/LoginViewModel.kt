package com.demo.app.ncov2020.userprofile

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.demo.app.basics.mvvm.BaseAndroidViewModel
import com.demo.app.ncov2020.common.GameNavigatorImpl
import com.demo.app.ncov2020.userprofile.login.Login
import com.demo.app.ncov2020.userprofile.login.LoginStates
import com.demo.app.ncov2020.userprofile.login.UserProfileAuthenticator
import com.demo.app.ncov2020.userprofile.login.UserProfileAuthenticatorImpl

class LoginViewModel(application: Application) : BaseAndroidViewModel(application) {
    var loginLiveData : MutableLiveData<Login> = MutableLiveData()
    var  authenticator : UserProfileAuthenticator? = null;

    init {
        authenticator = UserProfileAuthenticatorImpl.instance
        loginLiveData.value = Login(LoginStates.WAIT, "", true)
        val username  = CurrentSession.instance.username
        if(username != null && username.isNotEmpty()) {
            loginLiveData.value = Login(LoginStates.WAIT, username, true)
            onLoginClicked(null)
        }
    }

    fun onLoginClicked(v: View?)
    {
        val login = loginLiveData.value
        login?.let {
            if(it.username.isNotEmpty() && it.username.isNotBlank())
            {
                it.state = LoginStates.LOGGING_IN

                if(it.keep_me_login_in)
                    authenticator?.loginWithRememberMe(loginLiveData)
                else
                    authenticator?.login(loginLiveData)
            } else {
                login.state = LoginStates.ERROR
                loginLiveData.postValue(login)
            }
        }
    }

    fun loginIsSucceeded()
    {
        GameNavigatorImpl.instance.navigateLoginToMap()
    }
}
