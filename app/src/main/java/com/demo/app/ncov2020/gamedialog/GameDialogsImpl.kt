package com.demo.app.ncov2020.gamedialog

import androidx.fragment.app.FragmentManager

class GameDialogsImpl(private val fragmentManager: FragmentManager) : GameDialogs {

    override fun openCountryDialog(countryName: String) {
        val countryDialog = CountryDialogImpl()
        countryDialog.openDialog(countryName, fragmentManager, "COUNTRY_DIALOG")
    }

    override fun openDiseaseDialog() {
        val diseaseDialog = DiseaseDialogImpl()
        diseaseDialog.openDialog(fragmentManager, "DISEASE_DIALOG")
    }
}