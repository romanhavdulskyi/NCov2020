package com.demo.app.ncov2020.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.demo.app.basics.ViewModelFactory
import java.util.*


class ViewModelProvider private constructor(application: Application) {
    private val viewModelFactory: ViewModelFactory
    private val androidViewModelFactory: ViewModelFactory
    private val cache: HashMap<Class<*>, ViewModel> = HashMap()
    fun getViewModel(className: Class<*>?): ViewModel? {
        var viewModel = cache[className]
        if (viewModel == null) {
            viewModel = when {
                AndroidViewModel::class.java.isAssignableFrom(className!!) -> {
                    androidViewModelFactory.createViewModel(className)
                }
                ViewModel::class.java.isAssignableFrom(className) -> {
                    viewModelFactory.createViewModel(className)
                }
                else -> {
                    throw IllegalStateException("Class is not inherited from ViewModel!")
                }
            }
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

    init {
        viewModelFactory = ViewModelFactoryImpl()
        androidViewModelFactory = AndroidViewModelFactoryImpl(application)
    }
}