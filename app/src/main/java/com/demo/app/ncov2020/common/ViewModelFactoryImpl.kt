package com.demo.app.ncov2020.common

import android.app.Application
import androidx.lifecycle.ViewModel
import com.demo.app.basics.ViewModelFactory
import com.demo.app.ncov2020.map.MapViewModel
import com.demo.app.ncov2020.userprofile.LoginViewModel
import java.util.*

class ViewModelFactoryImpl private constructor(private val app: Application) : ViewModelFactory {
    private val viewModelCache: HashMap<Class<*>, ViewModel> = HashMap()
    override fun createViewModel(className: Class<*>): ViewModel {
        var viewModel: ViewModel? = null
        if (className == MapViewModel::class.java) {
            if (viewModelCache[className] == null) {
                viewModel = MapViewModel(app)
                viewModelCache[className] = viewModel
            } else {
                viewModelCache[className]
            }
        } else if (className == LoginViewModel::class.java) {
            if (viewModelCache[className] == null) {
                viewModel = LoginViewModel(app)
                viewModelCache[className] = viewModel
            } else {
                viewModelCache[className]
            }
        }
        return viewModel!!
    }

    companion object {
        private var instance: ViewModelFactoryImpl? = null
        @Synchronized
        fun getInstance(app: Application): ViewModelFactory? {
            if (instance == null) instance = ViewModelFactoryImpl(app)
            return instance
        }
    }

}