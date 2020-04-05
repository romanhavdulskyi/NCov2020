package com.demo.app.ncov2020.userprofile

data class CurrentSession(var username : String? = "", var playerGUID : String? = "")
{
    fun setValue(username: String?, playerGUID: String?)
    {
        this.playerGUID = playerGUID
        this.username = username
    }

    private object HOLDER {
        val INSTANCE = CurrentSession()
    }

    companion object {
        val instance: CurrentSession by lazy { HOLDER.INSTANCE }
    }
}