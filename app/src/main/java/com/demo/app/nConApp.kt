package com.demo.app

import android.app.Application
import com.demo.app.ncov2020.BuildConfig
import com.demo.app.ncov2020.logic.MainPart.GameModel
import com.demo.app.ncov2020.data.AppDatabase
import com.demo.app.ncov2020.data.AssetsAppDatabase
import com.demo.app.ncov2020.userprofile.login.CurrentSessionManager
import timber.log.Timber
import timber.log.Timber.DebugTree

class nConApp : Application() {

    override fun onCreate() {
        GameModel.testGameModel();
        super.onCreate()
        AppDatabase.create(this)
        AssetsAppDatabase.create(this)
        CurrentSessionManager.instance.initCurrent()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

    }
}