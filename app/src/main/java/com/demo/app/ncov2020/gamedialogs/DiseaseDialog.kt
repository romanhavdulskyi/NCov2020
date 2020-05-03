package com.demo.app.ncov2020.gamedialogs

import androidx.fragment.app.FragmentManager

interface DiseaseDialog {
    fun openDialog(fragmentManager: FragmentManager, tag : String)
}