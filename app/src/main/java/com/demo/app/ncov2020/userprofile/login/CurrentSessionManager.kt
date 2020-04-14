package com.demo.app.ncov2020.userprofile.login

import com.demo.app.ncov2020.data.CurrentUserRepo
import com.demo.app.ncov2020.data.GameRepositoryFacade
import com.demo.app.ncov2020.userprofile.CurrentSession
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CurrentSessionManager(private val facade: GameRepositoryFacade) {

    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    fun initCurrent()
    {
        executor.execute{
           val currentUserProfile =  facade.getProfile()
           if(currentUserProfile != null)
               CurrentSession.instance.setValue(currentUserProfile.username, currentUserProfile.playerGUID)
        }
    }

    private object HOLDER {
        val INSTANCE = CurrentSessionManager(GameRepositoryFacade.getInstance())
    }

    companion object {
        val instance: CurrentSessionManager by lazy { HOLDER.INSTANCE }
    }
}