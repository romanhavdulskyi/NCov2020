package com.demo.app.ncov2020.common

import androidx.navigation.NavController
import com.demo.app.ncov2020.R
import com.demo.app.ncov2020.userprofile.CurrentSession

class GameNavigatorImpl : GameNavigator {

    private var navController : NavController? = null

    override fun navigateLoginToMap() {
        navController?.navigate(R.id.mapFragment)
    }

    fun setNavController(navController: NavController)
    {
        this.navController = navController
    }

    fun releaseNavController()
    {
        this.navController = null
    }

    private object HOLDER {
        val INSTANCE = GameNavigatorImpl()
    }

    companion object {
        val instance: GameNavigatorImpl by lazy { HOLDER.INSTANCE }
    }
}