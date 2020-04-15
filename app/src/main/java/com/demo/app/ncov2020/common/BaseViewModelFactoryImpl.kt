package com.demo.app.ncov2020.common

import android.app.Application
import androidx.lifecycle.ViewModel
import com.demo.app.basics.ViewModelFactory
import com.demo.app.basics.mvvm.BaseViewModel
import com.demo.app.ncov2020.map.MapViewModel
import com.demo.app.ncov2020.userprofile.LoginViewModel
import java.lang.reflect.Constructor
import java.util.*

class BaseViewModelFactoryImpl : ViewModelFactory {
    override fun createViewModel(className: Class<*>): BaseViewModel {
        val constructor: Constructor<*> = className.getConstructor()
        return constructor.newInstance() as BaseViewModel
    }

}