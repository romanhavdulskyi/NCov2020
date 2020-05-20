package com.demo.app.ncov2020.gamedialogs.country

import androidx.fragment.app.FragmentManager

interface CountryDialog {
    fun openDialog(countryName : String, fragmentManager: FragmentManager, tag : String)
}