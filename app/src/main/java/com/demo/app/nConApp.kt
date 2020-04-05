package com.demo.app

import android.app.Application
import androidx.room.Room
import com.demo.app.ncov2020.BuildConfig
import com.demo.app.ncov2020.logic.GameModel
import com.demo.app.ncov2020.data.AppDatabase
import com.demo.app.ncov2020.data.AssetsAppDatabase
import com.demo.app.ncov2020.userprofile.login.CurrentSessionManager
import com.demo.app.ncov2020.userprofile.login.UserProfileAuthenticator
import timber.log.Timber
import timber.log.Timber.DebugTree

class nConApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.create(this)
        AssetsAppDatabase.create(this)
        CurrentSessionManager.instance.initCurrent()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        var gameModel = GameModel.testGameModel();
    }
}