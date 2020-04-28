package com.demo.app

import android.app.Application
import android.content.Context
import com.demo.app.ncov2020.BuildConfig
import com.demo.app.ncov2020.common.ViewModelProvider
import com.demo.app.ncov2020.common.offlinegeocoder.ReverseGeocodingCountry
import com.demo.app.ncov2020.data.AppDatabase
import com.demo.app.ncov2020.logic.MainPart.GameStateCallbackDecorator
import com.demo.app.ncov2020.userprofile.login.CurrentSessionManager
import timber.log.Timber
import timber.log.Timber.DebugTree

class NConApp : Application() {

    override fun onCreate() {
        super.onCreate()
        GameStateCallbackDecorator.testGameModel();
        AppDatabase.create(this)
        ViewModelProvider.init(this)
        CurrentSessionManager.instance.initCurrent()
        ReverseGeocodingCountry.init(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        context = this

    }

    companion object {
        var context:Context? = null
    }
}