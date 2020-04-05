package com.demo.app.ncov2020.userprofile.login

import com.demo.app.ncov2020.data.AppDatabase
import com.demo.app.ncov2020.data.CurrentUserRepository
import com.demo.app.ncov2020.data.UserRepository
import com.demo.app.ncov2020.userprofile.CurrentSession
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CurrentSessionManager(private val currentUserRepository: CurrentUserRepository) {

    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    fun initCurrent()
    {
        executor.execute{
           val currentUserProfile =  currentUserRepository.getProfile()
           if(currentUserProfile != null)
               CurrentSession.instance.setValue(currentUserProfile.username, currentUserProfile.playerGUID)
        }
    }

    private object HOLDER {
        val INSTANCE = CurrentSessionManager(CurrentUserRepository.instance)
    }

    companion object {
        val instance: CurrentSessionManager by lazy { HOLDER.INSTANCE }
    }
}