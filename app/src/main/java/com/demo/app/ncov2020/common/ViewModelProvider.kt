package com.demo.app.ncov2020.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.demo.app.basics.ViewModelFactory
import com.demo.app.basics.mvvm.BaseAndroidViewModel
import com.demo.app.basics.mvvm.BaseViewModel
import java.util.*
import kotlin.jvm.internal.Reflection


class ViewModelProvider private constructor(application: Application) {
    private val baseViewModelFactoryImpl : ViewModelFactory = BaseViewModelFactoryImpl()
    private val androidViewModelFactory: ViewModelFactory = AndroidViewModelFactoryImpl(application)
    private val cache: HashMap<Class<*>, com.demo.app.basics.mvvm.ViewModel> = HashMap()

    fun getViewModel(className: Class<*>?): com.demo.app.basics.mvvm.ViewModel? {
        var viewModel = cache[className]
        if (viewModel == null) {
            viewModel = when {
                BaseAndroidViewModel::class.java.isAssignableFrom(className!!) -> {
                    androidViewModelFactory.createViewModel(className)
                }
                BaseViewModel::class.java.isAssignableFrom(className) -> {
                    baseViewModelFactoryImpl.createViewModel(className)
                }
                else -> {
                    throw IllegalStateException("Class is not inherited from ViewModel!")
                }
            }
            cache[className] = viewModel!!
        }

        return viewModel
    }

    companion object {
        private var instance: ViewModelProvider? = null
        @Synchronized
        fun init(application: Application) {
            instance = ViewModelProvider(application)
        }

        @Synchronized
        fun getInstance(): ViewModelProvider? {
            checkNotNull(instance) { "Instance should be initialized before" }
            return instance
        }
    }

}