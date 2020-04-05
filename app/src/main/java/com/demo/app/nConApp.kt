package com.demo.app

import android.app.Application
import com.demo.app.ncov2020.BuildConfig
import com.demo.app.ncov2020.logic.GameModel
import timber.log.Timber
import timber.log.Timber.DebugTree

class nConApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        var gameModel = GameModel.testGameModel();
    }
}