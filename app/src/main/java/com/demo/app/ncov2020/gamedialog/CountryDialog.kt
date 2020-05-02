package com.demo.app.ncov2020.gamedialog

import androidx.fragment.app.FragmentManager

interface CountryDialog {
    fun openDialog(countryName : String, fragmentManager: FragmentManager, tag : String)
}