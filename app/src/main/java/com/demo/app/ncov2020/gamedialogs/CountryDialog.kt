package com.demo.app.ncov2020.gamedialogs

import androidx.fragment.app.FragmentManager

interface CountryDialog {
    fun openDialog(countryName : String, fragmentManager: FragmentManager, tag : String)
}