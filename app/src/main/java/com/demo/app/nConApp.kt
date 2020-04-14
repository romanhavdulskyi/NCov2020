package com.demo.app

import android.app.Application
import com.demo.app.ncov2020.BuildConfig
import com.demo.app.ncov2020.common.ViewModelFactoryImpl
import com.demo.app.ncov2020.common.ViewModelProvider
import com.demo.app.ncov2020.logic.MainPart.GameModel
import com.demo.app.ncov2020.data.AppDatabase
import com.demo.app.ncov2020.data.AssetsAppDatabase
import com.demo.app.ncov2020.userprofile.login.CurrentSessionManager
import com.mapbox.mapboxsdk.Mapbox
import timber.log.Timber
import timber.log.Timber.DebugTree

class nConApp : Application() {

    override fun onCreate() {
        GameModel.testGameModel();
        super.onCreate()
        Mapbox.getInstance(this, "pk.eyJ1IjoibmNvdmdhbWUiLCJhIjoiY2s3eWpjcjJjMDdnZTNqcGZ2ZXBxMGYxdSJ9.IBqgc27bmXnxY2G6iF-MiQ")
        AppDatabase.create(this)
        AssetsAppDatabase.create(this)
        ViewModelProvider.init(this)
        CurrentSessionManager.instance.initCurrent()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

    }
}