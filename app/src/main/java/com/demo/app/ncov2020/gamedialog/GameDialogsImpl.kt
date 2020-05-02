package com.demo.app.ncov2020.gamedialog

import androidx.fragment.app.FragmentManager
import com.demo.app.ncov2020.game.GameProvider

object GameDialogsImpl : GameDialogs {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var gameProvider: GameProvider

    fun init(fragmentManager: FragmentManager, gameProvider: GameProvider)
    {
        this.fragmentManager = fragmentManager
        this.gameProvider = gameProvider
    }

    override fun openCountryDialog(countryName: String) {
        val countryDialog = CountryDialogImpl(gameProvider)
        countryDialog.openDialog(countryName, fragmentManager, "COUNTRY_DIALOG")
    }

    override fun openDiseaseDialog() {
        val diseaseDialog = DiseaseDialogImpl()
        diseaseDialog.openDialog(fragmentManager, "DISEASE_DIALOG")
    }
}